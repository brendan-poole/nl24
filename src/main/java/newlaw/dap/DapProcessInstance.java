
package newlaw.dap;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.domain.Contact;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@Table(name="process_instance", schema="dap")
@RooJpaActiveRecord
public class DapProcessInstance extends ProcessInstance {

	public DapProcessInstance() {
		this.setData(new DapInstruction());
	}
	
	@Override
	public Contact getMainContact() {
		return null;
	}

	@Override
	public ProcessInstance merge() {
		return super.merge();
	}
	@Override
	public String getReference() {
		return this.getId().toString();
	}
}
