// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.UsuarioAdministrador;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect UsuarioAdministrador_Roo_Json {
    
    public String UsuarioAdministrador.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String UsuarioAdministrador.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static UsuarioAdministrador UsuarioAdministrador.fromJsonToUsuarioAdministrador(String json) {
        return new JSONDeserializer<UsuarioAdministrador>()
        .use(null, UsuarioAdministrador.class).deserialize(json);
    }
    
    public static String UsuarioAdministrador.toJsonArray(Collection<UsuarioAdministrador> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String UsuarioAdministrador.toJsonArray(Collection<UsuarioAdministrador> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<UsuarioAdministrador> UsuarioAdministrador.fromJsonArrayToUsuarioAdministradors(String json) {
        return new JSONDeserializer<List<UsuarioAdministrador>>()
        .use("values", UsuarioAdministrador.class).deserialize(json);
    }
    
}
