package newlaw.dap;

import javax.persistence.Table;

import newlaw.bpm.ProcessData;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@Table(schema="dap")
@RooJpaActiveRecord
public class DapInstruction extends ProcessData {

    private String claimRef;

    private String details;

    private String actionNeeded;

    private String conclusion;

    private String auditorsNotes;

    private String trainingNotes;
    
    private String auditorsTrainingNotes;

	@Override
	public String getReference() {
		return this.claimRef;
	}
}
