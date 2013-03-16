package newlaw.domain;


public enum InjuryType {
	Soft_Tissue,Fracture,Ligament,Rupture,Sprain,Muscular,Scarring,Bruising,Dislocation,Laceration,Fatality,Amputation,Respiratory;
	   @Override
	    public String toString() {
			return this.name().replace("_", " ");
	    }
}
