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

import br.com.cashpack.model.UsuarioCashPack;
import br.com.cashpack.service.UsuarioCashPackService;
import br.com.cashpack.service.validator.CashPackException;

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

		String codPais = "";
		String codArea = "";
		String numero = "";

		JsonNode jsonNode;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			jsonNode = objectMapper.readTree(factory.createJsonParser(json));

			if (jsonNode.has("codPais")) {
				codPais = jsonNode.get("codPais").asText();
			}

			if (jsonNode.has("codArea")) {
				codPais = jsonNode.get("codArea").asText();
			}

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
			return new ResponseEntity<String>("{\"ERROR\": \"Json inválido\"}",
					headers, HttpStatus.PRECONDITION_FAILED);
		}

	}
}
