// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import nl24.domain.CreditServiceProviderInstruction;

privileged aspect CreditServiceProviderInstruction_Roo_Jpa_Entity {
    
    declare @type: CreditServiceProviderInstruction: @Entity;
    
    declare @type: CreditServiceProviderInstruction: @Table(schema = "nl24");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long CreditServiceProviderInstruction.id;
    
    @Version
    @Column(name = "version")
    private Integer CreditServiceProviderInstruction.version;
    
    public Long CreditServiceProviderInstruction.getId() {
        return this.id;
    }
    
    public void CreditServiceProviderInstruction.setId(Long id) {
        this.id = id;
    }
    
    public Integer CreditServiceProviderInstruction.getVersion() {
        return this.version;
    }
    
    public void CreditServiceProviderInstruction.setVersion(Integer version) {
        this.version = version;
    }
    
}