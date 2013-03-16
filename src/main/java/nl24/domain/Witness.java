package nl24.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import newlaw.domain.Contact;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Table(schema="nl24", uniqueConstraints={
		@UniqueConstraint(columnNames="contact") })
@RooJpaActiveRecord
public class Witness { // implements Comparable {
	
    
	@ManyToOne(fetch=FetchType.LAZY)
	private Claim claim;
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	private Contact contact = new Nl24Person();
	
	@XmlTransient
	public Claim getClaim() {
		return claim;
	}
	/*
	@Override
	public int compareTo(Object o) {
		Witness w = (Witness)o;
		return this.getId().compareTo(w.getId());
	}
	*/
	public boolean isPopulated() {
		Nl24Person p = (Nl24Person)this.getContact();
		if((p.getForename() != null && !p.getForename().trim().isEmpty())
				|| (p.getSurname() != null && !p.getSurname().trim().isEmpty()))
			return true;
		return false;
	}
	
}
