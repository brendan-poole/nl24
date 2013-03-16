package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.bpm.transition.Transition;
import nl24.bpm.Nl24ProcessInstance;
import nl24.bpm.Nl24ProcessInstanceService;
import nl24.bpm.Nl24SwimlaneAssignment;
import nl24.domain.CreditServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstructSecondarySolicitorActionHandler extends ActionHandler {

	@Autowired
	SwimlaneService swimlaneService;
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance, List<String> messages)
			throws BpmException {
		messages.add("Claim sent to Test Sol B");
		LOG.debug("Performing action");
		
		swimlaneService.assignSwimlane(processInstance, "solicitor", null, "TEST_SOL_B");
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
