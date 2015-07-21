// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.service;

import br.com.cashpack.model.Endereco;
import br.com.cashpack.service.EnderecoService;
import java.util.List;

privileged aspect EnderecoService_Roo_Service {
    
    public abstract long EnderecoService.countAllEnderecoes();    
    public abstract void EnderecoService.deleteEndereco(Endereco endereco);    
    public abstract Endereco EnderecoService.findEndereco(Long id);    
    public abstract List<Endereco> EnderecoService.findAllEnderecoes();    
    public abstract List<Endereco> EnderecoService.findEnderecoEntries(int firstResult, int maxResults);    
    public abstract void EnderecoService.saveEndereco(Endereco endereco);    
    public abstract Endereco EnderecoService.updateEndereco(Endereco endereco);    
}
