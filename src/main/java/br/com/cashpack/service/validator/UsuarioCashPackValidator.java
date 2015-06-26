package br.com.cashpack.service.validator;

import java.util.Calendar;
import java.util.Date;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.exception.CodigoPinEmitidoRecentementeException;
import br.com.cashpack.exception.CodigoPinExpiradoException;
import br.com.cashpack.model.UsuarioCashPack;

public class UsuarioCashPackValidator implements CashPackValidator {

	@Override
	public void validate(Object object) throws CashPackException {
		UsuarioCashPack usuarioCashPack = (UsuarioCashPack) object;
		verificarUsuarioCashPackComPinEmitidoRecentemente(usuarioCashPack);
	}

	private void verificarUsuarioCashPackComPinEmitidoRecentemente(
			UsuarioCashPack usuarioCashPack)
			throws CodigoPinEmitidoRecentementeException {

		if (usuarioCashPack.getCodigoPin() != null) {
			verificarTempoDeEnvioDoUltimoPin(usuarioCashPack);
		}
	}

	private void verificarTempoDeEnvioDoUltimoPin(
			UsuarioCashPack usuarioCashPack)
			throws CodigoPinEmitidoRecentementeException {

		Date dataQuePinFoiEnviado = usuarioCashPack.getCodigoPin()
				.getDataQueFoiGerado();
		Calendar calendarDoMomentoQueFoiEnviado = Calendar.getInstance();
		calendarDoMomentoQueFoiEnviado.setTime(dataQuePinFoiEnviado);

		Calendar calendarAtual = Calendar.getInstance();

		int diferencaEntreAno = calendarDoMomentoQueFoiEnviado
				.get(Calendar.YEAR) - calendarAtual.get(Calendar.YEAR);
		int diferencaEntreMes = calendarDoMomentoQueFoiEnviado
				.get(Calendar.MONTH) - calendarAtual.get(Calendar.MONTH);
		int diferencaEntreDias = calendarDoMomentoQueFoiEnviado
				.get(Calendar.DAY_OF_MONTH)
				- calendarAtual.get(Calendar.DAY_OF_MONTH);

		if (diferencaEntreAno == 0 && diferencaEntreMes == 0
				&& diferencaEntreDias == 0) {

			throw new CodigoPinEmitidoRecentementeException(
					"Tempo mínimo para emissão de um novo PIN ainda não foi atingido! Tempo mínimo: 1 dia");
		}
	}

	public void validarTempoDeExpiracaoDeUmPin(UsuarioCashPack usuarioCashPack)
			throws CodigoPinExpiradoException {
		Date dataQuePinFoiEnviado = usuarioCashPack.getCodigoPin()
				.getDataQueFoiGerado();
		Calendar calendarDoMomentoQueFoiEnviado = Calendar.getInstance();
		calendarDoMomentoQueFoiEnviado.setTime(dataQuePinFoiEnviado);

		Calendar calendarAtual = Calendar.getInstance();

		int diferencaEntreAno = calendarDoMomentoQueFoiEnviado
				.get(Calendar.YEAR) - calendarAtual.get(Calendar.YEAR);
		int diferencaEntreMes = calendarDoMomentoQueFoiEnviado
				.get(Calendar.MONTH) - calendarAtual.get(Calendar.MONTH);
		int diferencaEntreDias = calendarDoMomentoQueFoiEnviado
				.get(Calendar.DAY_OF_MONTH)
				- calendarAtual.get(Calendar.DAY_OF_MONTH);

		if (diferencaEntreAno != 0 || diferencaEntreMes != 0
				|| diferencaEntreDias != 0) {

			throw new CodigoPinExpiradoException(
					"Tempo mínimo para emissão de um novo PIN ainda não foi atingido! Tempo mínimo: 1 dia");
		}
	}
}
