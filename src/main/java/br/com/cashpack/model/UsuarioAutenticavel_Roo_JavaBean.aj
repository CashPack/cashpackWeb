// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioAutenticavel;

privileged aspect UsuarioAutenticavel_Roo_JavaBean {
    
    public Credencial UsuarioAutenticavel.getCredencial() {
        return this.credencial;
    }
    
    public void UsuarioAutenticavel.setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }
    
}
