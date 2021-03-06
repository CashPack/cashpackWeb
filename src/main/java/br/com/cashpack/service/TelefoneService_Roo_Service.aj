// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.service;

import br.com.cashpack.model.Telefone;
import br.com.cashpack.service.TelefoneService;
import java.util.List;

privileged aspect TelefoneService_Roo_Service {
    
    public abstract long TelefoneService.countAllTelefones();    
    public abstract void TelefoneService.deleteTelefone(Telefone telefone);    
    public abstract Telefone TelefoneService.findTelefone(Long id);    
    public abstract List<Telefone> TelefoneService.findAllTelefones();    
    public abstract List<Telefone> TelefoneService.findTelefoneEntries(int firstResult, int maxResults);    
    public abstract void TelefoneService.saveTelefone(Telefone telefone);    
    public abstract Telefone TelefoneService.updateTelefone(Telefone telefone);    
}
