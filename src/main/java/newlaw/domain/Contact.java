package newlaw.domain;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(inheritanceType="TABLE_PER_CLASS")
@XmlSeeAlso({Person.class, Organisation.class}) // Required to make runtime objects marshall to xml
@SequenceGenerator(initialValue = 1, name = "contactGen", sequenceName = "contact_seq")

public abstract class Contact {
	
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE ,generator="contactGen")
   //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @XmlTransient
	public abstract String getName();
	
    @Valid //MEMO: Very important otherwise address won't be validated.
    @Embedded
    protected Address address = new Address();

    protected String email;
    
    
    
    
    
    
}
