package br.com.cashpack.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Credencial {

    /**
     */
    @NotNull
    private String login;

    /**
     */
    @NotNull
    private String senha;
}
