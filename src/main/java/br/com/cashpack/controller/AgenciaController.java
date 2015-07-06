package br.com.cashpack.controller;

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
import br.com.cashpack.model.Agencia;
import br.com.cashpack.service.AgenciaService;

import com.google.gson.Gson;

@RooWebJson(jsonObject = Agencia.class)
@Controller
@RequestMapping("/agencia")
public class AgenciaController {

	private HttpHeaders headers;
	
	@Autowired
	private AgenciaService agenciaService;
	
	private void init() {
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
	}

	@RequestMapping(value = "/cadastrarAgencia", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> cadastrarAgencia(@RequestBody String json) {
		init();

		Gson gson = new Gson();
		Agencia agencia = gson.fromJson(json, Agencia.class);

		String numeroTelefone = agencia.getTelefone().getNumero();
		String codArea = (String) numeroTelefone.subSequence(0, 3);
		codArea = codArea.replace("(", "").replace(")", "").replace(" ", "");

		String numero = "";
		numero = numeroTelefone.substring(4).replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
		agencia.getTelefone().setCodArea(codArea);

		try {
			agenciaService.cadastrar(agencia);
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);

		} catch (CashPackException e) {
			e.printStackTrace();

			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.ALREADY_REPORTED);
		}
	}

	@RequestMapping(value = "/confirmarPinAgencia", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> confirmarPinAgencia(@RequestBody String json) {
		init();
		
		Gson gson = new Gson();
		Agencia agencia = gson.fromJson(json, Agencia.class);

		try {
			agenciaService.confirmarPinAgencia(agencia);
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch (CashPackException e) {
			e.printStackTrace();

			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.ALREADY_REPORTED);
		}

	}
}
