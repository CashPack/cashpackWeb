package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.exception.CodigoPinExpiradoException;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.Usuario;

@RooService(domainTypes = { br.com.cashpack.model.CodigoPIN.class })
public interface CodigoPinService {

	CodigoPIN gerarPinAleatorio();

	void validarTempoDeExpiracaoDeUmPin(Usuario usuario)
			throws CodigoPinExpiradoException;
}
