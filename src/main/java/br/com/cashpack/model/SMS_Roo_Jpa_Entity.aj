// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.SMS;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect SMS_Roo_Jpa_Entity {
    
    declare @type: SMS: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long SMS.id;
    
    @Version
    @Column(name = "version")
    private Integer SMS.version;
    
    public Long SMS.getId() {
        return this.id;
    }
    
    public void SMS.setId(Long id) {
        this.id = id;
    }
    
    public Integer SMS.getVersion() {
        return this.version;
    }
    
    public void SMS.setVersion(Integer version) {
        this.version = version;
    }
    
}
