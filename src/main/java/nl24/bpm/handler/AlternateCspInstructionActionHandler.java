package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import nl24.bpm.Nl24ProcessInstance;
import nl24.bpm.Nl24ProcessInstanceService;
import nl24.domain.CreditServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlternateCspInstructionActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;
	
	public AlternateCspInstructionActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		LOG.debug("Performing action");

		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;
		Nl24ProcessInstanceService service = (Nl24ProcessInstanceService) getPhase().getProcessDefinition().getService();
		
		CreditServiceProvider csp = CreditServiceProvider.entityManager().createQuery("from CreditServiceProvider where userGroup=?",CreditServiceProvider.class)
				.setParameter(1, "SANDG").getSingleResult();

		if(csp.getUserGroup() == null) 
			throw new BpmException("CSP has a blank user group: "+csp);

		swimlaneService.assignSwimlane(pi, "creditServiceProvider", null, csp.getUserGroup());
		
		messages.add("Credit service provider selected: "+csp);
		pi.getClaim().getCreditServiceProviderInstruction().setCreditServiceProvider(csp);
		pi.getClaim().setCreditServiceProviderInstruction(pi.getClaim().getCreditServiceProviderInstruction().merge());

		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;

	}

}
