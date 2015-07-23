package br.com.cashpack.service;
import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.model.Gestor;

@RooService(domainTypes = { br.com.cashpack.model.Gestor.class })
public interface GestorService {
	
	void cadastrarGestor(Gestor gestor) throws CashPackException;

	void confirmarPinGestor(Gestor gestor) throws CashPackException;
	
}
