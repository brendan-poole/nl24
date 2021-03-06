
package nl24.domain;

import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;


@RooJavaBean
@RooJpaActiveRecord(schema="nl24")
public class RecoveryAgentInstruction {
	
	@ManyToOne
	RecoveryAgent recoveryAgent;
	
	private String vehicleLocation;
}
