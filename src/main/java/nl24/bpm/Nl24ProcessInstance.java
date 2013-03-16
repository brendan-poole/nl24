
package nl24.bpm;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.domain.Contact;
import nl24.domain.Claim;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@Table(name="process_instance", schema="nl24", uniqueConstraints={
		@UniqueConstraint(columnNames="data")})
@RooJpaActiveRecord
@XmlRootElement
@XmlType

@NamedQueries({
	@NamedQuery(name="findByClientNamePatterns",
			query="select a from Nl24ProcessInstance a join a.data b join b.client c join c.contact d where (lower(d.forename) like lower(:name1) and lower(d.surname) like lower(:name2)) or (lower(d.surname) like lower(:name1) and lower(d.forename) like lower(:name2)) or (:name2 = '%%' and lower(d.surname) like lower(:name1))"),
	@NamedQuery(name="findByClaimRef",
	query="select a from Nl24ProcessInstance a join a.data b join b.broker c where c.code like :broker and b.id=:id")
})
public class Nl24ProcessInstance extends ProcessInstance {
	
	public Nl24ProcessInstance() {
		this.setData(new Claim());
	}

	@Override
	public Contact getMainContact() {
		return ((Claim)this.getData()).getClient().getContact();
	}
	
	@Override
	public String getReference() {
		return this.getData().getReference();
	}

	// Override the one produced by Roo as we want it to run merge in the parent class so that transient vars remain after merge
	@Override
	public ProcessInstance merge() {
		return super.merge();
	}

	public Claim getClaim() {
		return (Claim)this.getData();
	}

	@Override
	public String getType() {
		if(this.getClaim().getIsThirdPartyClaim().equals(Boolean.TRUE)) {
			return "NL24 Third Party Capture";
		} else {
			return super.getType();
		}
	}
	
}