
package nl24.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(schema="nl24")
public class CreditServiceProviderInstruction {

	@ManyToOne
	private CreditServiceProvider creditServiceProvider;

	@ManyToOne(cascade={CascadeType.MERGE})
	private HireProvider hireProvider;
	
	@ManyToOne(cascade={CascadeType.MERGE})
	private Repairer repairer;
	
	private String rejectedEscalationNote;
	
	private VehicleCategory vehicleCategoryGroupRating;

	private String repairerJobNumber;
	private String repairsStatus;
	private String reasonVehicleNotInForRepair;
	@Temporal(TemporalType.TIMESTAMP)
	private Date estimatedRepairsCompletionDate;
	
	private Double totalLossValuation;
	private Double engineersTotalLossSalvageValue;
	private Double totalLossSalvageValue;
	@Temporal(TemporalType.TIMESTAMP)
	private Date totalLossSalvageValueAuthorised;
	private Double totalLossSalvageValueAgreed;

	private String hireRejectionReason;

	
}
