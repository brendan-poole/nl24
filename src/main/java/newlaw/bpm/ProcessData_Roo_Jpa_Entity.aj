// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import newlaw.bpm.ProcessData;

privileged aspect ProcessData_Roo_Jpa_Entity {
    
    declare @type: ProcessData: @Entity;
    
    declare @type: ProcessData: @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS);
    
    @Version
    @Column(name = "version")
    private Integer ProcessData.version;
    
    public Integer ProcessData.getVersion() {
        return this.version;
    }
    
    public void ProcessData.setVersion(Integer version) {
        this.version = version;
    }
    
}
