package newlaw.bpm.phase;

import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.handler.Prioritizer;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.swimlane.Swimlane;
import newlaw.bpm.transition.Transition;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * A phase (task) in a workflow. It could be manual (actionHandler is set to the default) or an autmated phase where the action handler contains a custom ActionHandler.
 * These are created as part of the flow in (or imported into) the applicationContext.xml
 * 
 */
@RooJavaBean
public class Phase {
	
	/** The process definition. */
	private ProcessDefinition processDefinition;
	
	/** The swimlane. */
	private Swimlane swimlane;
	
	/** The swimlane code. */
	private String swimlaneCode = "";
	
	/** The transitions. */
	private List<Transition> transitions;
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;
	
	/** The dev notes. */
	private String devNotes;
	
	/** The default page to display when the phase is arrived at. */
	private String defaultPage;
	
	/** The form pages to show for manual phases. */
	private List<String> pages;
	
	/** The action handler to run the the phase is arrived at. */
	private ActionHandler actionHandler;
	
	/** The prioritizer. Used to dynamically set the priority of the phase to, for example, allow it to be colour coded. */
	private Prioritizer prioritizer;
	
	/**
	 * Pre trigger check.
	 *
	 * @return true, if successful
	 */
	public boolean preTriggerCheck() {
		return true;
	}

	/**
	 * Post trigger check.
	 *
	 * @return true, if successful
	 */
	public boolean postTriggerCheck() {
		return true;
	}
	
	/**
	 * Inits the.
	 */
	public void init() {
		for(Transition t : transitions) {
			t.setPhase(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return code;
	}
	
	/**
	 * Gets the transition.
	 *
	 * @param transitionCode the transition code
	 * @return the transition
	 * @throws BpmException the bpm exception
	 */
	public Transition getTransition(String transitionCode) throws BpmException {
		for(Transition t : transitions) {
			if(t.getCode().equals(transitionCode.trim())) {
				return t;
			}
		}
		throw new BpmException("Unknown transition code : "+transitionCode);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return code.equals(((Phase)o).getCode());
	}
}