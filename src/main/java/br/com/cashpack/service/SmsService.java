package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.model.Usuario;

@RooService(domainTypes = { br.com.cashpack.model.SMS.class })
public interface SmsService {

	void sendPin(Usuario usuario);

}
