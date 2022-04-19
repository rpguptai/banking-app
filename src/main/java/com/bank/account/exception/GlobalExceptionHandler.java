/**
 * 
 */
package com.bank.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ravi
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ClientRequestException.class)
	public ResponseEntity<Response> handleResourceException(ClientRequestException clientRequestException) {
		log.error(clientRequestException.getMessage());
		return new ResponseEntity<>(new Response(clientRequestException.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NoRecordFoundException.class)
	public ResponseEntity<Response> handleNoDataFoundException(NoRecordFoundException noRecordFoundException) {
		log.error(noRecordFoundException.getMessage());
		return new ResponseEntity<>(new Response(noRecordFoundException.getMessage()), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(value = TechnicalException.class)
	public ResponseEntity<Response> handleGeneralException(TechnicalException exception) {
		log.error(exception.getMessage());
		return new ResponseEntity<>(new Response("There is issue : "+exception.getMessage()+ " , Please contact application team"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	private class Response {
        String message;
    }

}