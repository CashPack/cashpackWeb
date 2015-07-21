package br.com.cashpack.model;

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
public class Endereco {

	@NotNull
	private String logradouro;
	
	@NotNull
	@Size(max = 12)
	private String numero;
	
	@NotNull
	private String bairro;
	
	@NotNull
	private String municipio;
	
	@NotNull
	@Size(min = 2, max = 2)
	private String siglaUF;
	
	@NotNull
	@Size(min = 8, max = 8)
	private String cep;
	
	private String pais;
	
}
