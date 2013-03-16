
package nl24.passenger;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name="passenger_process_instance", schema="nl24", uniqueConstraints={
		@UniqueConstraint(columnNames="data")

})
@RooJpaActiveRecord
@XmlRootElement
@XmlType
@NamedQueries({
	@NamedQuery(name="findPassengerByClientNamePatterns",
			query="select a from Nl24PassengerProcessInstance a join a.data b join b.client c join c.contact d where (lower(d.forename) like lower(:name1) and lower(d.surname) like lower(:name2)) or (lower(d.surname) like lower(:name1) and lower(d.forename) like lower(:name2)) or (:name2 = '%%' and lower(d.surname) like lower(:name1))"),
	@NamedQuery(name="findPassengerByClaimRef",
	query="select a from Nl24PassengerProcessInstance a join a.data b where b.id=:id")
})

public class Nl24PassengerProcessInstance extends ProcessInstance {
	
	public Nl24PassengerProcessInstance() {
		this.setData(new PassengerClaim());
	}
	
	
	@Override
	public Contact getMainContact() {
		return getClaim().getPassenger() != null ? getClaim().getPassenger().getContact() : null;
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

	public PassengerClaim getClaim() {
		return (PassengerClaim)getData();
	}
	
}