// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.service;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.service.CredencialService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CredencialService_Roo_Service {
    
    declare @type: CredencialService: @Service;
    
    declare @type: CredencialService: @Transactional;
    
    public long CredencialService.countAllCredencials() {
        return Credencial.countCredencials();
    }
    
    public void CredencialService.deleteCredencial(Credencial credencial) {
        credencial.remove();
    }
    
    public Credencial CredencialService.findCredencial(Long id) {
        return Credencial.findCredencial(id);
    }
    
    public List<Credencial> CredencialService.findAllCredencials() {
        return Credencial.findAllCredencials();
    }
    
    public List<Credencial> CredencialService.findCredencialEntries(int firstResult, int maxResults) {
        return Credencial.findCredencialEntries(firstResult, maxResults);
    }
    
    public void CredencialService.saveCredencial(Credencial credencial) {
        credencial.persist();
    }
    
    public Credencial CredencialService.updateCredencial(Credencial credencial) {
        return credencial.merge();
    }
    
}
