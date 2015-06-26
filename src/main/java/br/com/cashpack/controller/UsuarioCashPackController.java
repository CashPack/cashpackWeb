package br.com.cashpack.controller;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.model.UsuarioCashPack;
import br.com.cashpack.service.UsuarioCashPackService;

@RooWebJson(jsonObject = UsuarioCashPack.class)
@Controller
@RequestMapping("/usuariocashpacks")
public class UsuarioCashPackController {

	@Autowired
	private UsuarioCashPackService usuarioCashPackService;

	@RequestMapping(value = "/cadastrarUsuarioCashPack", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> cadastrarUsuarioCashPack(
			@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("charset", "UTF-8");

		JsonNode jsonNode;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			jsonNode = objectMapper.readTree(factory.createJsonParser(json));

			String codPais = "";
			if (jsonNode.has("codPais")) {
				codPais = jsonNode.get("codPais").asText();
			}

			String codArea = "";
			if (jsonNode.has("codArea")) {
				codArea = jsonNode.get("codArea").asText();
			}

			String numero = "";
			if (jsonNode.has("numeroTelefone")) {
				numero = jsonNode.get("numeroTelefone").asText();
			}

			try {
				usuarioCashPackService.cadastrar(codPais, codArea, numero);
				return new ResponseEntity<String>(headers, HttpStatus.CREATED);
			} catch (CashPackException e) {
				e.printStackTrace();

				return new ResponseEntity<String>("{\"ERROR\": \""
						+ e.getMessage() + "\"}", headers,
						HttpStatus.ALREADY_REPORTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("{\"ERROR\": \"Json inválido\"}",
					headers, HttpStatus.PRECONDITION_FAILED);
		}
	}

	@RequestMapping(value = "/confirmarPinUsuarioCashPack", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> confirmarPinUsuarioCashPack(
			@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("charset", "UTF-8");

		JsonNode jsonNode;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			jsonNode = objectMapper.readTree(factory.createJsonParser(json));

			String codPais = "";
			if (jsonNode.has("codPais")) {
				codPais = jsonNode.get("codPais").asText();
			}

			String codArea = "";
			if (jsonNode.has("codArea")) {
				codArea = jsonNode.get("codArea").asText();
			}

			String numero = "";
			if (jsonNode.has("numeroTelefone")) {
				numero = jsonNode.get("numeroTelefone").asText();
			}

			String confirmacaoDoPin = "";
			if (jsonNode.has("confirmacaoDoPin")) {
				confirmacaoDoPin = jsonNode.get("confirmacaoDoPin").asText();
			}
			try {
				this.usuarioCashPackService.confirmarPin(codPais, codArea,
						numero, confirmacaoDoPin);
				return new ResponseEntity<String>(headers, HttpStatus.CREATED);
			} catch (CashPackException e) {
				e.printStackTrace();

				return new ResponseEntity<String>("{\"ERROR\": \""
						+ e.getMessage() + "\"}", headers,
						HttpStatus.ALREADY_REPORTED);
			}

		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\": \"Json inválido\"}",
					headers, HttpStatus.PRECONDITION_FAILED);
		}
	}
}
