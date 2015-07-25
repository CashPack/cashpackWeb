package br.com.cashpack.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class AdministradorDoSistema extends UsuarioCredenciavel {

    /**
     */
    @NotNull
    private String nome;
}
