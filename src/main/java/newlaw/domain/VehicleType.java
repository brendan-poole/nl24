package newlaw.domain;


public enum VehicleType {

	Car, Van, Motor_cycle, Bus, HGV, Bicycle, Tractor, Quad_bike;
	
    @Override
    public String toString() {
		return this.name().replace("_", " ");
    }
}
