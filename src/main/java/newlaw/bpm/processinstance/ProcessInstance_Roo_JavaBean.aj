// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.processinstance;

import java.util.Hashtable;
import java.util.List;
import newlaw.bpm.ProcessData;
import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.swimlane.SwimlaneAssignment;

privileged aspect ProcessInstance_Roo_JavaBean {
    
    public Long ProcessInstance.getId() {
        return this.id;
    }
    
    public void ProcessInstance.setId(Long id) {
        this.id = id;
    }
    
    public ProcessData ProcessInstance.getData() {
        return this.data;
    }
    
    public void ProcessInstance.setData(ProcessData data) {
        this.data = data;
    }
    
    public String ProcessInstance.getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }
    
    public void ProcessInstance.setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
    
    public void ProcessInstance.setPhaseAssignments(List<PhaseAssignment> phaseAssignments) {
        this.phaseAssignments = phaseAssignments;
    }
    
    public void ProcessInstance.setSwimlaneAssignments(List<SwimlaneAssignment> swimlaneAssignments) {
        this.swimlaneAssignments = swimlaneAssignments;
    }
    
    public void ProcessInstance.setKeyDates(List<KeyDate> keyDates) {
        this.keyDates = keyDates;
    }
    
    public String ProcessInstance.getLockedBy() {
        return this.lockedBy;
    }
    
    public void ProcessInstance.setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }
    
    public Enum ProcessInstance.getStatus() {
        return this.status;
    }
    
    public void ProcessInstance.setStatus(Enum status) {
        this.status = status;
    }
    
    public Hashtable<String, Object> ProcessInstance.getTransientVariables() {
        return this.transientVariables;
    }
    
    public void ProcessInstance.setTransientVariables(Hashtable<String, Object> transientVariables) {
        this.transientVariables = transientVariables;
    }
    
    public List<Attachment> ProcessInstance.getAttachments() {
        return this.attachments;
    }
    
    public void ProcessInstance.setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    
}
