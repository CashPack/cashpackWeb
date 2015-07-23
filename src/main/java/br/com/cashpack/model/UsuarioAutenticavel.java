package br.com.cashpack.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UsuarioAutenticavel extends Usuario {

	@ManyToOne
	@NotNull
	private Credencial credencial;

	public static UsuarioAutenticavel findUsuarioAutenticavelByCredencial(
			Credencial credencial) {

		String sqlQuery = "SELECT u From UsuarioAutenticavel u WHERE U.credencial.login =:login AND U.credencial.senha =:senha";
		EntityManager manager = entityManager();

		TypedQuery<Agencia> query = manager
				.createQuery(sqlQuery, Agencia.class);
		query.setParameter("login", credencial.getLogin());
		query.setParameter("senha", credencial.getSenha());

		List<Agencia> agencias = query.getResultList();
		if (agencias != null && agencias.size() > 0) {
			return agencias.get(0);
		} else {
			return null;
		}
	}
}
