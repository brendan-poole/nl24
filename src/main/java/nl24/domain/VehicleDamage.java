package nl24.domain;

import javax.persistence.Embeddable;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Embeddable
@RooJavaBean
//@RooToString
public class VehicleDamage {

    private Boolean vehicleSafe;

    private Boolean anythingHangingOffVehicle;

    private Boolean wheelsOnAndStraight;

    private Boolean anyDamageToLights;

    private Boolean anyDamageToBackLights;

    private Boolean anyFluidCommingFromVehicle;

    private Boolean anyWindowsBroken;

    private Boolean anyWater;

    private Boolean vehicleUnsecure;
}
