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

import br.com.cashpack.model.Agencia;
import br.com.cashpack.service.AgenciaService;

@RooWebJson(jsonObject = Agencia.class)
@Controller
@RequestMapping("/agencia")
public class AgenciaController {

	@Autowired
	private AgenciaService usuarioCashPackService;

	@RequestMapping(value = "/cadastrarAgencia", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> cadastrarAgencia(@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		JsonNode jsonNode;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			jsonNode = objectMapper.readTree(factory.createJsonParser(json));


		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("{\"ERROR\": \"Json inválido\"}",
					headers, HttpStatus.PRECONDITION_FAILED);
		}
		return null;
	}
}
