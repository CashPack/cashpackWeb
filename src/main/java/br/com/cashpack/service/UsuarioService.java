package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.model.Usuario;

@RooService(domainTypes = { br.com.cashpack.model.Usuario.class })
public interface UsuarioService {

	Usuario findUsuarioByTelefone(String codPais, String codArea, String numero);
}
