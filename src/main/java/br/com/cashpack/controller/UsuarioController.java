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

import br.com.cashpack.model.Agencia;
import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.Gestor;
import br.com.cashpack.model.Usuario;
import br.com.cashpack.service.AgenciaException;
import br.com.cashpack.service.AgenciaService;
import br.com.cashpack.service.GestorService;

import com.google.gson.Gson;

@RooWebJson(jsonObject = Usuario.class)
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private AgenciaService agenciaService;

	@Autowired
	private GestorService gestorService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> login(@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		Gson gson = new Gson();
		Credencial credencial = gson.fromJson(json, Credencial.class);

		try {
			Agencia agencia;
			agencia = this.agenciaService.findAgenciaByCredencial(credencial);

			Gestor gestor = null;

			if (agencia != null) {
				return new ResponseEntity<String>(agencia.toJson(), headers,
						HttpStatus.CREATED);
			} else {
				gestor = gestorService.findGestorByCredencial(credencial);
			}

			if (gestor != null) {
				return new ResponseEntity<String>(gestor.toJson(), headers,
						HttpStatus.CREATED);
			}

			return new ResponseEntity<String>(
					"{\"ERROR\": \"Usuário não encontrado!\"}", headers,
					HttpStatus.PRECONDITION_FAILED);

		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\": \"Json inválido\"}",
					headers, HttpStatus.PRECONDITION_FAILED);
		}
	}

}
