package br.com.cashpack.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cashpack.exception.CashPackException;
import br.com.cashpack.model.CodigoPIN;
import br.com.cashpack.model.Credencial;
import br.com.cashpack.model.Gestor;
import br.com.cashpack.model.StatusGestorEnum;
import br.com.cashpack.model.Telefone;
import br.com.cashpack.model.Usuario;
import br.com.cashpack.service.validator.EnderecoException;
import br.com.cashpack.service.validator.EnderecoValidator;
import br.com.cashpack.service.validator.TelefoneValidator;

public class GestorServiceImpl implements GestorService {

	private static final String SENHA_PADRAO_GESTOR = "euSouUmGestorCashPack";

	private TelefoneValidator telefoneValidator = new TelefoneValidator();
	private EnderecoValidator enderecoValidator = new EnderecoValidator();

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private CodigoPinService codigoPinService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private SmsService smsSender;

	@Autowired
	private CredencialService credencialService;

	public void cadastrarGestor(Gestor gestor) throws CashPackException {
		this.validate(gestor);

		this.telefoneService.saveTelefone(gestor.getTelefone());
		this.enderecoService.saveEndereco(gestor.getEndereco());
		this.credencialService.saveCredencial(gestor.getCredencial());

		CodigoPIN codigoPin = this.codigoPinService.gerarPinAleatorio();
		gestor.setCodigoPin(codigoPin);
		gestor.setStatusGestorEnum(StatusGestorEnum.DESATIVADO);
		this.codigoPinService.saveCodigoPIN(codigoPin);

		this.saveGestor(gestor);
		this.smsSender.sendPin(gestor);
	}

	private Gestor validate(Gestor gestor) throws CashPackException {
		this.validarCamposObrigatorios(gestor);
		gestor = validarNaoExistenciaDeUsuarioJaCadastradoComMesmoTelefone(gestor);
		this.validarUsuarioCadastradoEAtivado(gestor);

		return gestor;
	}

	private void validarCamposObrigatorios(Gestor gestor)
			throws GestorException, EnderecoException {

		if (gestor == null) {
			throw new GestorException("Gestor está null!");
		}

		this.enderecoValidator.validate(gestor.getEndereco());

		if (gestor.getCnpj() != null) {
			gestor.setCnpj(gestor.getCnpj().trim());
			if (gestor.getCnpj().length() != 14) {
				throw new GestorException(
						"O cnpj deve ter obrigatoriamente 14 dígitos!");
			}
		} else {
			throw new GestorException("CNPJ é um campo obrigatório!");
		}

		if (gestor.getEmail() == null) {
			throw new GestorException("E-mail é um campo obrigatório!");
		} else if (gestor.getEmail().length() < 5) {
			throw new GestorException("E-mail deve ter no mínimo 5 caracteres!");
		}

		if (gestor.getRazaoSocial() == null) {
			throw new GestorException("Razão Social é um campo obrigatório!");
		} else if (gestor.getRazaoSocial().length() < 5) {
			throw new GestorException(
					"Razão Social deve ter no mínimo 5 caracteres!");
		}

		if (gestor.getAceitouOsTermosDeContrato() == null
				|| !gestor.getAceitouOsTermosDeContrato()) {
			throw new GestorException(
					"Os termos de contrato devem ser aceitos para que o Gestor seja cadastrado!");
		}

		Credencial credencial = gestor.getCredencial();
		if (credencial == null) {
			credencial = new Credencial();
			credencial.setLogin(gestor.getEmail());
			credencial.setSenha(SENHA_PADRAO_GESTOR);
			gestor.setCredencial(credencial);
		} else {
			if (credencial.getLogin() == null
					|| credencial.getLogin().isEmpty()) {

				credencial.setLogin(gestor.getEmail());
			}

			if (credencial.getSenha() == null
					|| credencial.getSenha().isEmpty()) {
				credencial.setSenha(SENHA_PADRAO_GESTOR);
			}
		}
		gestor.setCredencial(credencial);
	}

	private Gestor validarNaoExistenciaDeUsuarioJaCadastradoComMesmoTelefone(
			Gestor gestor) throws CashPackException, GestorException {

		Telefone telefone = gestor.getTelefone();
		telefoneValidator.validate(telefone);

		Usuario usuarioComMesmoTelefone = null;
		try {
			usuarioComMesmoTelefone = this.usuarioService
					.findUsuarioByTelefone(telefone.getCodPais(),
							telefone.getCodArea(), telefone.getNumero());
		} catch (Exception e) {
			usuarioComMesmoTelefone = null;
		}

		if (usuarioComMesmoTelefone != null) {
			if (usuarioComMesmoTelefone instanceof Gestor) {
				gestor = (Gestor) usuarioComMesmoTelefone;

				if (gestor.getStatusGestorEnum().equals(
						StatusGestorEnum.DESATIVADO)) {

					return gestor;
				}
			}
			throw new GestorException(
					"O telefone informado está vinculado a um usuário já cadastrado!");
		}
		return gestor;
	}

	private void validarUsuarioCadastradoEAtivado(Gestor gestor)
			throws GestorException {

		boolean usuarioJaCadastradoEAtivado = gestor.getId() != null
				&& gestor.getStatusGestorEnum() != null
				&& gestor.getStatusGestorEnum()
						.equals(StatusGestorEnum.ATIVADO);
		if (usuarioJaCadastradoEAtivado) {
			throw new GestorException("O Gestor já está cadastrado e ativado!");
		}
	}

	@Override
	public void confirmarPinGestor(Gestor gestor) throws CashPackException {
		if (gestor == null || gestor.getCodigoPin() == null
				|| gestor.getCodigoPin().getCodigo() == null
				|| gestor.getCodigoPin().getCodigo().isEmpty()) {

			throw new GestorException("Código Pin é obrigatório!");
		}

		Telefone telefone = gestor.getTelefone();
		telefoneValidator.validate(telefone);

		Gestor gestorPesquisado = null;
		try {
			Usuario usuario = this.usuarioService.findUsuarioByTelefone(
					telefone.getCodPais(), telefone.getCodArea(),
					telefone.getNumero());
			if (usuario instanceof Gestor) {
				gestorPesquisado = (Gestor) usuario;
			}
		} catch (Exception e) {
			throw new GestorException(
					"Não existe Gestor cadastrado com o telefone informado!");
		}

		if (!gestor.getCodigoPin().getCodigo()
				.equalsIgnoreCase(gestorPesquisado.getCodigoPin().getCodigo())) {
			throw new GestorException("Código Pin não confere!");
		}
	}
}