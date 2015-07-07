package br.com.cashpack.service;

import java.util.Date;
import java.util.Random;

import br.com.cashpack.exception.CodigoPinExpiradoException;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.Usuario;

public class CodigoPinServiceImpl implements CodigoPinService {

	public CodigoPIN gerarPinAleatorio() {
		char[] alfabeto = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
				'L', 'M', 'N', 'P', 'Q', 'R', 'T', 'V', 'W', 'X', 'Y', 'Z',
				'2', '3', '4', '6', '7', '8', '9' };

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
		this.saveCodigoPIN(pin);

		return pin;
	}

	public void validarTempoDeExpiracaoDeUmPin(Usuario usuario)
			throws CodigoPinExpiradoException {

		Date dataQuePinFoiEnviado = usuario.getCodigoPin()
				.getDataQueFoiGerado();
		Date dataAtual = new Date();

		long diff = dataQuePinFoiEnviado.getTime() - dataAtual.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		boolean ultrapassouTempoDeExpiracao = true;
		if (diffDays == 0) {
			if (diffHours >= -1 && diffHours <= 1) {
				if (diffMinutes <= 15 && diffMinutes > -15) {
					ultrapassouTempoDeExpiracao = false;
				}
			}
		}

		if (ultrapassouTempoDeExpiracao) {
			throw new CodigoPinExpiradoException(
					"PIN expirado! Por favor, cadastre-se novamente e um novo código PIN será enviado!");
		}
	}
}
