package br.com.cashpack.service;
import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.model.Agencia;

@RooService(domainTypes = { br.com.cashpack.model.Agencia.class })
public interface AgenciaService {

	void cadastrar(Agencia agencia) throws CashPackException;
}
