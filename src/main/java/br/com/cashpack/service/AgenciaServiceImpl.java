package br.com.cashpack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.exception.CodigoPINJaAtivadoException;
import br.com.cashpack.exception.CodigoPinDivergenteException;
import br.com.cashpack.model.Agencia;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.Gestor;
import br.com.cashpack.model.RamoDeAtividade;
import br.com.cashpack.model.StatusAgencia;
import br.com.cashpack.model.Telefone;
import br.com.cashpack.model.TipoDeDocumentoDaAgenciaEnum;
import br.com.cashpack.model.Usuario;
import br.com.cashpack.service.validator.EnderecoValidator;
import br.com.cashpack.service.validator.TelefoneValidator;

public class AgenciaServiceImpl implements AgenciaService {

	private TelefoneValidator telefoneValidator = new TelefoneValidator();
	private EnderecoValidator enderecoValidator = new EnderecoValidator();

	@Autowired
	private SmsService smsSender;

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private CodigoPinService codigoPinService;

	@Autowired
	private GestorService gestorService;

	@Override
	public void cadastrar(Agencia agencia) throws CashPackException {
		validate(agencia);

		Agencia agenciaPesquisadaPorTelefone = null;
		Usuario usuarioComMesmoTelefone = null;

		try {
			Telefone telefone = agencia.getTelefone();
			this.telefoneValidator.validate(telefone);
			usuarioComMesmoTelefone = this.findUsuarioByTelefone(
					telefone.getCodPais(), telefone.getCodArea(),
					telefone.getNumero());

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
			if (agenciaPesquisadaPorTelefone.getStatusAgencia().equals(
					StatusAgencia.ATIVADO)) {
				throw new AgenciaException(
						"Agência já está cadastrada e devidamente ativada para usar o sistema!");
			}

			else if (agenciaPesquisadaPorTelefone.getStatusAgencia().equals(
					StatusAgencia.PENDENTE)) {
				throw new AgenciaException(
						"Agência já está cadastrada mas aguardando pagamento. Por favor, realize o pagamento da taxa para concluir o cadastro!");
			}
		}

		this.telefoneService.saveTelefone(agenciaPesquisadaPorTelefone
				.getTelefone());
		CodigoPIN codigoPin = this.codigoPinService.gerarPinAleatorio();
		agenciaPesquisadaPorTelefone.setCodigoPin(codigoPin);

		agenciaPesquisadaPorTelefone.setStatusAgencia(StatusAgencia.DESATIVADO);
		this.saveAgencia(agenciaPesquisadaPorTelefone);
		this.smsSender.sendPin(agenciaPesquisadaPorTelefone);
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

		if (agencia.getEmail() == null || agencia.getEmail().isEmpty()) {
			throw new AgenciaException(
					"E-mail da agência é um campo obrigatório!");
		}

		if (agencia.getTipoDeDocumentoAgenciaEnum() == null) {
			throw new AgenciaException(
					"Tipo de documento da agência é um campo obrigatório!");
		}

		if (agencia.getNumeroDocumento() == null
				|| agencia.getNumeroDocumento().isEmpty()) {
			throw new AgenciaException(agencia.getTipoDeDocumentoAgenciaEnum()
					+ " é um campo obrigatório!");
		} else {
			agencia.setNumeroDocumento(agencia.getNumeroDocumento()
					.replace(".", "").replace("-", "").replace("/", "")
					.replace(" ", ""));

			if (agencia.getTipoDeDocumentoAgenciaEnum().equals(
					TipoDeDocumentoDaAgenciaEnum.CPF)
					&& agencia.getNumeroDocumento().length() != 11) {
				throw new AgenciaException(
						"CPF deve ter obrigatoriamente 11 dígitos!");
			} else if (agencia.getTipoDeDocumentoAgenciaEnum().equals(
					TipoDeDocumentoDaAgenciaEnum.CNPJ)
					&& agencia.getNumeroDocumento().length() != 14) {
				throw new AgenciaException(
						"CPF deve ter obrigatoriamente 14 dígitos!");
			}
		}

		if (agencia.getRazaoSocial() == null
				|| agencia.getRazaoSocial().isEmpty()) {
			if (agencia.getTipoDeDocumentoAgenciaEnum().equals(
					TipoDeDocumentoDaAgenciaEnum.CPF)) {
				throw new AgenciaException("Nome é um campo obrigatório!");
			} else {
				throw new AgenciaException(
						"Razão Social é um campo obrigatório!");
			}
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

		if (agencia.getGestor() == null) {
			throw new AgenciaException(
					"Gestor que está cadastrando a agência é obrigatório!");
		} else {
			Gestor gestor = gestorService.findGestor(agencia.getGestor()
					.getId());
			if (gestor == null) {
				throw new AgenciaException(
						"O gerente que está cadastrando a agência não está cadastrado no sistema!");
			} else {
				agencia.setGestor(gestor);
			}
		}

		this.enderecoValidator.validate(agencia.getEndereco());
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
			this.codigoPinService
					.validarTempoDeExpiracaoDeUmPin(agenciaPesquisada);
			agenciaPesquisada.setStatusAgencia(StatusAgencia.PENDENTE);
			this.saveAgencia(agenciaPesquisada);
		}
	}

	@Override
	public List<Agencia> findAgenciasPorIdDeGestor(Long idGestor) {
		return Agencia.findAgenciasByGestorId(idGestor);
	}
}