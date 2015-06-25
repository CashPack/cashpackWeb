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
public class Telefone {

	/**
     */
	@NotNull
	@Size(min = 2, max = 4)
	private String codPais;

	/**
     */
	@NotNull
	@Size(min = 2, max = 3)
	private String codArea;

	/**
     */
	@NotNull
	@Size(min = 8, max = 9)
	private String numero;

	@Override
	public String toString() {
		String telefone = "";

		if (codPais != null && !codPais.isEmpty()) {
			telefone += codPais;
		}

		if (codArea != null && !codArea.isEmpty()) {
			telefone += codArea;
		}

		if (numero != null && !numero.isEmpty()) {
			telefone += numero;
		}

		return telefone;
	}

	
}
