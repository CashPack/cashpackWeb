package br.com.cashpack.model;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
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
	private String razaoSocial;

	@NotNull
	private String nomeFantasia;

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
	// @NotNull
	private Gerente gerente;

	public static Agencia findAgenciaByCnpj(String cnpj) {
		String sql = "SELECT a FROM Agencia a WHERE a.cnpj =:cnpj";

		EntityManager manager = entityManager();

		TypedQuery<Agencia> query = manager
				.createQuery(sql, Agencia.class);
		query.setParameter("cnpj", cnpj);

		return query.getSingleResult();
	}
}
