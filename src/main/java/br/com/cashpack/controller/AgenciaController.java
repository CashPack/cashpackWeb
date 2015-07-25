package br.com.cashpack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/pesquisarAgenciaPorIdDeGestor/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> pesquisarAgenciaPorIdDeGestor(
			@PathVariable("idGestor") Long idGestor) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<Agencia> agencias = agenciaService
					.findAgenciasByIdDeGestor(idGestor);
			if (agencias == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}

			Gson gson = new Gson();
			return new ResponseEntity<String>(gson.toJson(agencias), headers,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":" + e.getMessage()
					+ "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<Agencia> agencias = agenciaService
					.findAgenciasByIdDeGestor(id);
			if (agencias == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}

			Gson gson = new Gson();
			return new ResponseEntity<String>(gson.toJson(agencias), headers,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":" + e.getMessage()
					+ "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
