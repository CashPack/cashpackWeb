// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.Credencial;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Credencial_Roo_Jpa_Entity {
    
    declare @type: Credencial: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Credencial.id;
    
    @Version
    @Column(name = "version")
    private Integer Credencial.version;
    
    public Long Credencial.getId() {
        return this.id;
    }
    
    public void Credencial.setId(Long id) {
        this.id = id;
    }
    
    public Integer Credencial.getVersion() {
        return this.version;
    }
    
    public void Credencial.setVersion(Integer version) {
        this.version = version;
    }
    
}