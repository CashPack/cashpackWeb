package br.com.cashpack.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.model.UsuarioCashPack;

@RooService(domainTypes = { br.com.cashpack.model.SMS.class })
public interface SmsService {

	void sendPin(UsuarioCashPack usuarioCashPack);

}
