package br.com.cashpack.model;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class UsuarioAdministrador extends Usuario {

	/**
     */
	@NotNull
	private String nome;
	
	@NotNull
	@ManyToOne
	private Credencial credencial;
	
}
