package br.com.cashpack.service.validator;

import java.util.Calendar;
import java.util.Date;

import br.com.cashpack.exception.CodigoPinExpiradoException;
import br.com.cashpack.model.UsuarioCashPack;

public class UsuarioCashPackValidator implements CashPackValidator {

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
					"PIN expirado! Por favor, cadastre-se novamente e um novo código PIN será enviado!");
		}
	}
}
