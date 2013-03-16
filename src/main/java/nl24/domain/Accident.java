package nl24.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import newlaw.domain.Country;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
//@RooToString
@RooJpaActiveRecord(schema="nl24")
public class Accident {
	
    //TODO: remove unused fields
	
	@Embedded
	private VehicleDamage vehicleDamage;
	
	// Store date time separately to allow for nulls
	@Temporal(TemporalType.TIMESTAMP)
    private Date accidentDate;

	@Temporal(TemporalType.TIMESTAMP)
    private Date accidentTime;


    private String roadName;

    private String town;

    private String location;

    @Enumerated
    private Country country;
    
    private String postcode;
    
    @Size(max=8000) //max for sql server
    private String accidentCircumstances;

    private Integer occupantsNumber;

    private Boolean seatBelt;

    private boolean weatherSun;

    private boolean weatherRain;

    private boolean weatherSnow;

    private boolean weatherIce;

    private boolean weatherFog;

    private String otherWeatherDetails;

    private boolean dry;

    private boolean wet;

    private boolean snow;

    private boolean ice;

    private boolean mud;

    private boolean oil;

    private String otherConditions;

    private boolean driverIsDefendant;

    private Boolean policeReported;

    private boolean vehicleHitSide;

    private boolean vehicleHitInRear;

    private boolean vehicleHitWhilstParked;

    private boolean accidentInCarPark;

    private boolean accidentOnRoundabout;

    private boolean accidentChangingLanes;

    private boolean concertinaCollision;

    private boolean otherCircumstances;
    
    private String claimantPositionInVehicle;
    
    private String purposeOfJourney;
    
    private String journeyDuration;
    
    private String claimantCollectedLocation;
    
    private boolean claimantKnowsNonFaultDriver;
    
    private String landMarks;
}
