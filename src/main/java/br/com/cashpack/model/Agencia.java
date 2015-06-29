package br.com.cashpack.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Agencia extends Usuario {

	@NotNull
	private String razao_social;

	@NotNull
	@Size(min = 14, max = 14)
	private String cnpj;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusAgencia statusAgencia;

	@ManyToOne
	@NotNull
	private RamoDeAtividade ramoDeAtividade;

	@ManyToOne
	@NotNull
	private Gerente gerente;
}
