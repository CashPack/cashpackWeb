package br.com.cashpack.service;

import javax.security.auth.login.CredentialException;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioCredenciavel;

public class UsuarioCredenciavelServiceImpl implements
		UsuarioCredenciavelService {

	@Override
	public UsuarioCredenciavel findUsuarioCredenciavelByCredencial(
			Credencial credencial) throws CredentialException {
		if (credencial == null) {
			throw new CredentialException("Credencial está null!");
		} else if (credencial.getLogin() == null
				|| credencial.getLogin().isEmpty()) {
			throw new CredentialException("Login é um campo obrigatório");
		} else if (credencial.getSenha() == null
				|| credencial.getSenha().isEmpty()) {
			throw new CredentialException("Senha é um campo obrigatório");
		}

		try {
			return UsuarioCredenciavel.findUsuarioCredenciavelsByCredencial(
					credencial).getSingleResult();
		} catch (Exception e) {
			throw new CredentialException("Login não realizado!");
		}
	}
}
