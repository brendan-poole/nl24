package newlaw.bpm.phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import newlaw.bpm.BpmException;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.Swimlane;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import newlaw.bpm.transition.Transition;
import newlaw.bpm.transition.TransitionService;
import newlaw.identities.UserDetailsService;
import newlaw.util.AppUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * Privides phase related
 */
@Service
@RooJavaBean
public class PhaseService {
	
	protected static final Log LOG = LogFactory.getLog(PhaseService.class);

	@Autowired
	UserDetailsService userService;
	
	@Autowired
	TransitionService transitionService;
	
	
	/**
	 * For a given process instance update the currentUserHasAuthority. This is a transient field so therefore needs to be updated when a process instance is read from the database.
	 *
	 * @param processInstance the process instance
	 */
	public void updatePhaseAssignmentRights(ProcessInstance processInstance) {
		for(PhaseAssignment pa : processInstance.getPhaseAssignments()) {
			if(hasSwimlaneAuthorities(pa) 
					|| hasAssignedAuthorities(pa)) {
				pa.setCurrentUserHasAuthority(true);
			} else
				pa.setCurrentUserHasAuthority(false);
		}
	}
	
	/**
	 * Retrieves a list of the phases that the user is able to run.
	 *
	 * @param processInstance the process instance
	 * @return the list
	 * @throws BpmException the bpm exception
	 */
	public List<PhaseAssignment> findUsersPhaseAssignmentsForProcessInstance(ProcessInstance processInstance) throws BpmException {

		// Add phases relevant to the users role.
		
		Set<PhaseAssignment> pas = new HashSet<PhaseAssignment>();
		for (PhaseAssignment pa : processInstance.getPhaseAssignments()) {
				if(hasSwimlaneAuthorities(pa)) {
					pas.add(pa);
				}
		}

		// Add phases which have been assigned to the user
		for (PhaseAssignment pa : processInstance.getPhaseAssignments()) {
			if(!pas.contains(pa) && hasAssignedAuthorities(pa)) {
				pas.add(pa);
			}
		}
		return new ArrayList<PhaseAssignment>(pas);
	}

	/**
	 * Checks for swimlane authorities.
	 *
	 * @param phaseAssignment the phase assignment
	 * @return true, if successful
	 */
	public boolean hasSwimlaneAuthorities(PhaseAssignment phaseAssignment) {
		Phase phase = phaseAssignment.getProcessInstance().getProcessDefinition().getPhase(phaseAssignment.getPhaseCode());
		if (phase.getSwimlane() == null) {
			return true;
		} else {
			Swimlane swimlane = phaseAssignment.getProcessInstance().getProcessDefinition().getSwimlane(
					phase.getSwimlane().getCode());
			for (String auth : userService.getUsersAuthorities())
				for (String role : swimlane.getRoles().split(","))
					if (role.trim().equals(auth)) {
						phaseAssignment.setCurrentUserHasAuthority(true);
						return true;
					}
		}
		return false;
	}
	
	/**
	 * Checks for assigned authorities.
	 *
	 * @param phaseAssignment the phase assignment
	 * @return true, if successful
	 */
	public boolean hasAssignedAuthorities(PhaseAssignment phaseAssignment) {
		UserDetails ud = userService.getUserDetails();
		if(phaseAssignment.getSwimlaneAssignment() != null) {
			SwimlaneAssignment sa = phaseAssignment.getSwimlaneAssignment();
			if(sa.getUsername() != null && sa.getUsername().equals(ud.getUsername())) {
				return true;
			} else if(sa.getUserGroup() != null) {
				for(String auth : userService.getUsersAuthorities()) 
					if(auth.equals(sa.getUserGroup()))
						return true;
			}
		}
		return false;
	}
		
	/**
	 * Returns the next phase that is relevant for the current user.
	 *
	 * @param processInstance the process instance
	 * @return the phase assignment
	 */
	@SuppressWarnings("unchecked")
	public PhaseAssignment usersNextPhaseAssignment(ProcessInstance processInstance) {
		List<PhaseAssignment> pal = findUsersPhaseAssignmentsForProcessInstance(processInstance);
		Collections.sort(pal);
		for(PhaseAssignment pa : pal)
			if(pa.getDue().before(new Date()))
				return pa;
		return null;
	}


