package com.bank.account.exception;

/**
 * Functional exception like not enough balance.
 *
 */
public class FunctionalException extends RuntimeException {

	public FunctionalException(String message) {
		super(message);
	}

	public FunctionalException(String message, Throwable cause) {
		super(message, cause);
	}

}
