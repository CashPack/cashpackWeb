package br.com.cashpack.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@Inheritance(strategy = InheritanceType.JOINED)
@RooJpaActiveRecord(finders = { "findUsuariosByCodigoPinAndTelefone" })
public abstract class Usuario {

	/**	
     */
	@NotNull
	@OneToOne
	private Telefone telefone;

	/**
     */
	@NotNull
	@OneToOne
	private CodigoPIN codigoPin;

	public static Usuario findUsuarioByCodPaisAndCodAreaAndNumero(
			String codPais, String codArea, String numero) {
		String sqlQuery = "SELECT u From Usuario u WHERE u.telefone.codPais =:codPais AND u.telefone.codArea =:codArea AND u.telefone.numero =:numero";

		EntityManager manager = entityManager();

		TypedQuery<Usuario> query = manager
				.createQuery(sqlQuery, Usuario.class);
		query.setParameter("codPais", codPais);
		query.setParameter("codArea", codArea);
		query.setParameter("numero", numero);

		List<Usuario> usuarios = query.getResultList();
		if(usuarios != null && usuarios.size() > 0){
			return usuarios.get(0);
		} else{
			return null;
		}
	}
}
