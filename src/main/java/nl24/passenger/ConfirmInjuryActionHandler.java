package nl24.passenger;

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
		Nl24PassengerProcessInstance pi = (Nl24PassengerProcessInstance)processInstance;
		List<Transition> tl = new ArrayList<Transition>();
		if(pi.getClaim().getPassenger().getInjured()) {
			messages.add("Confirmed that passenger is injured");
			tl.add(this.getPhase().getTransitions().get(0));
		} else {
			messages.add("Confirmed that passenger NOT injured");
			tl.add(this.getPhase().getTransitions().get(1));
			
		}
		return tl;
	}

}
