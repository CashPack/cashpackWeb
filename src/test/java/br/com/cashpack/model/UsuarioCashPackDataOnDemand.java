package br.com.cashpack.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import com.google.gson.Gson;

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
		ramoDeAtividade.setNome("Ramo de Atividade da Agência");
		ramoDeAtividade.setVersion(0);

		// COM CPF
		Agencia agencia = new Agencia();
		agencia.setRazaoSocial("Razão Social da Agência");
		agencia.setNomeFantasia("Noma Fantasia da Agência");
		agencia.setEmail("email@agencia.com");
		agencia.setNumeroDocumento("005.000.00-00");
		agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CPF);
		agencia.setTelefone(telefone);
		agencia.setRamoDeAtividade(ramoDeAtividade);

		Gson gson = new Gson();
		String jsonCPF = gson.toJson(agencia);
		System.out.println(jsonCPF);

		// COM CNPJ
		agencia.setNumeroDocumento("52.836.475/0001-16");
		agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CNPJ);

		String jsonCNPJ = gson.toJson(agencia);
		System.out.println(jsonCNPJ);
	}
	
	@Test
	public void imprimeJsonNoFormatoDeConfirmacaoDePinDeUmaAgencia(){
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
		agencia.setNumeroDocumento("005.000.00-00");
		agencia.setTipoDeDocumentoAgenciaEnum(TipoDeDocumentoDaAgenciaEnum.CPF);
		agencia.setTelefone(telefone);
		agencia.setRamoDeAtividade(ramoDeAtividade);
		agencia.setCodigoPin(codigoPIN);
		
		Gson gson = new Gson();
		String json = gson.toJson(agencia);

		System.out.println(json);
	}
}
