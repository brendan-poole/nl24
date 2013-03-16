package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import nl24.bpm.Nl24ProcessInstance;
import nl24.domain.Solicitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public class SendClaimToNewlawActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance, List<String> messages) throws BpmException {
		
		messages.add("Claim sent to NewLaw");
		LOG.debug("Performing action");
		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;
		pi.getClaim().getSolicitorInstruction().setSolicitor((Solicitor) Solicitor.findOrganisationsByUserGroupEquals("NEWLAW").getSingleResult());
		pi.getClaim().setSolicitorInstruction(pi.getClaim().getSolicitorInstruction().merge());
		swimlaneService.assignSwimlane(processInstance, "solicitor", null, "NEWLAW");
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
