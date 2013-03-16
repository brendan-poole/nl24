package newlaw.bpm.swimlane;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import newlaw.bpm.BpmException;
import newlaw.bpm.processinstance.ProcessInstance;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType="TABLE_PER_CLASS")
@SequenceGenerator(initialValue = 1, name = "swimlaneAssignmentGen", sequenceName = "swimlane_assignment_seq")
/* TODO: low - why doesn't this work? 
     @Table(uniqueConstraints={
		@UniqueConstraint(columnNames={"swimlane_code", "process_instance"})
})*/

public abstract class SwimlaneAssignment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="swimlaneAssignmentGen")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id")
    private Long id;
    

	@ManyToOne(targetEntity=ProcessInstance.class, fetch=FetchType.LAZY)
	private ProcessInstance processInstance;

	@NotNull
	private String swimlaneCode;
	
	@Index(name = "_user_group_idx")
	private String userGroup;
	
	@Index(name = "_username_idx")
	private String username;
	
	@XmlTransient
	public ProcessInstance getProcessInstance() {
		return this.processInstance;
	}
	
	@XmlTransient
	public Swimlane getSwimlane() throws BpmException {
		if(processInstance == null)
			throw new BpmException("Process instance not initialised.");
		return processInstance.getProcessDefinition().getSwimlane(swimlaneCode);
	}

	public String toString() {
		return this.getSwimlaneCode()+" "+this.getUsername();
	}
	
	public String getAssignee() {
		if(username != null)
			return username;
		else if(userGroup != null)
			return userGroup;
		else
			throw new BpmException("Swimlane assignment has both null username and group");
	}
}
