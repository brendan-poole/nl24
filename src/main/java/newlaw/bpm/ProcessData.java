package newlaw.bpm;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import nl24.domain.Claim;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(inheritanceType = "TABLE_PER_CLASS")

@SequenceGenerator(initialValue = 1, name = "processDataGen", sequenceName = "process_data_seq", /* schema = "workflow", */ allocationSize = 1)
//@XmlSeeAlso(Claim.class)
public abstract class ProcessData {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "processDataGen")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public abstract String getReference();
}
