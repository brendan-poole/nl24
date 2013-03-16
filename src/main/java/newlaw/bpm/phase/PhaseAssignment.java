package newlaw.bpm.phase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import newlaw.bpm.BpmException;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneAssignment;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType="TABLE_PER_CLASS")
@SequenceGenerator(initialValue = 1,  name = "phaseAssignmentGen", sequenceName = "phase_assignment_seq")

public abstract class PhaseAssignment implements Comparable<PhaseAssignment> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="phaseAssignmentGen")
    //@GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date due;
	
    @ManyToOne(targetEntity=ProcessInstance.class, fetch=FetchType.LAZY)
	private ProcessInstance processInstance;

	@Column(nullable=false)
	private String phaseCode;

	@XmlTransient
	public Phase getPhase() throws BpmException {
		if(processInstance == null)
			throw new BpmException("Process instance not initialised.");
		return processInstance.getProcessDefinition().getPhase(phaseCode);
	}
	
	public void setPhase(Phase phase) {
		this.phaseCode=phase.getCode();
	}
	
	@XmlTransient
	public ProcessInstance getProcessInstance() {
		return this.processInstance;
	}
	
	@Transient
	private Boolean currentUserHasAuthority;
	
	public SwimlaneAssignment getSwimlaneAssignment() throws BpmException {
		if(processInstance == null)
			throw new BpmException("Process instance not initialised.");
		for(SwimlaneAssignment sa : processInstance.getSwimlaneAssignments())
			if(sa.getSwimlaneCode().equals(this.getPhase().getSwimlaneCode()))
				return sa;
		//throw new BpmException("Unable to find swimlane assignment for phase: "+this.toString());
		return null;
		
	}
	
	@XmlTransient
	public Boolean getCurrentUserHasAuthority() {
		return currentUserHasAuthority;
	}


	@Override
	public String toString() {
		return this.phaseCode+" #"+id; 
	}
	
	public int getPriority() throws BpmException {
		if(this.getPhase().getPrioritizer() != null)
			// TODO: variable date. Will probably need AppData singleton
			return this.getPhase().getPrioritizer().getPriority(this, new Date());
		else
			// TODO: fallback to static priority for phase.
			return 0;
	}
	
	@Override
	public int compareTo(PhaseAssignment o) {
		PhaseAssignment pa = (PhaseAssignment)o;
		if(this.getDue().after(pa.getDue()))
			return 1;
		else 
			return -1;
		
	}
}
