
package nl24.domain;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import newlaw.domain.Organisation;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(schema="nl24")
@XmlAccessorType(XmlAccessType.NONE)
public class CreditServiceProvider extends Organisation {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUsed;
	
	private Boolean autoPick = false;
	
	private String exportHandlerBean; 
	
	@ManyToOne(fetch=FetchType.LAZY)
	private RecoveryAgent recoveryAgent;
}
