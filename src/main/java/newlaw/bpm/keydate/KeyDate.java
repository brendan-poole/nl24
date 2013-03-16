package newlaw.bpm.keydate;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import newlaw.bpm.BpmException;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Stores information about he execution of a phase.
 */
@RooJavaBean
@RooToString
@SequenceGenerator(name = "keyDateGen", sequenceName = "key_date_seq")
@RooJpaActiveRecord(inheritanceType = "TABLE_PER_CLASS", finders = { "findKeyDatesByPhaseCodeEquals" })
public abstract class KeyDate {

    /** The id. */
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "keyDateGen")
    @Id
    private Long id;

    /** The process instance. */
    @ManyToOne(targetEntity = ProcessInstance.class, fetch = FetchType.LAZY)
    private ProcessInstance processInstance;

    /** The phase code. */
    @Index(name = "_phase_idx")
    private String phaseCode;

    /** The transition code. */
    private String transitionCode;

    /** The username. */
    @Index(name = "_username_idx")
    @NotNull
    private String username;

    /** The triggered. */
    @Temporal(TemporalType.TIMESTAMP)
    @Index(name = "_triggered_idx")
    private Date triggered;

    /**
     * Gets the process instance.
     *
     * @return the process instance
     */
    @XmlTransient
    public ProcessInstance getProcessInstance() {
        return this.processInstance;
    }

    /**
     * Gets the transition.
     *
     * @return the transition
     * @throws BpmException the bpm exception
     */
    @XmlTransient
    public Transition getTransition() throws BpmException {
        return getPhase().getTransition(this.transitionCode);
    }

    /**
     * Gets the phase.
     *
     * @return the phase
     * @throws BpmException the bpm exception
     */
    @XmlTransient
    public Phase getPhase() throws BpmException {
        if (processInstance == null) throw new BpmException("Process instance not initialised.");
        return processInstance.getProcessDefinition().getPhase(phaseCode);
    }
}
