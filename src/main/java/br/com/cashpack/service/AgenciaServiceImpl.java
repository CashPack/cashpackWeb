package br.com.cashpack.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.exception.CodigoPinEmitidoRecentementeException;
import br.com.cashpack.model.Agencia;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.RamoDeAtividade;
import br.com.cashpack.model.StatusAgencia;
import br.com.cashpack.model.Telefone;
import br.com.cashpack.model.Usuario;
import br.com.cashpack.service.validator.TelefoneValidator;

public class AgenciaServiceImpl implements AgenciaService {

	private TelefoneValidator telefoneValidator = new TelefoneValidator();

	@Autowired
	private SmsService smsSender;

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private CodigoPinService codigoPinService;

	@Override
	public void cadastrar(Agencia agencia) throws CashPackException {
		validate(agencia);

		Agencia agenciaPesquisadaPorTelefone = null;
		try {
			Telefone telefone = agencia.getTelefone();
			agenciaPesquisadaPorTelefone = this.findAgenciaByTelefone(
					telefone.getCodPais(), telefone.getCodArea(),
					telefone.getNumero());

		} catch (Exception e) {
			agenciaPesquisadaPorTelefone = agencia;
		}

		if (agenciaPesquisadaPorTelefone != null
				&& agenciaPesquisadaPorTelefone.getId() != null) {
			if (!agencia.getCnpj().equals(
					agenciaPesquisadaPorTelefone.getCnpj())) {
				throw new AgenciaException(
						"Já existe uma agência cadastrada com o mesmo telefone mas com outro CNPJ!");
			}

			if (agenciaPesquisadaPorTelefone.getStatusAgencia().equals(
					StatusAgencia.ATIVADO)) {
				throw new AgenciaException(
						"Agência já está cadastrada e devidamente ativada para usar o sistema!");
			}

			else if (agenciaPesquisadaPorTelefone.getStatusAgencia().equals(
					StatusAgencia.PENDENTE)) {
				throw new AgenciaException(
						"Agencia já está cadastrada mas aguardando pagamento. Por favor, realize o pagamento da taxa para concluir o cadastro!");
			}
		}

		this.telefoneService.saveTelefone(agenciaPesquisadaPorTelefone
				.getTelefone());
		verificarUsuarioCashPackComPinEmitidoRecentemente(agenciaPesquisadaPorTelefone);

		CodigoPIN codigoPin = gerarPinAleatorio();
		agenciaPesquisadaPorTelefone.setCodigoPin(codigoPin);

		agenciaPesquisadaPorTelefone.setStatusAgencia(StatusAgencia.DESATIVADO);
		this.saveAgencia(agenciaPesquisadaPorTelefone);
		// this.smsSender.sendPin(agencia);
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

	private void verificarUsuarioCashPackComPinEmitidoRecentemente(
			Agencia agencia) throws CodigoPinEmitidoRecentementeException {

		if (agencia.getCodigoPin() != null) {
			verificarTempoDeEnvioDoUltimoPin(agencia);
		}
	}

	private void verificarTempoDeEnvioDoUltimoPin(Agencia agencia)
			throws CodigoPinEmitidoRecentementeException {

		Date dataQuePinFoiEnviado = agencia.getCodigoPin()
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

	private Agencia findAgenciaByCnpj(String cnpj) {
		return Agencia.findAgenciaByCnpj(cnpj);
	}

	private Agencia findAgenciaByTelefone(String codPais, String codArea,
			String numero) {

		Usuario usuario = Usuario
				.findUsuarioCashPackByCodPaisAndCodAreaAndNumero(codPais,
						codArea, numero);

		if (usuario != null) {
			return (Agencia) usuario;
		}
		return null;
	}

	private void validate(Agencia agencia) throws CashPackException {
		if (agencia == null) {
			throw new AgenciaException("Agência está null");
		}

		telefoneValidator.validate(agencia.getTelefone());

		if (agencia.getCnpj() == null || agencia.getCnpj().isEmpty()) {
			throw new AgenciaException("CNPJ é um campo obrigatório!");
		}

		if (agencia.getRazaoSocial() == null
				|| agencia.getRazaoSocial().isEmpty()) {
			throw new AgenciaException("Razão Social é um campo obrigatório!");
		}

		if (agencia.getNomeFantasia() == null
				|| agencia.getNomeFantasia().isEmpty()) {
			throw new AgenciaException("Nome Fantasia é um campo obrigatório!");
		}

		if (agencia.getRamoDeAtividade() == null
				|| agencia.getRamoDeAtividade().getId() == null) {
			throw new AgenciaException("Ramo de Atividade da agência está null");
		} else {
			RamoDeAtividade ramo = RamoDeAtividade.findRamoDeAtividade(agencia
					.getRamoDeAtividade().getId());
			if (ramo == null) {
				throw new AgenciaException(
						"Ramo de Atividade não está cadastrado no sistema!");
			} else {
				agencia.setRamoDeAtividade(ramo);
			}
		}

		// if (agencia.getGerente() == null) {
		// throw new AgenciaException(
		// "Gerente que está cadastrando a agência é obrigatório!");
		// } else {
		// Gerente gerente = Gerente.findGerente(agencia.getGerente().getId());
		// if (gerente == null) {
		// throw new AgenciaException(
		// "O gerente que está cadastrando a agência não está cadastrado no sistema!");
		// } else {
		// agencia.setGerente(gerente);
		// }
		// }
	}
}