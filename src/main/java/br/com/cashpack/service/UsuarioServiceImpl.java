package br.com.cashpack.service;

import br.com.cashpack.model.Usuario;

public class UsuarioServiceImpl implements UsuarioService {

	@Override
	public Usuario findUsuarioByTelefone(String codPais, String codArea,
			String numero) {

		return Usuario.findUsuarioByCodPaisAndCodAreaAndNumero(codPais,
				codArea, numero);

	}

}
