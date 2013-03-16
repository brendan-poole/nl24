package newlaw.domain;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlSeeAlso;

import nl24.domain.Nl24Person;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType="TABLE_PER_CLASS")
@XmlSeeAlso(Nl24Person.class)
public abstract class Person extends Contact {

    private String forename;

    private String middleName;

    private String surname;

    private String homeTelephone;

    private String workTelephone;

    private String mobileTelephone;
    
    @Enumerated
    private Title title;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    //@org.springframework.format.annotation.DateTimeFormat(style = "M-")
    @Past
    private java.util.Date dateOfBirth;

    private Character sex;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(forename == null ? "" : forename);
        sb.append(" ");
        sb.append(surname == null ? "" : surname);        	        
        return sb.toString().trim();
    }

	@Override
	public String getName() {

		String n = "";
		n += forename == null ? "" : forename;
		n += surname == null ? "" : " " + surname;
		return n.trim();
		
	}

}
