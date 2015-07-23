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

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioAutenticavel;
import br.com.cashpack.model.adapter.UsuarioAutenticavelAdapter;
import br.com.cashpack.service.UsuarioAutenticavelService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RooWebJson(jsonObject = UsuarioAutenticavel.class)
@Controller
@RequestMapping("/usuarioAutenticavel")
public class UsuarioAutenticavelController {

	@Autowired
	private UsuarioAutenticavelService usuarioAutenticavelService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> login(@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		Gson gson = new Gson();
		Credencial credencial = gson.fromJson(json, Credencial.class);

		try {
			UsuarioAutenticavel usuarioAutenticavel = this.usuarioAutenticavelService
					.findUsuarioAutenticavelByCredencial(credencial);

			Gson gsonExt = null;
			{
				GsonBuilder builder = new GsonBuilder();
				builder.registerTypeAdapter(UsuarioAutenticavel.class,
						new UsuarioAutenticavelAdapter());
				gsonExt = builder.create();
			}
			String usuarioJson = gsonExt.toJson(usuarioAutenticavel,
					UsuarioAutenticavel.class);

			return new ResponseEntity<String>(usuarioJson, headers,
					HttpStatus.ALREADY_REPORTED);

		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.ALREADY_REPORTED);
		}
	}
}