// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.service;

import br.com.cashpack.model.UsuarioCashPack;
import br.com.cashpack.service.UsuarioCashPackService;
import java.util.List;

privileged aspect UsuarioCashPackService_Roo_Service {
    
    public abstract long UsuarioCashPackService.countAllUsuarioCashPacks();    
    public abstract void UsuarioCashPackService.deleteUsuarioCashPack(UsuarioCashPack usuarioCashPack);    
    public abstract UsuarioCashPack UsuarioCashPackService.findUsuarioCashPack(Long id);    
    public abstract List<UsuarioCashPack> UsuarioCashPackService.findAllUsuarioCashPacks();    
    public abstract List<UsuarioCashPack> UsuarioCashPackService.findUsuarioCashPackEntries(int firstResult, int maxResults);    
    public abstract void UsuarioCashPackService.saveUsuarioCashPack(UsuarioCashPack usuarioCashPack);    
    public abstract UsuarioCashPack UsuarioCashPackService.updateUsuarioCashPack(UsuarioCashPack usuarioCashPack);    
}
