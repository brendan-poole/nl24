package newlaw.dap;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;

public class AssignAuditorActionHandler extends ActionHandler {
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		DapProcessInstance pi = ((DapProcessInstance)processInstance);
		DapService service = (DapService) getPhase().getProcessDefinition().getService();

		if(!pi.getTransientVariables().containsKey("auditor"))
				throw new BpmException("Auditor is not present in process instance varibles.");
		
		DapSwimlaneAssignment sa = (DapSwimlaneAssignment) service.createSwimlaneAssignment();
		sa.setUsername(pi.getTransientVariables().get("auditor").toString());
		sa.setProcessInstance(pi);
		sa.setSwimlaneCode("auditor");
		pi.getSwimlaneAssignments().add(sa);
		sa.persist();
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
