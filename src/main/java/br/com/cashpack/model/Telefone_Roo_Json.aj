// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.Telefone;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Telefone_Roo_Json {
    
    public String Telefone.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String Telefone.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static Telefone Telefone.fromJsonToTelefone(String json) {
        return new JSONDeserializer<Telefone>()
        .use(null, Telefone.class).deserialize(json);
    }
    
    public static String Telefone.toJsonArray(Collection<Telefone> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String Telefone.toJsonArray(Collection<Telefone> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<Telefone> Telefone.fromJsonArrayToTelefones(String json) {
        return new JSONDeserializer<List<Telefone>>()
        .use("values", Telefone.class).deserialize(json);
    }
    
}
