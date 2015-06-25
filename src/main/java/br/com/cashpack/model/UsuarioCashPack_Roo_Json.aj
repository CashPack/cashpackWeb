// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.cashpack.model;

import br.com.cashpack.model.UsuarioCashPack;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect UsuarioCashPack_Roo_Json {
    
    public String UsuarioCashPack.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String UsuarioCashPack.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static UsuarioCashPack UsuarioCashPack.fromJsonToUsuarioCashPack(String json) {
        return new JSONDeserializer<UsuarioCashPack>()
        .use(null, UsuarioCashPack.class).deserialize(json);
    }
    
    public static String UsuarioCashPack.toJsonArray(Collection<UsuarioCashPack> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String UsuarioCashPack.toJsonArray(Collection<UsuarioCashPack> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<UsuarioCashPack> UsuarioCashPack.fromJsonArrayToUsuarioCashPacks(String json) {
        return new JSONDeserializer<List<UsuarioCashPack>>()
        .use("values", UsuarioCashPack.class).deserialize(json);
    }
    
}
