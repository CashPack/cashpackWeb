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
public class Agencia extends Usuario {

	@NotNull
	private String nomeFantasia;

	@NotNull
	private String razaoSocial;

	@NotNull
	private String email;

	@NotNull
	@Size(min = 11, max = 14)
	private String numeroDocumento;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoDeDocumentoDaAgenciaEnum tipoDeDocumentoAgenciaEnum;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusAgencia statusAgencia;

	@ManyToOne
	@NotNull
	private RamoDeAtividade ramoDeAtividade;

	@ManyToOne
	@NotNull
	private Gestor gestor;

	@ManyToOne
	@NotNull
	private Endereco endereco;

	@ManyToOne
	@NotNull
	private Credencial credencial;

	public static Agencia findAgenciaByNumeroDocumento(String numeroDocumento) {
		String sql = "SELECT a FROM Agencia a WHERE a.numeroDocumento =:numeroDocumento";
		EntityManager manager = entityManager();
		TypedQuery<Agencia> query = manager.createQuery(sql, Agencia.class);
		query.setParameter("numeroDocumento", numeroDocumento);
		return query.getSingleResult();
	}

	public static List<Agencia> findAgenciasByGestorId(Long idGestor) {
		String sql = "SELECT a FROM Agencia a WHERE a.gestor.id =:idGestor";
		EntityManager manager = entityManager();
		TypedQuery<Agencia> query = manager.createQuery(sql, Agencia.class);
		query.setParameter("idGestor", idGestor);
		return query.getResultList();
	}

	public static Agencia findAgenciaByCredencial(Credencial credencial) {
		String sqlQuery = "SELECT a From Agencia a WHERE a.credencial.login =:login AND a.credencial.senha =:senha";
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
