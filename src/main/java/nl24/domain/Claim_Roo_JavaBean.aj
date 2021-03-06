// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import nl24.bpm.Nl24ProcessInstance;
import nl24.domain.Accident;
import nl24.domain.Broker;
import nl24.domain.Claim;
import nl24.domain.CreditServiceProviderInstruction;
import nl24.domain.Party;
import nl24.domain.PoliceAttendance;
import nl24.domain.RecoveryAgentInstruction;
import nl24.domain.SolicitorInstruction;
import nl24.domain.Witness;

privileged aspect Claim_Roo_JavaBean {
    
    public Broker Claim.getBroker() {
        return this.broker;
    }
    
    public void Claim.setBroker(Broker broker) {
        this.broker = broker;
    }
    
    public String Claim.getBrokerPolicyNumber() {
        return this.brokerPolicyNumber;
    }
    
    public void Claim.setBrokerPolicyNumber(String brokerPolicyNumber) {
        this.brokerPolicyNumber = brokerPolicyNumber;
    }
    
    public Accident Claim.getAccident() {
        return this.accident;
    }
    
    public void Claim.setAccident(Accident accident) {
        this.accident = accident;
    }
    
    public void Claim.setClient(Party client) {
        this.client = client;
    }
    
    public void Claim.setThirdParty(Party thirdParty) {
        this.thirdParty = thirdParty;
    }
    
    public CreditServiceProviderInstruction Claim.getCreditServiceProviderInstruction() {
        return this.creditServiceProviderInstruction;
    }
    
    public void Claim.setCreditServiceProviderInstruction(CreditServiceProviderInstruction creditServiceProviderInstruction) {
        this.creditServiceProviderInstruction = creditServiceProviderInstruction;
    }
    
    public RecoveryAgentInstruction Claim.getRecoveryAgentInstruction() {
        return this.recoveryAgentInstruction;
    }
    
    public void Claim.setRecoveryAgentInstruction(RecoveryAgentInstruction recoveryAgentInstruction) {
        this.recoveryAgentInstruction = recoveryAgentInstruction;
    }
    
    public SolicitorInstruction Claim.getSolicitorInstruction() {
        return this.solicitorInstruction;
    }
    
    public void Claim.setSolicitorInstruction(SolicitorInstruction solicitorInstruction) {
        this.solicitorInstruction = solicitorInstruction;
    }
    
    public PoliceAttendance Claim.getPoliceAttendance() {
        return this.policeAttendance;
    }
    
    public void Claim.setPoliceAttendance(PoliceAttendance policeAttendance) {
        this.policeAttendance = policeAttendance;
    }
    
    public void Claim.setWitnesses(List<Witness> witnesses) {
        this.witnesses = witnesses;
    }
    
    public void Claim.setProcessInstance(Nl24ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
    
    public Boolean Claim.getIsThirdPartyClaim() {
        return this.isThirdPartyClaim;
    }
    
    public void Claim.setIsThirdPartyClaim(Boolean isThirdPartyClaim) {
        this.isThirdPartyClaim = isThirdPartyClaim;
    }
    
    public Boolean Claim.getThirdPartyInvolved() {
        return this.thirdPartyInvolved;
    }
    
    public void Claim.setThirdPartyInvolved(Boolean thirdPartyInvolved) {
        this.thirdPartyInvolved = thirdPartyInvolved;
    }
    
    public String Claim.getMidResult() {
        return this.midResult;
    }
    
    public void Claim.setMidResult(String midResult) {
        this.midResult = midResult;
    }
    
}
