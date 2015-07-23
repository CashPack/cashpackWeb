package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioAutenticavel;

@RooService(domainTypes = { br.com.cashpack.model.UsuarioAutenticavel.class })
public interface UsuarioAutenticavelService {

	UsuarioAutenticavel findUsuarioAutenticavelByCredencial(Credencial credencial)
			throws UsuarioAutenticavelException;
}
