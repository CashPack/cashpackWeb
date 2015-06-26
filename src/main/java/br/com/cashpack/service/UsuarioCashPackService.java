package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.model.UsuarioCashPack;

@RooService(domainTypes = { br.com.cashpack.model.UsuarioCashPack.class })
public interface UsuarioCashPackService {

	UsuarioCashPack cadastrar(String codTelefonoPais, String codTelefonoArea,
			String numeroTelefone) throws CashPackException;

	void confirmarPin(String codPais, String codArea, String numero,
			String confirmacaoDoPin) throws CashPackException;
}
