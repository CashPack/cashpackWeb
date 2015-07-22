package br.com.cashpack.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
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
public class Gestor extends Usuario {

	@NotNull
	@Size(min = 14, max = 14)
	private String cnpj;

	@NotNull
	private String razaoSocial;

	private String nomeFantasia;

	@NotNull
	private String email;

	private Boolean aceitouOsTermosDeContrato;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusGestorEnum statusGestorEnum;

	@NotNull
	@ManyToOne
	private Credencial credencial;

	@NotNull
	@ManyToOne
	private Endereco endereco;
}