	/**
	 * Retrieves all of the users phase assignments before a given due date
	 *
	 * @param due the due
	 * @return the list
	 */
	public List<PhaseAssignment> findUsersPhaseAssignments(Date due) {
		List<SwimlaneAssignment> sal = SwimlaneAssignment.entityManager().createQuery(
				"select a from SwimlaneAssignment a where username=?", SwimlaneAssignment.class)
				.setParameter(1, userService.getUserDetails().getUsername())
				.getResultList();
		List<PhaseAssignment> pal = new ArrayList<PhaseAssignment>();
		for(SwimlaneAssignment sa : sal) {
			for(PhaseAssignment pa : sa.getProcessInstance().getPhaseAssignments()) {
				try {
					if(pa.getDue().before(due) 
							&& pa.getPhase().getSwimlaneCode().equals(sa.getSwimlaneCode()))
						pal.add(pa);
				} catch(Exception e) {
					LOG.error("Unable to retrieve swimlane code for PhaseAssignment: "+pa, e);
				}
			}
		}
		Collections.sort(pal);
		return pal;
	}
	
	/**
	 * Find users group phase assignments.
	 *
	 * @param due the due
	 * @return the list
	 */
	public List<PhaseAssignment> findUsersGroupPhaseAssignments(Date due) {
		List<PhaseAssignment> pal = new ArrayList<PhaseAssignment>();
		Set<String> swimlanes = null;
		swimlanes = new HashSet<String>();
		for(String auth : userService.getUsersAuthorities()) {
			List<SwimlaneAssignment> sal = SwimlaneAssignment.entityManager().createQuery(
					"select a from SwimlaneAssignment a where userGroup=?", SwimlaneAssignment.class)
					.setParameter(1, auth).getResultList();
			for(SwimlaneAssignment sa : sal) {
				for(PhaseAssignment pa : sa.getProcessInstance().getPhaseAssignments()) {
					if(pa.getPhase().getSwimlaneCode().equals(sa.getSwimlaneCode()))
						pal.add(pa);
				}
			}
			
			// Get all of the swimlanes in which this auth is applicable
			Hashtable<String, ProcessDefinition> pds = AppUtils.getApplicationContext()
					.getBean("processDefinitions", Hashtable.class);
			for(ProcessDefinition pd : pds.values())
				for(Swimlane s : pd.getSwimlanes())
					for(String role : s.getRoles().split(","))
						if(auth.equals(role) && !swimlanes.contains(s.getCode()))
							swimlanes.add(s.getCode());
		}
		
		if(swimlanes != null) {
			
			List<PhaseAssignment> allPas = PhaseAssignment.entityManager()
					.createQuery("select a from PhaseAssignment a where due < ?",PhaseAssignment.class)
					.setParameter(1, due)
					.getResultList();
			for(PhaseAssignment pa : allPas) {
					for(String sc : swimlanes) {
						if(pa.getPhase().getSwimlaneCode().equals(sc))
							pal.add(pa);
					}
			}
		}
		Collections.sort(pal);
		return pal;
	}

	/**
	 * Adds a phase to the process instance.
	 *
	 * @param processInstance the process instance
	 * @param phase the phase
	 * @param due the due
	 * @throws BpmException the bpm exception
	 */
	@Transactional
	public void addPhase(ProcessInstance processInstance, String phase, Date due) throws BpmException {
		for (PhaseAssignment pa : processInstance.getPhaseAssignments()) {
			if (pa.getPhaseCode().equals(phase))
				// Duplicate phase - dont add
				return;
		}
		PhaseAssignment pa = processInstance.getProcessDefinition().getService().createPhaseAssignment();
		pa.setDue(due);
		pa.setPhaseCode(phase);
		pa.setProcessInstance(processInstance);
		processInstance.getPhaseAssignments().add(pa);
		pa.persist();
		LOG.debug("Phase added to process instance " + processInstance + ": " + phase);

	}

