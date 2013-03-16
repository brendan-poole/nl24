package newlaw.bpm.handler;

import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class StartActionHandler extends ActionHandler {

	@Override
	public List<Transition> performAction(ProcessInstance processInstance, List<String> messages) throws BpmException {
		messages.add("Process started");
		LOG.debug("Performing action");
		 return this.getPhase().getTransitions();
	}

}

