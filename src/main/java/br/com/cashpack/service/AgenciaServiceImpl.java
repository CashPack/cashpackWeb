package br.com.cashpack.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.exception.CodigoPINJaAtivadoException;
import br.com.cashpack.exception.CodigoPinDivergenteException;
import br.com.cashpack.exception.CodigoPinEmitidoRecentementeException;
import br.com.cashpack.model.Agencia;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.RamoDeAtividade;
import br.com.cashpack.model.StatusAgencia;
import br.com.cashpack.model.Telefone;
import br.com.cashpack.model.TipoDeDocumentoDaAgenciaEnum;
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
		Usuario usuarioComMesmoTelefone = null;

		try {
			Telefone telefone = agencia.getTelefone();
			this.telefoneValidator.validate(telefone);
			usuarioComMesmoTelefone = this.findUsuarioByTelefone(telefone.getCodPais(), telefone.getCodArea(), telefone.getNumero());

		} catch (Exception e) {
			agenciaPesquisadaPorTelefone = agencia;
		}

		if (usuarioComMesmoTelefone != null) {
			if (usuarioComMesmoTelefone instanceof Agencia) {
				agenciaPesquisadaPorTelefone = (Agencia) usuarioComMesmoTelefone;
			} else {
				throw new AgenciaException(
						"O telefone informado está vinculado a um usuário já cadastrado!");
			}
		}

		if (agenciaPesquisadaPorTelefone != null
				&& agenciaPesquisadaPorTelefone.getId() != null) {
			if (agenciaPesquisadaPorTelefone.getStatusAgencia().equals(StatusAgencia.ATIVADO)) {
				throw new AgenciaException("Agência já está cadastrada e devidamente ativada para usar o sistema!");
			}

			else if (agenciaPesquisadaPorTelefone.getStatusAgencia().equals(StatusAgencia.PENDENTE)) {
				throw new AgenciaException("Agência já está cadastrada mas aguardando pagamento. Por favor, realize o pagamento da taxa para concluir o cadastro!");
			}
		}

		this.telefoneService.saveTelefone(agenciaPesquisadaPorTelefone.getTelefone());
		
		CodigoPIN codigoPin = gerarPinAleatorio();
		agenciaPesquisadaPorTelefone.setCodigoPin(codigoPin);

		agenciaPesquisadaPorTelefone.setStatusAgencia(StatusAgencia.DESATIVADO);
		this.saveAgencia(agenciaPesquisadaPorTelefone);
		this.smsSender.sendPin(agenciaPesquisadaPorTelefone);
	}

	private CodigoPIN gerarPinAleatorio() {
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
		codigoPinService.saveCodigoPIN(pin);

		return pin;
	}

	private Usuario findUsuarioByTelefone(String codPais, String codArea,
			String numero) {

		return Usuario.findUsuarioByCodPaisAndCodAreaAndNumero(codPais,
				codArea, numero);

	}

	private void validate(Agencia agencia) throws CashPackException {
		if (agencia == null) {
			throw new AgenciaException("Agência está null");
		}
		telefoneValidator.validate(agencia.getTelefone());

		if (agencia.getTipoDeDocumentoAgenciaEnum() == null) {
			throw new AgenciaException(
					"Tipo de documento da agência é um campo obrigatório!");
		}

		if (agencia.getNumeroDocumento() == null
				|| agencia.getNumeroDocumento().isEmpty()) {
			throw new AgenciaException(agencia.getTipoDeDocumentoAgenciaEnum()
					+ " é um campo obrigatório!");
		}

		if (agencia.getTipoDeDocumentoAgenciaEnum().equals(
				TipoDeDocumentoDaAgenciaEnum.CPF)
				&& agencia.getNumeroDocumento().length() != 11) {
			throw new AgenciaException(
					"CPF deve ter obrigatoriamente 11 dígitos!");
		}

		else if (agencia.getTipoDeDocumentoAgenciaEnum().equals(
				TipoDeDocumentoDaAgenciaEnum.CNPJ)
				&& agencia.getNumeroDocumento().length() != 14) {
			throw new AgenciaException(
					"CPF deve ter obrigatoriamente 14 dígitos!");
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

	@Override
	public void confirmarPinAgencia(Agencia agencia) throws CashPackException {
		Agencia agenciaPesquisada = null;
		Usuario usuario = null;

		try {
			Telefone telefone = agencia.getTelefone();
			usuario = this.findUsuarioByTelefone(telefone.getCodPais(),
					telefone.getCodArea(), telefone.getNumero());

			if (usuario instanceof Agencia) {
				agenciaPesquisada = (Agencia) usuario;
			} else {
				throw new AgenciaException(
						"Não existe agência cadastrada com o telefone informado!");
			}
		} catch (Exception e) {
			throw new AgenciaException(
					"Não existe agência cadastrada com o telefone informado!");
		}

		if (!agenciaPesquisada.getStatusAgencia().equals(
				StatusAgencia.DESATIVADO)) {
			throw new CodigoPINJaAtivadoException(
					"Agência já teve o PIN ativado!");
		}

		if (!agenciaPesquisada.getCodigoPin().getCodigo().toUpperCase()
				.equals(agencia.getCodigoPin().getCodigo().toUpperCase())) {
			throw new CodigoPinDivergenteException("Código PIN não confere!");
		} else {
			// this.validarTempoDeExpiracaoDeUmPin(agenciaPesquisada);
			agenciaPesquisada.setStatusAgencia(StatusAgencia.PENDENTE);
			this.saveAgencia(agenciaPesquisada);
		}
	}

	// private void validarTempoDeExpiracaoDeUmPin(Agencia agenciaPesquisada)
	// throws CodigoPinExpiradoException {
	//
	// Date dataQuePinFoiEnviado = agenciaPesquisada.getCodigoPin()
	// .getDataQueFoiGerado();
	// Calendar calendarDoMomentoQueFoiEnviado = Calendar.getInstance();
	// calendarDoMomentoQueFoiEnviado.setTime(dataQuePinFoiEnviado);
	//
	// Calendar calendarAtual = Calendar.getInstance();
	//
	// int diferencaEntreAno = calendarDoMomentoQueFoiEnviado
	// .get(Calendar.YEAR) - calendarAtual.get(Calendar.YEAR);
	// int diferencaEntreMes = calendarDoMomentoQueFoiEnviado
	// .get(Calendar.MONTH) - calendarAtual.get(Calendar.MONTH);
	// int diferencaEntreDias = calendarDoMomentoQueFoiEnviado
	// .get(Calendar.DAY_OF_MONTH)
	// - calendarAtual.get(Calendar.DAY_OF_MONTH);
	//
	// if (diferencaEntreAno != 0 || diferencaEntreMes != 0
	// || diferencaEntreDias != 0) {
	//
	// throw new CodigoPinExpiradoException(
	// "Tempo mínimo para emissão de um novo PIN ainda não foi atingido! Tempo mínimo: 1 dia");
	// }
	//
	// }
}