package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.phase.PhaseService;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.processdefinition.ProcessDefinitionException;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import newlaw.bpm.transition.TransitionService;
import newlaw.util.AppUtils;
import nl24.bpm.Nl24ProcessInstance;
import nl24.domain.Client;
import nl24.domain.Party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckClaimTypeActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;

	@Autowired
	PhaseService phaseService;
	
	@Autowired
	TransitionService transitionService;
	
	public CheckClaimTypeActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		LOG.debug("Performing action");

		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;

		List<Transition> tl = new ArrayList<Transition>();
		if(pi.getClaim().getIsThirdPartyClaim()) {
			messages.add("Confirmed as TP driver claim");
			tl.add(getPhase().getTransition("tpDriver"));
		} else {
			messages.add("Confirmed as client claim");
			tl.add(getPhase().getTransition("client"));			
		}
		return tl;

	}

}
