// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.Endereco;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Endereco_Roo_Jpa_Entity {
    
    declare @type: Endereco: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Endereco.id;
    
    @Version
    @Column(name = "version")
    private Integer Endereco.version;
    
    public Long Endereco.getId() {
        return this.id;
    }
    
    public void Endereco.setId(Long id) {
        this.id = id;
    }
    
    public Integer Endereco.getVersion() {
        return this.version;
    }
    
    public void Endereco.setVersion(Integer version) {
        this.version = version;
    }
    
}
