// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioCredenciavel;

privileged aspect UsuarioCredenciavel_Roo_JavaBean {
    
    public Credencial UsuarioCredenciavel.getCredencial() {
        return this.credencial;
    }
    
    public void UsuarioCredenciavel.setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }
    
}
