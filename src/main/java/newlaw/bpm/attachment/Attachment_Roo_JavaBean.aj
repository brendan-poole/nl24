// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.attachment;

import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.processinstance.ProcessInstance;

privileged aspect Attachment_Roo_JavaBean {
    
    public Long Attachment.getId() {
        return this.id;
    }
    
    public void Attachment.setId(Long id) {
        this.id = id;
    }
    
    public byte[] Attachment.getFileData() {
        return this.fileData;
    }
    
    public void Attachment.setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
    
    public Integer Attachment.getFileVersion() {
        return this.fileVersion;
    }
    
    public void Attachment.setFileVersion(Integer fileVersion) {
        this.fileVersion = fileVersion;
    }
    
    public String Attachment.getFileCode() {
        return this.fileCode;
    }
    
    public void Attachment.setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
    
    public String Attachment.getDescription() {
        return this.description;
    }
    
    public void Attachment.setDescription(String description) {
        this.description = description;
    }
    
    public String Attachment.getName() {
        return this.name;
    }
    
    public void Attachment.setName(String name) {
        this.name = name;
    }
    
    public ProcessInstance Attachment.getProcessInstance() {
        return this.processInstance;
    }
    
    public void Attachment.setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
    
}