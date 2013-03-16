package nl24.domain;

import javax.persistence.Table;

import newlaw.domain.Organisation;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
//@RooToString
@Table(schema="nl24")
@RooJpaActiveRecord
public class Police extends Organisation {
	
	public String toString() {
		String s = this.getName();
		if(this.getAddress() != null && this.getAddress().getCity() != null && !this.getAddress().getCity().trim().isEmpty())
			s+= " - "+this.getAddress().getCity();
		return s;
	}
}
