package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.model.UsuarioCashPack;
import br.com.cashpack.service.validator.CashPackException;

@RooService(domainTypes = { br.com.cashpack.model.UsuarioCashPack.class })
public interface UsuarioCashPackService {

	UsuarioCashPack cadastrar(String codTelefonoPais, String codTelefonoArea,
			String numeroTelefone) throws CashPackException;
}
