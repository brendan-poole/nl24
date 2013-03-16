package newlaw.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ViewActionHandler extends ActionHandler {

	@Override
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		// Don't progress down any transition as we need to stop and wait for input to view.
		return new ArrayList<Transition>();
		
	}
}

