package newlaw.bpm.transition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.keydate.KeyDateService;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.phase.PhaseService;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.identities.UserDetailsService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
public class TransitionService {

	@Autowired
	transient MailSender mailTemplate;

	@Autowired
	UserDetailsService userService;

	@Autowired
	SwimlaneService swimlaneService;
	
	private String emailUrl;

	private boolean runActionHandlers = true;
	
	@Autowired
	private PhaseService phaseService;

	@Autowired
	private KeyDateService keyDateService;


	protected static final Log LOG = LogFactory.getLog(TransitionService.class);

	/**
	 * Triggers the transition for a given phase.  The action handlers are executed for all automated phases.
	 *
	 * @param processInstance the process instance
	 * @param messages the messages
	 * @param task the task
	 * @param phase the phase
	 * @return the process instance
	 * @throws BpmException the bpm exception
	 */
	public ProcessInstance trigger(ProcessInstance processInstance, List<String> messages, String task, String phase)
			throws BpmException {
		
		ProcessInstance pi = processInstance;
		LOG.debug("Triggering task for process instance " + pi + ": task=" + task + " phase=" + phase);
		Transition type = null;
		for (Transition t : pi.getProcessDefinition().getPhase(phase).getTransitions())
			if (t.getCode().equals(task))
				type = t;

		try {
			List<Phase> pl = trigger(pi, messages, type, new Date());
			List<Transition> tl = null;
			for(Phase p : pl) {
				try {
					tl = runActionHandler(p.getActionHandler(),pi, messages);
				} catch (Exception e) {
					messages.add("Unable to trigger phase "+p+": "+e.getMessage());
					
				}
				if(tl != null)
					for(Transition t : tl) {
						if(this.runActionHandlers) {
							trigger(pi, messages, t.getCode(), t.getPhase().getCode());
						}
					}
			}
			
			return pi;

		} catch (Exception e) {

			LOG.error("Error triggering task: ", e);
			throw new BpmException("Unable to trigger task: "+e.getMessage() , e);
		}

	}

	@Transactional
	public List<Transition> runActionHandler(ActionHandler actionHandler, ProcessInstance processInstance, List<String> messages) {
		List<Transition> transitions = null;
		try {
			transitions = actionHandler.performAction(processInstance, messages);
		} catch (Exception e) {
			throw new TransitionException("Unable to run action handler for "+actionHandler.getPhase().getCode(), e);
		}
		return transitions;
		
	}

	/**
	 * Trigger a transition between two phase by removing the existing phase and adding the new phases. The due due dates are calc'd for each new phase.
	 *
	 * @param processInstance the process instance
	 * @param messages the messages
	 * @param task the task
	 * @param currentDate the current date
	 * @return the list
	 * @throws BpmException the bpm exception
	 */
	@Transactional
	private List<Phase> trigger(ProcessInstance processInstance, List<String> messages, Transition task,Date currentDate) throws BpmException {

		
		List<Phase> pl = new ArrayList<Phase>();
		
		ProcessInstance pi = processInstance;

		// Remove the existing phase
		phaseService.removePhase(pi, task.getPhase().getCode());

		keyDateService.insertKeyDate(pi, task.getPhase().getCode(), task.getCode(), new Date(),userService.getUserDetails().getUsername());
		
		// Removes phases which become invalid as a result of this transition
		if (task.getInvalidatePhases() != null && !task.getInvalidatePhases().isEmpty())
			for (String invalidPhase : task.getInvalidatePhases().split(","))
				phaseService.removePhase(pi, invalidPhase);
		
		// Remove any phases that should be killed.
		if (task.getKillPhases() != null && !task.getKillPhases().isEmpty())
			for (String killPhase : task.getKillPhases().split(","))
				phaseService.removePhase(pi, killPhase);
		
		// Add the new phases
		for (int i = 0; i < task.getToPhase().size(); i++) {
			Phase p = task.getToPhase().get(i);

			if (!phaseService.isValidPhase(pi, p.getCode())) {
				// Phase marked as invalid by a transition that was ran previously so don't add it. 
				// 		For example, if the CSP had indicated that the vehicle has been repaired, the 'chase csp for vehicle repair' phase is invalid.
				LOG.debug("Phase marked as invalid so not adding: " + p.getCode());
			} else {
				
				// Calc the due date for the new phase.
				DateTime due = null;
				if(task.getDueDateHandlers().size() > 0) {
					due = new DateTime(task.getDueDateHandlers().get(i).getDueDate(pi));
				}
				if(due == null) {
					due = new DateTime(currentDate);
				}
				if (task.getPeriods().size() > 0) {
					Period period = task.getPeriods().get(i);
					due = due.plus(period);
				}

				// Add the new phase as the calc'd due date
				phaseService.addPhase(pi, p.getCode(), due.toDate());
				
				pl.add(p);
			}
		}
/*
			for (Transition type : p.getTransitions()) {
				if (type.getEmail() == Boolean.TRUE) {
					SimpleMailMessage msg = new SimpleMailMessage();
					msg.setFrom("noreply@new-law.co.uk");
					msg.setSubject("New task to be actioned: " + type.getName());
					String[] groups = p.getSwimlane().getRoles().split(",");
					List<String> tos = new ArrayList<String>();
					for (String g : groups) {
						Group gr = userService.findGroup(g);
						if (gr.getEmail() != null) {
							tos.add(gr.getEmail());
						}
					}
					msg.setTo(tos.toArray(new String[tos.size()]));
					msg.setText(this.emailUrl.replace("%id", pi.toString()).replace("%task",
							type.getCode().toLowerCase()));
					mailTemplate.send(msg);
					// throw new MailSendException("Could not send email");
				}
			}
			*/
		return pl;
		
	}
	
	/**
	 * Gets the transition between 2 phases or returns null if phases are not connected.
	 *
	 * @param phaseA the phase a
	 * @param phaseB the phase b
	 * @return the transition between phases
	 */
	public Transition getTransitionBetweenPhases(Phase phaseA, Phase phaseB) {
		Transition followedTransition = null;
		for(Transition t : phaseA.getTransitions()) {
			if(t.getToPhase().contains(phaseB)) {
				followedTransition = t;
				break;
			}
		}
		return followedTransition;
	}
}
