package br.com.cashpack.service.validator;

import br.com.cashpack.exception.CashPackException;

public interface CashPackValidator {

	void validate(Object object) throws CashPackException;
}
