package br.com.cashpack.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.exception.CodigoPinDivergenteException;
import br.com.cashpack.exception.TelefoneException;
import br.com.cashpack.exception.UsuarioCashPackJaAtivadoException;
import br.com.cashpack.exception.UsuarioCashPackNaoEncontradoException;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.StatusUsuarioCashPack;
import br.com.cashpack.model.Telefone;
import br.com.cashpack.model.Usuario;
import br.com.cashpack.model.UsuarioCashPack;
import br.com.cashpack.service.validator.TelefoneValidator;
import br.com.cashpack.service.validator.UsuarioCashPackValidator;

public class UsuarioCashPackServiceImpl implements UsuarioCashPackService {

	private TelefoneValidator telefoneValidator = new TelefoneValidator();
	private UsuarioCashPackValidator usuarioCashPackValidator = new UsuarioCashPackValidator();

	@Autowired
	private SmsService smsSender;

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private CodigoPinService codigoPinService;

	public UsuarioCashPack cadastrar(String codTelefonoPais,
			String codTelefonoArea, String numeroTelefone)
			throws CashPackException {

		Telefone telefone = new Telefone();
		telefone.setCodPais(codTelefonoPais);
		telefone.setCodArea(codTelefonoArea);
		telefone.setNumero(numeroTelefone);
		this.telefoneValidator.validate(telefone);

		UsuarioCashPack usuarioCashPack;
		try {
			usuarioCashPack = this.findUsuarioCashPackByTelefone(
					codTelefonoPais, codTelefonoArea, numeroTelefone);
		} catch (Exception e) {
			usuarioCashPack = new UsuarioCashPack();
		}
		if (usuarioCashPack.getStatus() != null
				&& usuarioCashPack.getStatus() != StatusUsuarioCashPack.DESATIVADO) {

			throw new UsuarioCashPackJaAtivadoException(
					"Esse usuário já está cadastrado e com o PIN validado!");
		}
		this.usuarioCashPackValidator.validate(usuarioCashPack);

		CodigoPIN codigoPin = gerarPinAleatorio();
		if (usuarioCashPack.getCodigoPin() == null) {
			usuarioCashPack.setCodigoPin(codigoPin);
		} else {
			usuarioCashPack.getCodigoPin().setCodigo(codigoPin.getCodigo());
			usuarioCashPack.getCodigoPin().setDataQueFoiGerado(
					codigoPin.getDataQueFoiGerado());
		}

		if (usuarioCashPack.getTelefone() == null) {
			this.telefoneService.saveTelefone(telefone);
			usuarioCashPack.setTelefone(telefone);
		}
		usuarioCashPack.setStatus(StatusUsuarioCashPack.DESATIVADO);
		this.saveUsuarioCashPack(usuarioCashPack);

		this.smsSender.sendPin(usuarioCashPack);

		return usuarioCashPack;

	}

	private UsuarioCashPack findUsuarioCashPackByTelefone(String codPais,
			String codArea, String numero) {

		Usuario usuario = Usuario
				.findUsuarioCashPackByCodPaisAndCodAreaAndNumero(codPais,
						codArea, numero);

		if (usuario != null) {
			return (UsuarioCashPack) usuario;
		}

		return null;
	}

	private CodigoPIN gerarPinAleatorio() {
		char[] alfabeto = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		int qtdCaracteresDoPin = 5;
		String codigo = "";
		for (int i = 0; i < qtdCaracteresDoPin; i++) {
			Random gerador = new Random();
			int numero = gerador.nextInt(alfabeto.length - 1);

			if (numero < 0) {
				numero = 0;
			}
			codigo += alfabeto[numero];
		}
		CodigoPIN pin = new CodigoPIN();
		pin.setCodigo(codigo);
		pin.setDataQueFoiGerado(new Date());
		codigoPinService.saveCodigoPIN(pin);

		return pin;
	}

	@Override
	public void confirmarPin(String codPais, String codArea, String numero,
			String confirmacaoDoPin) throws CashPackException {

		Telefone telefone = new Telefone();
		telefone.setCodPais(codPais);
		telefone.setCodArea(codArea);
		telefone.setNumero(numero);
		this.telefoneValidator.validate(telefone);
		if (confirmacaoDoPin == null || confirmacaoDoPin.isEmpty()) {
			new TelefoneException("Código PIN é obrigatório!");
		}

		UsuarioCashPack usuarioCashPack;
		try {
			usuarioCashPack = this.findUsuarioCashPackByTelefone(codPais,
					codArea, numero);
		} catch (Exception e) {
			usuarioCashPack = null;
		}

		if (usuarioCashPack == null) {
			throw new UsuarioCashPackNaoEncontradoException(
					"Usuario CashPack não encontrado!");
		}

		if (!usuarioCashPack.getStatus().equals(
				StatusUsuarioCashPack.DESATIVADO)) {
			throw new UsuarioCashPackJaAtivadoException(
					"Usuário CashPack já está ativado!");
		}

		String codigo = "";
		if (usuarioCashPack.getCodigoPin() != null) {
			codigo = usuarioCashPack.getCodigoPin().getCodigo();
		}

		if (!codigo.equals(confirmacaoDoPin)) {
			throw new CodigoPinDivergenteException("Código PIN não confere!");
		}

		if (codigo.equals(confirmacaoDoPin)) {
			usuarioCashPackValidator
					.validarTempoDeExpiracaoDeUmPin(usuarioCashPack);

			usuarioCashPack.setStatus(StatusUsuarioCashPack.ATIVADO_SEM_CPF);
			this.saveUsuarioCashPack(usuarioCashPack);
		}
	}
}