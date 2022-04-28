package com.bank.account.exception;


/**
 * Functional exception like not enough balance.
 *
 */
public class FunctionalException extends RuntimeException {

	/**
	 * Instantiates a new functional exception.
	 *
	 * @param message the message
	 */
	public FunctionalException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new functional exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public FunctionalException(String message, Throwable cause) {
		super(message, cause);
	}

}
