/**
 * 
 */
package com.bank.account.exception;

/**
 * @author Ravi
 *
 */
public class TechnicalException extends RuntimeException {

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

}