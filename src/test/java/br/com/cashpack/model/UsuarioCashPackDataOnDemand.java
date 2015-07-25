package br.com.cashpack.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import br.com.cashpack.model.adapter.UsuarioCredenciavelAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RooDataOnDemand(entity = UsuarioCashPack.class)
public class UsuarioCashPackDataOnDemand {

	@Test
	public void verificarColetaDoCodAreaEDooNumeroDeTelefone() {
		String numeroComArea = "(83) 98874 - 6463";

		String area = "";
		String numero = "";

		area = (String) numeroComArea.subSequence(0, 3);
		area = area.replace("(", "").replace(")", "").replace(" ", "");

		numero = numeroComArea.substring(4).replace("(", "").replace(")", "")
				.replace("-", "").replace(" ", "");

		Assert.assertEquals("83", area);
		Assert.assertEquals("988746463", numero);
	}

	@Test
	public void imprimeJsonNoFormatoDeRecebimentoDeCadastroDeUmUsuarioCashPack() {
		Telefone telefone = new Telefone();
		telefone.setCodPais("55");
		telefone.setNumero("(83) 98874 - 6463");

		UsuarioCashPack usuarioCashPack = new UsuarioCashPack();
		usuarioCashPack.setTelefone(telefone);

		Gson gson = new Gson();
		String json = gson.toJson(usuarioCashPack);
		System.out.println(json);
	}

	@Test
	public void imprimeJsonNoFormatoDeRecebimentoDeCadastroDeUmaAgencia() {
		Telefone telefone = new Telefone();
		telefone.setCodPais("55");
		telefone.setNumero("(83) 98874 - 6463");

		RamoDeAtividade ramoDeAtividade = new RamoDeAtividade();
		ramoDeAtividade.setId(1L);
		ramoDeAtividade.setNome("Ramo 01");
		ramoDeAtividade.setVersion(0);

		Endereco endereco = new Endereco();
		endereco.setBairro("Valentina");
		endereco.setCep("58064190");
		endereco.setLogradouro("Rua Manoel Inácio dos Santos");
		endereco.setNumero("167");
		endereco.setPais("Brasil");
		endereco.setSiglaUF("PB");
		endereco.setMunicipio("João Pessoa");
		endereco.setVersion(0);
		
		Gestor gestor = new Gestor();
		gestor.setId(1L);
		gestor.setVersion(0);

		// COM CPF
		Agencia agencia = new Agencia();
		agencia.setRazaoSocial("Razão Social da Agência");
		agencia.setNomeFantasia("Noma Fantasia da Agência");
		agencia.setEmail("email@agencia.com");
		agencia.setNumeroDocumento("005.000.000-00");
		agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CPF);
		agencia.setTelefone(telefone);
		agencia.setRamoDeAtividade(ramoDeAtividade);
		agencia.setEndereco(endereco);
		agencia.setGestor(gestor);

		Gson gson = new Gson();
		String jsonCPF = gson.toJson(agencia);
		System.out.println(jsonCPF);

		// // COM CNPJ
		// agencia.setNumeroDocumento("52.836.475/0001-16");
		// agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CNPJ);

