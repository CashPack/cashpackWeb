package br.com.cashpack.service.validator;

import br.com.cashpack.model.Telefone;
import br.com.cashpack.service.TelefoneService;

public class TelefoneValidator {

	private static final int TAM_MIN_COD_TELEFONO_PAIS = 2;
	private static final int TAM_MAX_COD_TELEFONO_PAIS = 4;

	private static final int TAM_MIN_COD_TELEFONO_AREA = 2;
	private static final int TAM_MAX_COD_TELEFONO_AREA = 3;

	private static final int TAM_MIN_NUMERO_TELEFONE = 8;
	private static final int TAM_MAX_NUMERO_TELEFONE = 9;

	public void validate(Telefone telefone, TelefoneService telefoneService)
			throws CashPackException {

		validateCodPais(telefone.getCodPais());
		validateCodArea(telefone.getCodArea());
		validateNumeroTelefone(telefone.getNumero());
	}

	private void validateNumeroTelefone(String numeroTelefone)
			throws TelefoneException {
		if (numeroTelefone == null || numeroTelefone.isEmpty()) {
			throw new TelefoneException("Número do telefone é obrigatório");
		} else {
			if (numeroTelefone.length() > TAM_MAX_NUMERO_TELEFONE) {
				throw new TelefoneException(
						"O número do telefone deve ter no máximo "
								+ TAM_MAX_NUMERO_TELEFONE + " dígitos");
			}

			if (numeroTelefone.length() < TAM_MIN_NUMERO_TELEFONE) {
				throw new TelefoneException(
						"O número do telefone deve ter no mínimo "
								+ TAM_MIN_NUMERO_TELEFONE + " dígitos");
			}
		}
	}

	private void validateCodArea(String codTelefonoArea)
			throws TelefoneException {
		if (codTelefonoArea == null || codTelefonoArea.isEmpty()) {
			throw new TelefoneException(
					"Código de área do telefone é obrigatório");
		} else {
			if (codTelefonoArea.length() > TAM_MAX_COD_TELEFONO_AREA) {
				throw new TelefoneException(
						"Código de área do telefone deve ter no máximo "
								+ TAM_MAX_COD_TELEFONO_AREA + " dígitos");
			}

			if (codTelefonoArea.length() < TAM_MIN_COD_TELEFONO_AREA) {
				throw new TelefoneException(
						"Código do país do telefone deve ter no mínimo "
								+ TAM_MAX_COD_TELEFONO_AREA + " dígitos");
			}
		}
	}

	private void validateCodPais(String codTelefonoPais)
			throws TelefoneException {

		if (codTelefonoPais == null || codTelefonoPais.isEmpty()) {
			throw new TelefoneException(
					"Código do país do telefone é obrigatório");
		} else {
			if (codTelefonoPais.length() > TAM_MAX_COD_TELEFONO_PAIS) {
				throw new TelefoneException(
						"Código do país do telefone deve ter no máximo "
								+ TAM_MAX_COD_TELEFONO_PAIS + " dígitos");
			}

			if (codTelefonoPais.length() < TAM_MIN_COD_TELEFONO_PAIS) {
				throw new TelefoneException(
						"Código do país do telefone deve ter no mínimo "
								+ TAM_MAX_COD_TELEFONO_PAIS + " dígitos");
			}
		}
	}
}