	/**
	 * Removes the phase.
	 *
	 * @param processInstance the process instance
	 * @param phase the phase
	 * @throws BpmException the bpm exception
	 */
	@Transactional
	public void removePhase(ProcessInstance processInstance, String phase) throws BpmException {
		Phase p = processInstance.getProcessDefinition().getPhase(phase);
		// remove the phase assignment from the process instance
		for (PhaseAssignment pa2 : processInstance.getPhaseAssignments()) {
			if (pa2.getPhaseCode().equals(p.getCode())) {
				pa2.remove();
				processInstance.getPhaseAssignments().remove(pa2);
				
				break;
			}
		}
		LOG.debug("Phase removed to process instance " + processInstance + ": " + phase);
	}

	/**
	 * Validate phase.
	 *
	 * @param processInstance the process instance
	 * @param phase the phase
	 * @param result the result
	 */
	public void validatePhase(ProcessInstance processInstance, String phase, BindingResult result) {
		ProcessDefinition pd = processInstance.getProcessDefinition();
		Phase phaseObj = pd.getPhase(phase);
		if(phaseObj.getPages() != null) {
			for(String page : phaseObj.getPages()) {
				processInstance.getProcessDefinition().getService().validate(processInstance, phase, page, null, result,false);
			}
		}
	}

	/**
	 * Checks if is valid phase for the provided process instance.
	 *
	 * @param pi the pi
	 * @param phase the phase
	 * @return true, if is valid phase
	 * @throws BpmException the bpm exception
	 */
	public boolean isValidPhase(ProcessInstance pi, String phase) throws BpmException {
		List<KeyDate> kdl = KeyDate.entityManager()
				.createQuery("from KeyDate where processInstance=?", KeyDate.class)
				.setParameter(1, pi).getResultList();
		for(KeyDate kd : kdl) {
			Transition t = kd.getTransition();
			if(t.getInvalidatePhases() != null)
				for (String invalidPhase : kd.getTransition().getInvalidatePhases().split(","))
					if (phase.equals(invalidPhase.trim()))
						return false;
		}
		return true;
	}

	/**
	 * Attempt rollback to the given phase. This is possible if in progressing to a new phase it does not perform any automated tasks. This is called when the back button is used in the browser.
	 *
	 * @param processInstance the process instance
	 * @param newPhase the new phase
	 * @param oldPhase the old phase
	 * @return true, if successful
	 */
	@Transactional
	public boolean attemptRollback(ProcessInstance processInstance, Phase newPhase, Phase oldPhase) {
		Transition followedTransition = transitionService.getTransitionBetweenPhases(oldPhase, newPhase);
		if(followedTransition==null)
			return false;
		
		for(Phase p : followedTransition.getToPhase()) {
			List<PhaseAssignment> pal = PhaseAssignment.entityManager()
					.createQuery("select a from PhaseAssignment a where a.processInstance=? and a.phaseCode=?)", PhaseAssignment.class)
						.setParameter(1, processInstance)
						.setParameter(2, p.getCode()).getResultList();
			if(pal.size()==0) {
				return false;
			}
		}

		KeyDate kd = KeyDate.entityManager()
				.createQuery("select a from KeyDate a where a.processInstance=? and a.phaseCode=? order by a.triggered", KeyDate.class)
					.setParameter(1, processInstance)
					.setParameter(2, oldPhase.getCode()).setMaxResults(1).getSingleResult();
		kd.remove();

		for(Phase p : followedTransition.getToPhase()) {
			PhaseAssignment pa = PhaseAssignment.entityManager()
					.createQuery("select a from PhaseAssignment a where a.processInstance=? and a.phaseCode=?)", PhaseAssignment.class)
						.setParameter(1, processInstance)
						.setParameter(2, p.getCode()).getSingleResult();
			pa.remove();
		}
		PhaseAssignment pa = processInstance.getProcessDefinition().getService().createPhaseAssignment();
		pa.setDue(new Date());
		pa.setPhaseCode(oldPhase.getCode());
		pa.setProcessInstance(processInstance);
		pa.persist();
		
		return true;
	}
}
