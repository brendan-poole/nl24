package nl24.passenger;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import newlaw.bpm.ProcessData;
import nl24.domain.Passenger;
import nl24.domain.SolicitorInstruction;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
//@RooToString
@Table(schema="nl24", uniqueConstraints={
		@UniqueConstraint(columnNames="passenger")
		})
@RooJpaActiveRecord
@XmlType
@XmlRootElement
public class PassengerClaim extends ProcessData { //implements Comparable  {
	
    
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	private Passenger passenger = new Passenger();

	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	SolicitorInstruction solicitorInstruction = new SolicitorInstruction();
	
	@Override
	public String getReference() {
		return this.getId().toString();
	}
}
