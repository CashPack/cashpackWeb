// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.Agencia;
import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.Endereco;
import br.com.cashpack.model.Gestor;
import br.com.cashpack.model.RamoDeAtividade;
import br.com.cashpack.model.StatusAgencia;
import br.com.cashpack.model.TipoDeDocumentoDaAgenciaEnum;

privileged aspect Agencia_Roo_JavaBean {
    
    public String Agencia.getNomeFantasia() {
        return this.nomeFantasia;
    }
    
    public void Agencia.setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String Agencia.getRazaoSocial() {
        return this.razaoSocial;
    }
    
    public void Agencia.setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String Agencia.getEmail() {
        return this.email;
    }
    
    public void Agencia.setEmail(String email) {
        this.email = email;
    }
    
    public String Agencia.getNumeroDocumento() {
        return this.numeroDocumento;
    }
    
    public void Agencia.setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
    public TipoDeDocumentoDaAgenciaEnum Agencia.getTipoDeDocumentoAgenciaEnum() {
        return this.tipoDeDocumentoAgenciaEnum;
    }
    
    public void Agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum tipoDeDocumentoAgenciaEnum) {
        this.tipoDeDocumentoAgenciaEnum = tipoDeDocumentoAgenciaEnum;
    }
    
    public StatusAgencia Agencia.getStatusAgencia() {
        return this.statusAgencia;
    }
    
    public void Agencia.setStatusAgencia(StatusAgencia statusAgencia) {
        this.statusAgencia = statusAgencia;
    }
    
    public RamoDeAtividade Agencia.getRamoDeAtividade() {
        return this.ramoDeAtividade;
    }
    
    public void Agencia.setRamoDeAtividade(RamoDeAtividade ramoDeAtividade) {
        this.ramoDeAtividade = ramoDeAtividade;
    }
    
    public Gestor Agencia.getGestor() {
        return this.gestor;
    }
    
    public void Agencia.setGestor(Gestor gestor) {
        this.gestor = gestor;
    }
    
    public Endereco Agencia.getEndereco() {
        return this.endereco;
    }
    
    public void Agencia.setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public Credencial Agencia.getCredencial() {
        return this.credencial;
    }
    
    public void Agencia.setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }
    
}
