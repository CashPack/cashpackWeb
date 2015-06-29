package br.com.cashpack.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import br.com.cashpack.model.SMS;
import br.com.cashpack.model.Usuario;

public class HotMobileSmsSenderService implements SmsService {

	private Map<String, String> mensagens;

	public HotMobileSmsSenderService() {
		this.mensagens = new HashMap<String, String>();

		this.mensagens.put("-1", "Erro de Envios – Instabilidade do sistema. ");
		this.mensagens.put("-2", "Sem Crédito. ");
		this.mensagens.put("-5", "Login ou Senha inválidos. ");
		this.mensagens.put("-7", "Mensagem inválida. ");
		this.mensagens.put("-8", "Remetente inválido.");
		this.mensagens.put("-9", "Número do GSM no formato inválido. ");
		this.mensagens.put("-13", "Número do GSM inválido. ");
		this.mensagens.put("-20", "Serviço fora do ar. ");
		this.mensagens.put("-30", "Data de Agendamento inválida. ");
	}

	@Override
	public void sendPin(Usuario usuario) {
		SMS sms = new SMS();
		sms.setDataDeEnvio(new Date());
		sms.setUsuario(usuario);

		String url = montarURL(usuario, sms);
		String retorno = "";
		try {
			HttpClient client = new HttpClient();
			HttpMethod method = new GetMethod(url);
			int statusCode = client.executeMethod(method);

			// if (statusCode != HttpStatus.SC_OK) {
			// System.err.println("Method failed: " + method.getStatusLine());
			// }

			retorno = new String(method.getResponseBody());
			sms.setStatus(statusCode + " - " + retorno);
		} catch (Exception e) {
			e.printStackTrace();
			sms.setStatus("Não foi possível enviar o PIN via SMS: " + retorno);
		}
		this.saveSMS(sms);
	}

	private String montarURL(Usuario usuario, SMS sms) {

		String urlString = "http://painel.hotmobile.com.br/SendAPI/Send.aspx";

		Properties parameters = new Properties();
		parameters.setProperty("usr", "cashpack");
		parameters.setProperty("pwd", "010203");
		parameters.setProperty("number", usuario.getTelefone()
				.toString());

		String texto = "[CASHPACK] PIN gerado para cadastro: "
				+ usuario.getCodigoPin().getCodigo();
		sms.setTexto(texto);

		parameters.setProperty(
				"msg",
				texto.replace(" ", "%20").replace("[", "%5B")
						.replace("]", "%5D").replace(";", "%3B")
						.replace(":", "%3A"));

		// o iterador, para criar a URL
		Iterator i = parameters.keySet().iterator();
		// o contador
		int counter = 0;

		// enquanto ainda existir parametros
		while (i.hasNext()) {
			// pega o nome
			String name = (String) i.next();
			// pega o valor
			String value = parameters.getProperty(name);

			urlString += (++counter == 1 ? "?" : "&") + name + "=" + value;
		}
		return urlString;
	}
}