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
import nl24.domain.Injury;

privileged aspect Injury_Roo_Jpa_Entity {
    
    declare @type: Injury: @Entity;
    
    declare @type: Injury: @Table(schema = "nl24");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Injury.id;
    
    @Version
    @Column(name = "version")
    private Integer Injury.version;
    
    public Long Injury.getId() {
        return this.id;
    }
    
    public void Injury.setId(Long id) {
        this.id = id;
    }
    
    public Integer Injury.getVersion() {
        return this.version;
    }
    
    public void Injury.setVersion(Integer version) {
        this.version = version;
    }
    
}