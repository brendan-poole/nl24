package nl24.domain;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import newlaw.bpm.ProcessData;
import newlaw.domain.Contact;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
//@RooToString
@Table(schema="nl24", uniqueConstraints={
		@UniqueConstraint(columnNames="contact")
		})
@RooJpaActiveRecord
public class Passenger { //implements Comparable  {
	
    
	@ManyToOne(fetch=FetchType.LAZY)
	private Party party;
	
	//TODO: low - if changed to organisation, it doesn't remove the person rec
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	private Contact contact = new Nl24Person();

	private Boolean injured;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Injury injury = new Injury();

	
	@XmlTransient
	public Party getParty() {
		return party;
	}

	public boolean isPopulated() {
		Nl24Person p = (Nl24Person)this.getContact();
		if((p.getForename() != null && !p.getForename().trim().isEmpty())
				|| (p.getSurname() != null && !p.getSurname().trim().isEmpty()))
			return true;
		return false;
	}

/*	@Override
	public int compareTo(Object o) {
		Passenger p = (Passenger)o;
		return this.getId().compareTo(p.getId());
	}
	*/

}
