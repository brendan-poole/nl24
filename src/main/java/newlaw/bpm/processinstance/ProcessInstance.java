package newlaw.bpm.processinstance;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import newlaw.bpm.ProcessData;
import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import newlaw.domain.Contact;
import newlaw.util.AppUtils;
import newlaw.util.CustomHashtable;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.transaction.annotation.Transactional;

/**
 * A process instance that encapsulates the current state of a workflow. 
 */
@RooJavaBean
@RooJpaActiveRecord(inheritanceType = "TABLE_PER_CLASS")
/*
 * Create a separate sequence here. For some reason the sequence was
 * incrementing by 32k on restarting tomcat when using what Roo provided. The
 * allocationSize causes hibernate to not cache ids otherwise it will increment
 * by 50 on restarting tomcat.
 */
@SequenceGenerator(initialValue = 1, name = "processInstanceGen", sequenceName = "process_instance_seq", /* schema = "workflow", */ allocationSize = 1)
public abstract class ProcessInstance {

	//@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processInstanceGen")
	//private Long id;

    /** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="processInstanceGen" )
    @Column(name = "id")
    private Long id;
    

	/** The data. For example, Claim is an implementation of ProcessData */
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	private ProcessData data;
	
	/** The process definition key. */
	@Index(name = "_process_definition_key_idx")
	@NotNull
	private String processDefinitionKey;

	/** The phase assignments. */
	@OneToMany( mappedBy = "processInstance", targetEntity = PhaseAssignment.class, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("due")
	private List<PhaseAssignment> phaseAssignments = new ArrayList<PhaseAssignment>();

	/** The swimlane assignments. */
	@OneToMany(mappedBy = "processInstance", targetEntity = SwimlaneAssignment.class, cascade = CascadeType.REMOVE, orphanRemoval = true,  fetch = FetchType.EAGER)
	private List<SwimlaneAssignment> swimlaneAssignments = new ArrayList<SwimlaneAssignment>();

	/** The key dates. */
	@OneToMany(mappedBy = "processInstance", targetEntity = KeyDate.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@OrderBy("triggered")
	private List<KeyDate> keyDates = new ArrayList<KeyDate>();

	/** The locked by. */
	private String lockedBy;
	
	/** The status. */
	private Enum status;
	
	/** The transient variables. */
	@Transient
	private Hashtable<String,Object> transientVariables = new CustomHashtable<String, Object>();
	
	/**
	 * Gets the process definition.
	 *
	 * @return the process definition
	 */
	@XmlTransient
	public ProcessDefinition getProcessDefinition() {
		Hashtable<String, ProcessDefinition> defs = (Hashtable<String, ProcessDefinition>) AppUtils
				.getApplicationContext().getBean("processDefinitions");
		return (ProcessDefinition) defs.get(processDefinitionKey);
	}

	/** The attachments. */
	@OneToMany(mappedBy = "processInstance", targetEntity = Attachment.class, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Attachment> attachments = new ArrayList<Attachment>();
	
	/**
	 * Gets the main contact.
	 *
	 * @return the main contact
	 */
	public abstract Contact getMainContact();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(this.getClass().toString());
		sb.append(" ").append(id == null ? "" : id.toString());
		if(getMainContact() != null && getMainContact().getName() != null) 
			sb.append(" ").append(getMainContact().getName());
		return sb.toString();
	}
	
	/**
	 * Gets the key dates.
	 *
	 * @return the key dates
	 */
	@XmlElementWrapper(name="keyDates")
	@XmlElement(name="keyDate")
	public List<KeyDate> getKeyDates() {
		return keyDates;
	}

	/**
	 * Gets the phase assignments.
	 *
	 * @return the phase assignments
	 */
	@XmlElementWrapper(name="phaseAssignments")
	@XmlElement(name="phaseAssignment")
	public List<PhaseAssignment> getPhaseAssignments() {
		return phaseAssignments;
	}

	/**
	 * Gets the swimlane assignments.
	 *
	 * @return the swimlane assignments
	 */
	@XmlElementWrapper(name="swimlaneAssignments")
	@XmlElement(name="swimlaneAssignment")
	
	public List<SwimlaneAssignment> getSwimlaneAssignments() {
		return swimlaneAssignments;
	}
	
	//TODO: low - a bit inefficient - better to maintain this list
	/**
	 * Gets the swimlane assignees.
	 *
	 * @return the swimlane assignees
	 */
	public Hashtable<String,String> getSwimlaneAssignees() {
		Hashtable<String,String> sah = new Hashtable<String,String>();
		for(SwimlaneAssignment sa : swimlaneAssignments) {
			if(sa.getUsername() != null)
				sah.put(sa.getSwimlaneCode(), sa.getUsername());
		}
		return sah;
	}

	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	@XmlElement
	public abstract String getReference();
	
	//TODO: low - this data access shouldn't be in domain layer but the merge operation is done in an aspect that 
	// roo generates and unless its here roo won't remote its merge method
    /**
	 * Merge.
	 *
	 * @return the process instance
	 */
	@Transactional
    public ProcessInstance merge() {
        if (entityManager == null) entityManager = entityManager();
        Hashtable<String,Object> tvs = transientVariables;
        ProcessInstance merged = this.entityManager.merge(this);
        merged.setTransientVariables(tvs);
        this.entityManager.flush();
        return merged;
    }
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
    	return this.getProcessDefinition().getName();
    }

	
}
