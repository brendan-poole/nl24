package newlaw.domain;


public enum InjuryArea {

	Neck,Chest,Back,Fingers,Thumb,Hand,Wrist,Elbow,Arm,Toes,Foot,Ankle,Knee,Leg,Achilles,Hip,Genitals,Sternum,Pelvis,Stomach,Minor_Head_Injury,Severe_Head_Injury,Ribs,Shoulders,Clavicle,Facial_Injuries,Nose,Cheekbone,Jaw,Dental,Internal_Injuries,Loss_of_Sight,Loss_of_Hearing,Loss_of_an_Ear,Tinnitus,Loss_of_Smell,Loss_of_Taste,Psychological,PTSD,Fatality,Loss_of_Hair;
	
	   @Override
	    public String toString() {
			return this.name().replace("_", " ");
	    }
	 
}
