package br.com.cashpack.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cashpack.model.RamoDeAtividade;

@RooWebJson(jsonObject = RamoDeAtividade.class)
@Controller
@RequestMapping("/ramoDeAtividade")
public class RamoDeAtividadeController {

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<RamoDeAtividade> result = RamoDeAtividade
					.findAllRamoDeAtividades();
			return new ResponseEntity<String>(
					RamoDeAtividade.toJsonArray(result), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":" + e.getMessage()
					+ "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
