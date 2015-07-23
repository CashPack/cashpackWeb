package br.com.cashpack.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
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
	private Endereco endereco;

	@NotNull
	@ManyToOne
	private Credencial credencial;

	public static Gestor findGestorByCredencial(Credencial credencial) {
		String sqlQuery = "SELECT g From Gestor g WHERE g.credencial.login =:login AND g.credencial.senha =:senha";

		EntityManager manager = entityManager();

		TypedQuery<Gestor> query = manager.createQuery(sqlQuery, Gestor.class);
		query.setParameter("login", credencial.getLogin());
		query.setParameter("senha", credencial.getSenha());

		List<Gestor> gestores = query.getResultList();
		if (gestores != null && gestores.size() > 0) {
			return gestores.get(0);
		} else {
			return null;
		}
	}
}
