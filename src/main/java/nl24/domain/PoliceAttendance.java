package nl24.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
//@RooToString
@Table(schema="nl24")
@RooJpaActiveRecord
public class PoliceAttendance {

	@ManyToOne(cascade={CascadeType.MERGE})
	Police police;
	
	String comments;
	
	String referenceNumber;
	
	String reportingOfficerName;
}
