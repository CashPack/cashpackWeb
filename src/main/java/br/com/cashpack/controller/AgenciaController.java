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

	@Autowired
	private AgenciaService agenciaService;

	@RequestMapping(value = "/cadastrarAgencia", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> cadastrarAgencia(@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		Gson gson = new Gson();
		Agencia agencia = gson.fromJson(json, Agencia.class);
		try {
			
			agenciaService.cadastrar(agencia);
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
		} catch (CashPackException e) {
			e.printStackTrace();

			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.ALREADY_REPORTED);
		}
	}
}
