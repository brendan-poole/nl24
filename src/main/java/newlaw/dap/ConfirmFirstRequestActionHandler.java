package newlaw.dap;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import newlaw.bpm.transition.Transition;

import org.springframework.stereotype.Service;

@Service
public class ConfirmFirstRequestActionHandler extends ActionHandler {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {

		//TODO: low - this isn't specific to dap
		 List<SwimlaneAssignment> sa = (List<SwimlaneAssignment>) em.createQuery("from SwimlaneAssignment where username=? and processInstance<>?")
					.setParameter(1, processInstance.getSwimlaneAssignees().get("trainee"))
			.setParameter(2, processInstance)
			.getResultList();
		List<Transition> tl = new ArrayList<Transition>();
		if(sa.isEmpty()) {
			messages.add("Confirmed as first instruction.");
			tl.add(this.getPhase().getTransitions().get(0));
		} else {
			messages.add("Confirmed as NOT first instruction.");
			tl.add(this.getPhase().getTransitions().get(1));
		}
		return tl;
	}

}
