package nl24.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;

import newlaw.domain.Contact;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
//@RooToString
@RooJpaActiveRecord(inheritanceType="TABLE_PER_CLASS")
@SequenceGenerator(initialValue = 1, name = "partyGen", sequenceName = "party_seq", allocationSize=1)
@Table(uniqueConstraints={
		@UniqueConstraint(columnNames="contact"),
		@UniqueConstraint(columnNames="vehicle"),
		@UniqueConstraint(columnNames="injury"),
})
//@XmlSeeAlso({Client.class, ThirdParty.class})
public abstract class Party {

	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="partyGen")
    private Long id;

	public Party() {
		Passenger p = this.addPassenger();
		this.getPassengers().add(p);
	}
	//TODO: low - if changed to organisation, it doesn't remove the person rec
	
	@Valid // this ensures properties within the object get validated by hibernate
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Contact contact = new Nl24Person();
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Vehicle vehicle = new Vehicle();
	
	private String vehicleDamageDetails;

	@OneToMany(mappedBy="party", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("id")
	private List<Passenger> passengers = new ArrayList<Passenger>();

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Injury injury = new Injury();
	
	private Boolean injured;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Insurer insurer;
	
	private String insurancePolicyNumber;
	
	public Passenger addPassenger() {
		Passenger p = new Passenger();
		p.setParty(this);
		//this.getPassengers().add(p); - causes it to insert 2 passengers!?
		return p;
	}
	
	public Passenger getFirstPassenger() {
		return passengers.get(0);
	}
/*
	@SuppressWarnings("unchecked")
	public List<Passenger> getPassengers() {
		Collections.sort(passengers);
		return passengers;
	}
*/

	@XmlElementWrapper(name="passengers")
	@XmlElement(name="passenger")
	public List<Passenger> getPassengers() {
		return passengers;
	}
	
	
	
}
