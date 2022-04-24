/**
 * 
 */
package com.bank.account.exception;

/**
 * Exception when data requested is not available
 *
 */
public class DataNotFoundException extends RuntimeException {

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
