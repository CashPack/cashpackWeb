package br.com.cashpack.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUsuarioCredenciavelsByCredencial" })
public abstract class UsuarioCredenciavel extends Usuario {

	/**
     */
	@NotNull
	@ManyToOne
	private Credencial credencial;

	public static TypedQuery<UsuarioCredenciavel> findUsuarioCredenciavelsByCredencial(
			Credencial credencial) {
		EntityManager em = entityManager();
		TypedQuery<UsuarioCredenciavel> q = em
				.createQuery(
						"SELECT o FROM UsuarioCredenciavel AS o WHERE o.credencial.login = :login AND o.credencial.senha = :senha",
						UsuarioCredenciavel.class);
		q.setParameter("login", credencial.getLogin());
		q.setParameter("senha", credencial.getSenha());
		return q;
	}
}
