package br.com.cashpack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cashpack.model.Gestor;
import br.com.cashpack.service.AgenciaService;
import br.com.cashpack.service.GestorService;

import com.google.gson.Gson;

@RooWebJson(jsonObject = Gestor.class)
@Controller
@RequestMapping("/gestor")
public class GestorController {

	@Autowired
	private GestorService gestorService;

	private HttpHeaders headers;

	@Autowired
	private AgenciaService agenciaService;

	private void init() {
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
	}

	@RequestMapping(value = "/cadastrarGestor", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> cadastrarGestor(@RequestBody String json) {
		init();

		Gson gson = new Gson();
		Gestor gestor = gson.fromJson(json, Gestor.class);
		try {
			this.gestorService.cadastrarGestor(gestor);
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.ALREADY_REPORTED);
		}
	}

	@RequestMapping(value = "/confirmarPinGestor", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> confirmarPinGestor(@RequestBody String json) {
		init();

		Gson gson = new Gson();
		Gestor gestor = gson.fromJson(json, Gestor.class);
		try {
			gestorService.confirmarPinGestor(gestor);
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.ALREADY_REPORTED);
		}
	}
	
	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<Gestor> result = Gestor
					.findAllGestors();
			return new ResponseEntity<String>(
					Gestor.toJsonArray(result), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":" + e.getMessage()
					+ "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
