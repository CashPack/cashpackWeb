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
import br.com.cashpack.model.Usuario;
import br.com.cashpack.model.UsuarioCredenciavel;
import br.com.cashpack.model.adapter.UsuarioCredenciavelAdapter;
import br.com.cashpack.service.UsuarioCredenciavelService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RooWebJson(jsonObject = UsuarioCredenciavel.class)
@Controller
@RequestMapping("/usuarioCredenciavel")
public class UsuarioCredenciavelController {

	@Autowired
	private UsuarioCredenciavelService usuarioCredenciavelService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> cadastrarUsuarioCashPack(
			@RequestBody String json) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			Gson gson = new Gson();
			Credencial credencial = gson.fromJson(json, Credencial.class);
			UsuarioCredenciavel usuarioCredenciavel = this.usuarioCredenciavelService
					.findUsuarioCredenciavelByCredencial(credencial);

			Gson gsonExt = null;
			{
				GsonBuilder builder = new GsonBuilder();
				builder.registerTypeAdapter(UsuarioCredenciavel.class,
						new UsuarioCredenciavelAdapter());
				gsonExt = builder.create();
			}
			String usuarioJson = gsonExt.toJson(usuarioCredenciavel,
					UsuarioCredenciavel.class);

			return new ResponseEntity<String>(usuarioJson, headers,
					HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\": \"" + e.getMessage()
					+ "\"}", headers, HttpStatus.PRECONDITION_FAILED);
		}
	}
}