package nl24.domain;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import newlaw.domain.Contact;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
//@RooToString
@Table(schema="nl24")
@RooJpaActiveRecord
public class Client extends Party {
	
	private String occupation;

	private String nationalInsuranceNumber;
	
	private String reasonNoNationalInsuranceNumber;
	
	private Boolean vatRegistered;
	
	private Boolean accessToOtherVehicle;
	
}
