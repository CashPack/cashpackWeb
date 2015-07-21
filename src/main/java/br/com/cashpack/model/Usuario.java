package br.com.cashpack.model;

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

		return query.getSingleResult();
	}

//	@Override
//	public JsonElement serialize(Usuario src, Type typeOfSrc,
//			JsonSerializationContext context) {
//
//		JsonObject retValue = new JsonObject();
//		String className = src.getClass().getCanonicalName();
//		retValue.addProperty("Usuario", className);
//		JsonElement elem = context.serialize(src);
//		retValue.add("Instance", elem);
//		return retValue;
//	}
//
//	@Override
//	public Usuario deserialize(JsonElement json, Type typeOfT,
//			JsonDeserializationContext context) throws JsonParseException {
//		JsonObject jsonObject = json.getAsJsonObject();
//		JsonPrimitive prim = (JsonPrimitive) jsonObject.get("Usuario");
//		String className = prim.getAsString();
//
//		Class<?> klass = null;
//		try {
//			klass = Class.forName(className);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			throw new JsonParseException(e.getMessage());
//		}
//		return context.deserialize(jsonObject.get("Instance"), klass);
//	}
}
