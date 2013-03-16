package nl24.domain;

import javax.persistence.Enumerated;

import newlaw.domain.VehicleType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;


@RooJavaBean
//@RooToString
@RooJpaActiveRecord(schema="nl24")
public class Vehicle {

    @Enumerated
    private VehicleType vehicleType;
	private String make;
	private String model;
	private String vrn;
	private String colour;
	private String engineSize;
	
}
