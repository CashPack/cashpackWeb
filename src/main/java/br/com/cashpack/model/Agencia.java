package br.com.cashpack.model;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	
	@OneToOne
	private Credencial credencial;

	public static Agencia findAgenciaByNumeroDocumento(String numeroDocumento) {
		String sql = "SELECT a FROM Agencia a WHERE a.numeroDocumento =:numeroDocumento";

		EntityManager manager = entityManager();

		TypedQuery<Agencia> query = manager.createQuery(sql, Agencia.class);
		query.setParameter("numeroDocumento", numeroDocumento);

		return query.getSingleResult();
	}
}
