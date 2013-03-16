package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;
import nl24.bpm.Nl24ProcessInstance;


public class ConfirmInjuryActionHandler extends ActionHandler {

	public ConfirmInjuryActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		LOG.debug("Performing action");
		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;
		List<Transition> tl = new ArrayList<Transition>();
		if(pi.getClaim().getClient().getInjured()) {
			messages.add("Confirmed that client is injured");
			tl.add(this.getPhase().getTransitions().get(0));
		} else {
			messages.add("Confirmed that client NOT injured");
			tl.add(this.getPhase().getTransitions().get(1));
			
		}
		return tl;
	}

}
