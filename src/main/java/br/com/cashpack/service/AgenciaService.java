package br.com.cashpack.service;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.model.Agencia;
import br.com.cashpack.model.Credencial;

@RooService(domainTypes = { br.com.cashpack.model.Agencia.class })
public interface AgenciaService {

	void cadastrar(Agencia agencia) throws CashPackException;

	void confirmarPinAgencia(Agencia agencia) throws CashPackException;
	
	List<Agencia> findAgenciasByIdDeGestor(Long idGestor);

}
