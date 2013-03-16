package nl24.domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import newlaw.domain.Organisation;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(schema="nl24")
public class Broker extends Organisation {
	
	@ManyToOne(fetch=FetchType.LAZY)
	private CreditServiceProvider defaultCreditServiceProvider;
	
	private String code;
}
