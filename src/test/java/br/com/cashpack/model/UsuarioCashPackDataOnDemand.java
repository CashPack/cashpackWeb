package br.com.cashpack.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = UsuarioCashPack.class)
public class UsuarioCashPackDataOnDemand {

	@Test
	public void verificarColetaDoCodAreaEDooNumeroDeTelefone() {
		String numeroComArea = "(83) 98874 - 6463";

		String area = "";
		String numero = "";

		area = (String) numeroComArea.subSequence(0, 3);
		area = area.replace("(", "").replace(")", "").replace(" ", "");
		
		numero = numeroComArea.substring(4).replace("(", "").replace(")", "")
				.replace("-", "").replace(" ", "");

		Assert.assertEquals("83", area);
		Assert.assertEquals("988746463", numero);
	}
}
