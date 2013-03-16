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
public class CreateTpDriverClaimsActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;

	@Autowired
	PhaseService phaseService;
	
	@Autowired
	TransitionService transitionService;
	
	public CreateTpDriverClaimsActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		LOG.debug("Performing action");

		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;

		Hashtable<String, ProcessDefinition> allDefs = (Hashtable<String, ProcessDefinition>) AppUtils.getApplicationContext().getBean("processDefinitions");
		ProcessInstanceService service = null;
		for(ProcessDefinition pd : allDefs.values()) {
				if(pd.getCode().equals("nl24")) {
					service = pd.getService();
					break; // MEMO: latest process def at top. 
				}
					
		}
		if(service == null)
			throw new ProcessDefinitionException("Process definition not found with code: nl24");
		
		Party p = pi.getClaim().getThirdParty();
		Client cl = new Client();
		cl.setContact(p.getContact());
		cl.setInjured(p.getInjured());
		cl.setPassengers(cl.getPassengers());
		Nl24ProcessInstance c = (Nl24ProcessInstance) service.startProcessInstance("fnol1");
		LOG.debug("Changing TP to client for pi "+pi.getId()+" tp "+p.getId()+" client "+c.getClaim().getClient().getId());
		c.getClaim().setClient(cl);
		c.getClaim().setIsThirdPartyClaim(Boolean.TRUE);
		c.getClaim().setBroker(pi.getClaim().getBroker());
		c.merge();
		messages.add("Created TP claim for "+c.getClaim().getThirdParty().getContact() +" with claim ref "+c.getReference());
		transitionService.trigger(c, messages, "started", "start");
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;

	}

}
