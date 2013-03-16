package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import newlaw.util.AppUtils;
import nl24.bpm.Nl24ProcessInstance;
import nl24.domain.CreditServiceProvider;
import nl24.integration.SendClaimHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PickCspActionHandler extends ActionHandler {

	@Autowired SwimlaneService swimlaneService;

	public PickCspActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		LOG.debug("Performing action");

		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;
		CreditServiceProvider csp = pi.getClaim().getBroker().getDefaultCreditServiceProvider();
		
		/* MEMO: code to automatically balance out instructions to CSPs. No longer required as now CSP is specific to broker.
		
		// "nulls first" is not supported by hibernate so need to query for nulls otherwise they will never be chosen
		List<CreditServiceProvider> cspl = CreditServiceProvider.entityManager().createQuery(
			"select a from CreditServiceProvider a where a.autoPick=true and lastUsed is null", CreditServiceProvider.class).setMaxResults(1)
			.getResultList();
		if(cspl.size() == 1) 
			csp = cspl.get(0);
		else { 
			cspl = CreditServiceProvider.entityManager().createQuery(
					"select a from CreditServiceProvider a where a.autoPick=true order by a.lastUsed", CreditServiceProvider.class).setMaxResults(1)
					.getResultList();
			if(cspl.size() == 1) 
				csp = cspl.get(0);
			else
				throw new BpmException("No CSPs found.");
		}
	*/		
	
		csp.setLastUsed(new Date());
		csp.persist();

		if(csp.getUserGroup() == null) 
			throw new BpmException("CSP has a blank user group: "+csp);
		
		swimlaneService.assignSwimlane(pi, "creditServiceProvider", null, csp.getUserGroup());

		messages.add("Credit service provider chosen based on broker: "+csp);

		pi.getClaim().getCreditServiceProviderInstruction().setCreditServiceProvider(csp);
		pi.getClaim().setCreditServiceProviderInstruction(pi.getClaim().getCreditServiceProviderInstruction().merge());

		SendClaimHandler sch = (SendClaimHandler) AppUtils.getApplicationContext().getBean(csp.getExportHandlerBean());
		sch.send(pi.getClaim());

		messages.add("Claim successfully sent to CSP.");
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;

	}

}
