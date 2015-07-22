package br.com.cashpack.service.validator;

import br.com.cashpack.model.Endereco;

public class EnderecoValidator {

	private static final int COMPRIMENTO_MAXIMO_NUMERO = 12;
	private static final int COMPRIMENTO_MAXIMO_SIGLA_UF = 2;
	private static final int COMPRIMENTO_MAXIMO_CEP = 8;

	public void validate(Endereco endereco) throws EnderecoException {
		if (endereco == null) {
			throw new EnderecoException("Endereço está null!");
		}

		if (endereco.getLogradouro() == null
				|| endereco.getLogradouro().isEmpty()) {

			throw new EnderecoException("Logradouro é obrigatório!");
		}

		validarNumero(endereco);

		if (endereco.getBairro() == null || endereco.getBairro().isEmpty()) {

			throw new EnderecoException("Bairro é obrigatório!");
		}

		if (endereco.getMunicipio() == null
				|| endereco.getMunicipio().isEmpty()) {

			throw new EnderecoException("Município é obrigatório!");
		}

		validarSiglaUF(endereco);

		validarCEP(endereco);

	}

	private void validarCEP(Endereco endereco) throws EnderecoException {
		if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
			throw new EnderecoException("CEP é obrigatório!");
		} else {
			endereco.setCep(endereco.getCep().replace("-", "").replace(" ", ""));
			if (endereco.getSiglaUF().length() > COMPRIMENTO_MAXIMO_CEP) {
				throw new EnderecoException("CEP deve ter no máximo "
						+ COMPRIMENTO_MAXIMO_CEP + " caracteres!");
			}
		}
	}

	private void validarSiglaUF(Endereco endereco) throws EnderecoException {
		if (endereco.getSiglaUF() == null || endereco.getSiglaUF().isEmpty()) {
			throw new EnderecoException("Unidade Federativa é obrigatório!");
		} else {
			if (endereco.getSiglaUF().length() > COMPRIMENTO_MAXIMO_SIGLA_UF) {
				throw new EnderecoException(
						"Unidade Federativa deve ter no máximo "
								+ COMPRIMENTO_MAXIMO_SIGLA_UF + " caracteres!");
			}
		}
	}

	private void validarNumero(Endereco endereco) throws EnderecoException {
		if (endereco.getNumero() == null || endereco.getNumero().isEmpty()) {
			throw new EnderecoException("Número é obrigatório!");
		} else {
			if (endereco.getNumero().length() > COMPRIMENTO_MAXIMO_NUMERO) {
				throw new EnderecoException("Número deve ter no máximo "
						+ COMPRIMENTO_MAXIMO_NUMERO + " caracteres!");
			}
		}
	}
}