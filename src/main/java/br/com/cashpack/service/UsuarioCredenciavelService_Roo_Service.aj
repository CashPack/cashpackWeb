// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.service;

import br.com.cashpack.model.UsuarioCredenciavel;
import br.com.cashpack.service.UsuarioCredenciavelService;
import java.util.List;

privileged aspect UsuarioCredenciavelService_Roo_Service {
    
    public abstract long UsuarioCredenciavelService.countAllUsuarioCredenciavels();    
    public abstract void UsuarioCredenciavelService.deleteUsuarioCredenciavel(UsuarioCredenciavel usuarioCredenciavel);    
    public abstract UsuarioCredenciavel UsuarioCredenciavelService.findUsuarioCredenciavel(Long id);    
    public abstract List<UsuarioCredenciavel> UsuarioCredenciavelService.findAllUsuarioCredenciavels();    
    public abstract List<UsuarioCredenciavel> UsuarioCredenciavelService.findUsuarioCredenciavelEntries(int firstResult, int maxResults);    
    public abstract void UsuarioCredenciavelService.saveUsuarioCredenciavel(UsuarioCredenciavel usuarioCredenciavel);    
    public abstract UsuarioCredenciavel UsuarioCredenciavelService.updateUsuarioCredenciavel(UsuarioCredenciavel usuarioCredenciavel);    
}
