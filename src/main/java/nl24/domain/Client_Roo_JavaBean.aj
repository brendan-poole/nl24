// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import nl24.domain.Client;

privileged aspect Client_Roo_JavaBean {
    
    public String Client.getOccupation() {
        return this.occupation;
    }
    
    public void Client.setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String Client.getNationalInsuranceNumber() {
        return this.nationalInsuranceNumber;
    }
    
    public void Client.setNationalInsuranceNumber(String nationalInsuranceNumber) {
        this.nationalInsuranceNumber = nationalInsuranceNumber;
    }
    
    public String Client.getReasonNoNationalInsuranceNumber() {
        return this.reasonNoNationalInsuranceNumber;
    }
    
    public void Client.setReasonNoNationalInsuranceNumber(String reasonNoNationalInsuranceNumber) {
        this.reasonNoNationalInsuranceNumber = reasonNoNationalInsuranceNumber;
    }
    
    public Boolean Client.getVatRegistered() {
        return this.vatRegistered;
    }
    
    public void Client.setVatRegistered(Boolean vatRegistered) {
        this.vatRegistered = vatRegistered;
    }
    
    public Boolean Client.getAccessToOtherVehicle() {
        return this.accessToOtherVehicle;
    }
    
    public void Client.setAccessToOtherVehicle(Boolean accessToOtherVehicle) {
        this.accessToOtherVehicle = accessToOtherVehicle;
    }
    
}
