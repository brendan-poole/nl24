package newlaw.dap;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;

public class AssignSwimlanesActionHandler extends ActionHandler {
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		DapProcessInstance pi = ((DapProcessInstance)processInstance);
		DapService service = (DapService) getPhase().getProcessDefinition().getService();

		if(!pi.getTransientVariables().containsKey("trainee"))
				throw new BpmException("Trainee is not present in process instance varibles.");
		if(!pi.getTransientVariables().containsKey("manager"))
				throw new BpmException("Manager is not present in process instance varibles.");
		
		DapSwimlaneAssignment trainee = (DapSwimlaneAssignment) service.createSwimlaneAssignment();
		trainee.setUsername(pi.getTransientVariables().get("trainee").toString());
		trainee.setProcessInstance(pi);
		trainee.setSwimlaneCode("trainee");
		pi.getSwimlaneAssignments().add(trainee);
		trainee.persist();
		
		DapSwimlaneAssignment manager  = (DapSwimlaneAssignment) service.createSwimlaneAssignment();
		manager.setUsername(pi.getTransientVariables().get("manager").toString());
		manager.setSwimlaneCode("manager");
		manager.setProcessInstance(pi);
		pi.getSwimlaneAssignments().add(manager);
		manager.persist();
		
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
