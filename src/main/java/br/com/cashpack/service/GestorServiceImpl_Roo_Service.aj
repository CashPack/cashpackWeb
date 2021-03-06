// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.service;

import br.com.cashpack.model.Gestor;
import br.com.cashpack.service.GestorServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect GestorServiceImpl_Roo_Service {
    
    declare @type: GestorServiceImpl: @Service;
    
    declare @type: GestorServiceImpl: @Transactional;
    
    public long GestorServiceImpl.countAllGestors() {
        return Gestor.countGestors();
    }
    
    public void GestorServiceImpl.deleteGestor(Gestor gestor) {
        gestor.remove();
    }
    
    public Gestor GestorServiceImpl.findGestor(Long id) {
        return Gestor.findGestor(id);
    }
    
    public List<Gestor> GestorServiceImpl.findAllGestors() {
        return Gestor.findAllGestors();
    }
    
    public List<Gestor> GestorServiceImpl.findGestorEntries(int firstResult, int maxResults) {
        return Gestor.findGestorEntries(firstResult, maxResults);
    }
    
    public void GestorServiceImpl.saveGestor(Gestor gestor) {
        gestor.persist();
    }
    
    public Gestor GestorServiceImpl.updateGestor(Gestor gestor) {
        return gestor.merge();
    }
    
}
