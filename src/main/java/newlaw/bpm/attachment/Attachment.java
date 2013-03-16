package newlaw.bpm.attachment;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import newlaw.bpm.processinstance.ProcessInstance;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType="TABLE_PER_CLASS")
@SequenceGenerator(initialValue = 1, name = "attachmentGen", sequenceName = "attachment_seq")
/*@Table(uniqueConstraints={
		@UniqueConstraint(columnNames={"fileVersion", "fileCode", "processInstance"})
})*/

public abstract class Attachment {

	@GeneratedValue(strategy = GenerationType.TABLE, generator="attachmentGen" )
    @Id
    private Long id;
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="attachmentGen")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    
    private byte[] fileData;
    
    private Integer fileVersion;
    
    private String fileCode;
    
    private String description;
    
    private String name;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private ProcessInstance processInstance;
}
