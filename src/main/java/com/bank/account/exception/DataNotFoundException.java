/**
 * 
 */
package com.bank.account.exception;


/**
 * Exception when data requested is not available.
 */
public class DataNotFoundException extends RuntimeException {

	/**
	 * Instantiates a new data not found exception.
	 *
	 * @param message the message
	 */
	public DataNotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new data not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
