package br.com.cashpack.service;

import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.UsuarioAutenticavel;

public class UsuarioAutenticavelServiceImpl implements
		UsuarioAutenticavelService {

	@Override
	public UsuarioAutenticavel findUsuarioAutenticavelByCredencial(
			Credencial credencial) throws UsuarioAutenticavelException {
		if (credencial == null) {
			throw new UsuarioAutenticavelException("Credencial está null!");
		} else if (credencial.getLogin() == null
				|| credencial.getLogin().isEmpty()) {
			throw new UsuarioAutenticavelException(
					"Login é um campo obrigatório!");
		} else if (credencial.getSenha() == null
				|| credencial.getSenha().isEmpty()) {
			throw new UsuarioAutenticavelException(
					"Senha é um campo obrigatório!");
		}

		return UsuarioAutenticavel
				.findUsuarioAutenticavelByCredencial(credencial);
	}

}
