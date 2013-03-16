package nl24.domain;

import javax.persistence.Enumerated;

import newlaw.domain.InjuryArea;
import newlaw.domain.InjuryType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
//@RooToString
@RooJpaActiveRecord(schema = "nl24")
public class Injury {

	@Enumerated
	private InjuryArea injuryArea;
	@Enumerated
	private InjuryType injuryType;
	
	private Boolean softTissue;
	private Boolean bone;
	private Boolean whiplash;
	private Boolean other;
	private Boolean medicalAttentionSought;
	private Integer daysDetained;
	private Boolean visitedGp;
	private String injuryDescription;

}
