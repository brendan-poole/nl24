package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.processdefinition.ProcessDefinitionException;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import newlaw.bpm.transition.TransitionService;
import newlaw.util.AppUtils;
import nl24.bpm.Nl24ProcessInstance;
import nl24.passenger.Nl24PassengerProcessInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CreateTpPassengerClaimActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;
	
	@Autowired
	TransitionService transitionService;
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		LOG.debug("Performing action");

		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;

		Hashtable<String, ProcessDefinition> allDefs = (Hashtable<String, ProcessDefinition>) AppUtils.getApplicationContext().getBean("processDefinitions");
		ProcessInstanceService service = null;
		for(ProcessDefinition pd : allDefs.values()) {
			if(pd.getCode().equals("nl24Passenger")) {
				service = pd.getService();
				break; // MEMO: latest process def at top. 
			}
		}
		if(service == null)
			throw new ProcessDefinitionException("Process definition not found with code: nl24Passenger");
		
		Nl24PassengerProcessInstance c = (Nl24PassengerProcessInstance) service.startProcessInstance("fnolPassenger1");
		swimlaneService.assignSwimlane(c, "fruHandler", pi.getSwimlaneAssignees().get("fruHandler"),null);
		c.merge();
		messages.add("Created passenger claim for with claim ref "+c.getReference());
		transitionService.trigger(c, messages, "started", "start");
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}
}
