/**
 * 
 */
package com.bank.account.exception;


public class ClientRequestException extends RuntimeException {

	public ClientRequestException(String message) {
		super(message);
	}

	public ClientRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
