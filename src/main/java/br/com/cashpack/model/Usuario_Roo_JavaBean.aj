// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.Telefone;
import br.com.cashpack.model.Usuario;

privileged aspect Usuario_Roo_JavaBean {
    
    public Telefone Usuario.getTelefone() {
        return this.telefone;
    }
    
    public void Usuario.setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    
    public CodigoPIN Usuario.getCodigoPin() {
        return this.codigoPin;
    }
    
    public void Usuario.setCodigoPin(CodigoPIN codigoPin) {
        this.codigoPin = codigoPin;
    }
    
}
