package newlaw.bpm.keydate;

import java.util.Date;
import java.util.List;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.identities.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyDateService.
 */
@Service
public class KeyDateService {
	
	/** The user service. */
	@Autowired
	UserDetailsService userService;
	
	/**
	 * Gets the key date by phase.
	 *
	 * @param phase the phase
	 * @return the key date by phase
	 */
	public List<KeyDate> getKeyDateByPhase(String phase) {
		List<KeyDate> l = KeyDate.entityManager().createQuery("from KeyDate where phaseCode=?",KeyDate.class)
			.setParameter(1, phase).getResultList();
		return l;
	}
	
	/**
	 * Insert key date.
	 *
	 * @param processInstance the process instance
	 * @param phase the phase
	 * @param transition the transition
	 * @param date the date
	 * @param username the username
	 */
	public void insertKeyDate(ProcessInstance processInstance, String phase, String transition, Date date, String username) {
		if(processInstance == null) throw new IllegalArgumentException("processInstance cannot be null");
		if(phase == null) throw new IllegalArgumentException("phase cannot be null");
		if(transition == null) throw new IllegalArgumentException("transition cannot be null");
		if(date == null) throw new IllegalArgumentException("date cannot be null");
		
		// Check if phase exists.
		processInstance.getProcessDefinition().getPhase(phase);
		
		KeyDate kd = processInstance.getProcessDefinition().getService().createKeyDate();
		kd.setPhaseCode(phase);
		kd.setProcessInstance(processInstance);
		kd.setTriggered(date);
		kd.setTransitionCode(transition);
		kd.setUsername(username);
		processInstance.getKeyDates().add(kd);
		kd.persist();
		
		processInstance.getProcessDefinition().getService().updateStatus(kd);

	}


}
