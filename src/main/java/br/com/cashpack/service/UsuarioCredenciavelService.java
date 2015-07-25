package br.com.cashpack.service;
import javax.security.auth.login.CredentialException;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioCredenciavel;

@RooService(domainTypes = { br.com.cashpack.model.UsuarioCredenciavel.class })
public interface UsuarioCredenciavelService {

	UsuarioCredenciavel findUsuarioCredenciavelByCredencial(Credencial credencial) throws CredentialException;
}
