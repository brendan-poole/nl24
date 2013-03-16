package newlaw.domain;

import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import nl24.domain.CreditServiceProvider;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType = "TABLE_PER_CLASS", finders = { "findOrganisationsByUserGroupEquals" })
public abstract class Organisation extends Contact {

    private String name;

    private String contactName;

    private String userGroup;

    private String telephone;
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(name != null)
        	sb.append(name);
        if(this.getAddress() != null && this.getAddress().getCity() != null)
        	sb.append(" (").append(this.getAddress().getCity()).append(")");
        return sb.toString();
    }

    @XmlElement
    public String getName() {
        return name;
    }
}
