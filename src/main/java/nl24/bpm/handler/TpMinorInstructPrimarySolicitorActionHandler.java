package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import newlaw.domain.Organisation;
import newlaw.util.AppUtils;
import nl24.domain.Claim;
import nl24.domain.Solicitor;
import nl24.integration.SendClaimHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TpMinorInstructPrimarySolicitorActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance, List<String> messages)
			throws BpmException {

		Claim c = (Claim)processInstance.getData();
		
		Solicitor sol = (Solicitor) Organisation.findOrganisationsByUserGroupEquals("GT_LAW").getSingleResult();
		c.getSolicitorInstruction().setSolicitor(sol);
		LOG.debug(c.getSolicitorInstruction().getSolicitor());
		c.setSolicitorInstruction(c.getSolicitorInstruction().merge());
		swimlaneService.assignSwimlane(processInstance, "solicitor", null, sol.getUserGroup());
		
		
		SendClaimHandler sch = (SendClaimHandler) AppUtils.getApplicationContext().getBean("xmlEmailSendClaimToSolHandler");
		
		sch.send(c);
		
		messages.add("Claim sent to "+sol.getName());
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
