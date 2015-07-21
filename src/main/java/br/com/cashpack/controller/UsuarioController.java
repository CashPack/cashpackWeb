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
import br.com.cashpack.model.Usuario;
import br.com.cashpack.service.UsuarioCashPackService;

@RooWebJson(jsonObject = Usuario.class)
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioCashPackService usuarioCashPackService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> login(
			@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

//		try {
//
//				return new ResponseEntity<String>(null);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			return new ResponseEntity<String>("{\"ERROR\": \"Json inválido\"}", headers, HttpStatus.PRECONDITION_FAILED);
//		}
		return null;
	}

	@RequestMapping(value = "/confirmarPinUsuarioCashPack", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> confirmarPinUsuarioCashPack(
			@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

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
			String numero = "";
			if (jsonNode.has("numeroTelefone")) {
				String numeroTelefone = jsonNode.get("numeroTelefone").asText();

				codArea = (String) numeroTelefone.replace(" ", "").subSequence(0, 3);
				codArea = codArea.replace("(", "").replace(")", "").replace(" ", "");

				numero = numeroTelefone.replace(" ", "").substring(4).replace("(", "").replace(")", "").replace("-", "");
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
