// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import newlaw.domain.Contact;
import nl24.domain.Injury;
import nl24.domain.Party;
import nl24.domain.Passenger;

privileged aspect Passenger_Roo_JavaBean {
    
    public void Passenger.setParty(Party party) {
        this.party = party;
    }
    
    public Contact Passenger.getContact() {
        return this.contact;
    }
    
    public void Passenger.setContact(Contact contact) {
        this.contact = contact;
    }
    
    public Boolean Passenger.getInjured() {
        return this.injured;
    }
    
    public void Passenger.setInjured(Boolean injured) {
        this.injured = injured;
    }
    
    public Injury Passenger.getInjury() {
        return this.injury;
    }
    
    public void Passenger.setInjury(Injury injury) {
        this.injury = injury;
    }
    
}
