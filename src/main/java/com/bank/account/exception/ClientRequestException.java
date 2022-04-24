package com.bank.account.exception;

/**
 * exception when there is some issue with the client request like no card / account in the database.
 */
public class ClientRequestException extends RuntimeException {

	public ClientRequestException(String message) {
		super(message);
	}

	public ClientRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
