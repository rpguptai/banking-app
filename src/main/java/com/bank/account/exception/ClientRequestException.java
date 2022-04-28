package com.bank.account.exception;


/**
 * exception when there is some issue with the client request like no card / account in the database.
 */
public class ClientRequestException extends RuntimeException {

	/**
	 * Instantiates a new client request exception.
	 *
	 * @param message the message
	 */
	public ClientRequestException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new client request exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ClientRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