		String jsonCNPJ = gson.toJson(agencia);
		System.out.println(jsonCNPJ);
	}

	@Test
	public void imprimeJsonNoFormatoDeConfirmacaoDePinDeUmaAgencia() {
		Telefone telefone = new Telefone();
		telefone.setCodPais("55");
		telefone.setNumero("(83) 98874 - 6463");

		RamoDeAtividade ramoDeAtividade = new RamoDeAtividade();
		ramoDeAtividade.setId(1L);
		ramoDeAtividade.setNome("Ramo de Atividade da Agência");
		ramoDeAtividade.setVersion(0);

		CodigoPIN codigoPIN = new CodigoPIN();
		codigoPIN.setCodigo("ASD876");

		// COM CPF
		Agencia agencia = new Agencia();
		agencia.setRazaoSocial("Razão Social da Agência");
		agencia.setNomeFantasia("Noma Fantasia da Agência");
		agencia.setEmail("email@agencia.com");
		agencia.setNumeroDocumento("005.000.000-00");
		agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CPF);
		agencia.setTelefone(telefone);
		agencia.setRamoDeAtividade(ramoDeAtividade);
		agencia.setCodigoPin(codigoPIN);

		Gson gson = new Gson();
		String json = gson.toJson(agencia);

		System.out.println(json);
	}

	@Test
	public void imprimeJsonNoFormatoDeCadastroGestor() {
		Gestor gestor = new Gestor();
		gestor.setCnpj("12345678901112");
		gestor.setRazaoSocial("Valor da Razão Social");
		gestor.setNomeFantasia("Raul Glu glu");
		gestor.setEmail("email@cashpack.com.br");

		Telefone telefone = new Telefone();
		telefone.setCodPais("55");
		telefone.setNumero("988746463");
		gestor.setTelefone(telefone);

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Daura Saraíva, 393-471 - Aeroporto, Bayeux - PB, 58308-130, Brasil");
		// endereco.setLogradouro("Rua Manoel Inácio dos Santos, 199 - Valentina de Figueiredo, João Pessoa - PB, 58064-190, Brasil");
		gestor.setEndereco(endereco);

		Gson gson = new Gson();
		System.out.println(gson.toJson(gestor));
	}

	@Test
	public void imprimeJsonNoConfirmacaoDePinDeGestor() {
		CodigoPIN codigoPin = new CodigoPIN();
		codigoPin.setCodigo("asdfgh");

		Telefone telefone = new Telefone();
		telefone.setCodPais("55");
		telefone.setNumero("988746463");

		Gestor gestor = new Gestor();
		gestor.setTelefone(telefone);
		gestor.setCodigoPin(codigoPin);
		gestor.setAceitouOsTermosDeContrato(true);

		Gson gson = new Gson();
		System.out.println(gson.toJson(gestor));
	}

	@Test
	public void testSerialize() {
		Gestor gestor = criarGestorValido();
		Agencia agencia = criarAgenciaValida(gestor);

		Usuario usuarios[] = new Usuario[] { gestor, agencia };

		Gson gsonExt = null;
		{
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Usuario.class,
					new UsuarioCredenciavelAdapter());
			gsonExt = builder.create();
		}

		for (Usuario usuario : usuarios) {
			String usuarioJson = gsonExt.toJson(usuario, Usuario.class);
			System.out.println(usuarioJson + "\n\n");
			// Usuario usuario2 = gsonExt.fromJson(usuarioJson, Usuario.class);
			// System.out.println(usuario2);
		}
	}

	private Agencia criarAgenciaValida(Gestor gestor) {

		Agencia agencia = new Agencia();
		agencia.setId(1L);
		agencia.setVersion(1);
		agencia.setEmail("fernando.dsfw@gmail.com");
		agencia.setNomeFantasia("Nome fantasia");
		agencia.setRazaoSocial("Razão Social");
		agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CNPJ);
		agencia.setNumeroDocumento("12345678901112");
		agencia.setStatusAgencia(StatusAgencia.ATIVADO);
		agencia.setGestor(gestor);

		RamoDeAtividade ramoDeAtividade = new RamoDeAtividade();
		ramoDeAtividade.setId(1L);
		ramoDeAtividade.setVersion(0);
		ramoDeAtividade.setNome("Padaria");
		agencia.setRamoDeAtividade(ramoDeAtividade);

		Telefone telefone = new Telefone();
		telefone.setId(1L);
		telefone.setVersion(0);
		telefone.setCodPais("55");
		telefone.setCodArea("83");
		telefone.setNumero("988746463");
		agencia.setTelefone(telefone);

		CodigoPIN codigoPin = new CodigoPIN();
		codigoPin.setId(1L);
		codigoPin.setVersion(0);
		codigoPin.setDataQueFoiGerado(new Date());
		codigoPin.setCodigo("asdfgh");
		agencia.setCodigoPin(codigoPin);

		Credencial credencial = new Credencial();
		credencial.setId(1L);
		credencial.setVersion(0);
		credencial.setLogin("algumLogin");
		credencial.setSenha("algumaSenha");
		agencia.setCredencial(credencial);
		return agencia;
	}

	private Gestor criarGestorValido() {
		Gestor gestor = new Gestor();
		gestor.setId(1L);
		gestor.setVersion(0);

		Telefone telefone = new Telefone();
		telefone.setId(1L);
		telefone.setVersion(0);
		telefone.setCodPais("55");
		telefone.setCodArea("83");
		telefone.setNumero("988746463");

		gestor.setTelefone(telefone);
		gestor.setNomeFantasia("Mercearia do João");
		gestor.setRazaoSocial("Fulano de Tal - ME");
		gestor.setCnpj("12345678901112");
		gestor.setEmail("fernando.dsfw@gmail.com");
		gestor.setAceitouOsTermosDeContrato(true);
		gestor.setStatusGestorEnum(StatusGestorEnum.ATIVADO);

		CodigoPIN codigoPin = new CodigoPIN();
		codigoPin.setId(1L);
		codigoPin.setVersion(0);
		codigoPin.setDataQueFoiGerado(new Date());
		codigoPin.setCodigo("asdfgh");
		gestor.setCodigoPin(codigoPin);

		Credencial credencial = new Credencial();
		credencial.setId(1L);
		credencial.setVersion(0);
		credencial.setLogin("algumLogin");
		credencial.setSenha("algumaSenha");
		gestor.setCredencial(credencial);
		return gestor;
	}
}
