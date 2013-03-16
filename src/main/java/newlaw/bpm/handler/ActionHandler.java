package newlaw.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.roo.addon.javabean.RooJavaBean;
@RooJavaBean
public abstract class ActionHandler {

	protected final Log LOG = LogFactory.getLog(this.getClass());
	
	private Phase phase;
	
	public abstract List<Transition> performAction(ProcessInstance processInstance, List<String> messages) throws BpmException;

	public List<Transition> performAction(ProcessInstance processInstance, Transition transition, List<String> messages) throws BpmException {
		List<Transition> tl = this.performAction(processInstance, messages);
		if(tl == null) {
			tl = new ArrayList<Transition>();
			tl.add(transition);
		}
		return tl;
	}

}
