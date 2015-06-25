package br.com.cashpack.model;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class UsuarioCashPack extends Usuario {

	/**
     */
	@Size(min = 11, max = 11)
	private String cpf;

	/**
     */
	@NotNull
	@Enumerated
	private StatusUsuarioCashPack status;

}
