// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.processinstance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import newlaw.bpm.processinstance.ProcessInstance;

privileged aspect ProcessInstance_Roo_Jpa_Entity {
    
    declare @type: ProcessInstance: @Entity;
    
    declare @type: ProcessInstance: @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS);
    
    @Version
    @Column(name = "version")
    private Integer ProcessInstance.version;
    
    public Integer ProcessInstance.getVersion() {
        return this.version;
    }
    
    public void ProcessInstance.setVersion(Integer version) {
        this.version = version;
    }
    
}
