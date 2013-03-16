package nl24.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import newlaw.bpm.ProcessData;
import newlaw.bpm.processinstance.ProcessInstance;
import nl24.bpm.Nl24ProcessInstance;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
@RooJavaBean
//@RooToString
@Table(schema="nl24", uniqueConstraints={
		@UniqueConstraint(columnNames="accident"),
		@UniqueConstraint(columnNames="client"),
		@UniqueConstraint(columnNames="third_party"),
		@UniqueConstraint(columnNames="credit_service_provider_instruction"),
		//@UniqueConstraint(columnNames="recovery_agent_instruction"),
		@UniqueConstraint(columnNames="police_attendance")
		
})
@RooJpaActiveRecord
@XmlType
@XmlRootElement
public class Claim extends ProcessData {
	
	public Claim() {
		Witness w = this.addWitness();
		witnesses.add(w);
	}
	
	//TODO: create BrokerInstruction to allow broker info to me merged separately from the PI
	@ManyToOne(fetch=FetchType.EAGER) // Broker code is used in claim reference to fetch eagerly
	private Broker broker;
	
	private String brokerPolicyNumber;
	
	// All of the one-to-ones are set to not cascade merges so that we can update the process without saving them
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private Accident accident = new Accident();
	
	// Eagerly fetch client as the name is needed regularly
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	private Party client = new Client();

	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private	Party thirdParty = new ThirdParty();
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private	CreditServiceProviderInstruction creditServiceProviderInstruction = new CreditServiceProviderInstruction();
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private	RecoveryAgentInstruction recoveryAgentInstruction = new RecoveryAgentInstruction();
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private	SolicitorInstruction solicitorInstruction = new SolicitorInstruction();
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private	PoliceAttendance policeAttendance = new PoliceAttendance();
	
	
	@OneToMany(mappedBy="claim", cascade={CascadeType.PERSIST, CascadeType.REMOVE},fetch=FetchType.LAZY)
	@OrderBy("id")
	private List<Witness> witnesses = new ArrayList<Witness>();
	
	@OneToOne(mappedBy="data",fetch=FetchType.LAZY)
	private Nl24ProcessInstance processInstance;
	
	/* XXX: the following generates a working schema but when xml is generated it causes validation errors:
	 
	 	Uncommented out
	 		<ns2:client xsi:type="ns2:party" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	 		
	 		Which generates the following error
	 			cvc-elt.4.3: Type 'ns2:party' is not validly derived from the type definition, 'thirdParty', of element 'ns2:thirdParty'.
	 	
	 	Commented out
	 		
	 		<ns2:client>
	 		
	 		Which generates no errors
	 	
	 	*/
	@XmlElement(type=Client.class)
	public Party getClient() {
		return client;
	}
	@XmlElement(type=ThirdParty.class)
	public Party getThirdParty() {
		return thirdParty;
	}
	
	
	private Boolean isThirdPartyClaim = Boolean.FALSE;
	
	private Boolean thirdPartyInvolved;
	
	@Override
	@XmlAttribute(required=true)
	public String getReference() {
		String s = "";
		if(getId() != null)
			s = getId().toString();
		if(getBroker() != null && getBroker().getCode() != null) {
			s = getBroker().getCode() + s;
		}
		return s;
	}


	public Witness addWitness() {
		Witness w = new Witness();
		w.setClaim(this);
		return w;
	}
	
	public Witness getFirstWitness() {
		return witnesses.get(0);
	}

	@XmlElementWrapper(name="witnesses")
	@XmlElement(name="witness")
	public List<Witness> getWitnesses() {
		return witnesses;
	}
	
	private String midResult;
/*	
	@XmlElement
	public List<String> getPassengerClaimRefs() {
		List<String> pcl = new ArrayList<String>(); 
		for(Claim c : passengerClaims) {
			pcl.add(c.getReference());
		}
		return pcl;
	}
*/
	@XmlTransient
	public ProcessInstance getProcessInstance() {
		return this.processInstance; 
	}
}
