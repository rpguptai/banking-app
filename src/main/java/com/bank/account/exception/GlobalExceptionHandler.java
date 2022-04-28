/**
 * 
 */
package com.bank.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bank.account.vo.ResponseVO;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handle resource exception.
	 *
	 * @param clientRequestException the client request exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = ClientRequestException.class)
	public ResponseEntity<ResponseVO> handleResourceException(ClientRequestException clientRequestException) {
		log.error(clientRequestException.getMessage());
		return new ResponseEntity<>(new ResponseVO(null,"ERROR101",clientRequestException.getMessage(),null), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle no data found exception.
	 *
	 * @param noRecordFoundException the no record found exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<ResponseVO> handleNoDataFoundException(DataNotFoundException noRecordFoundException) {
		log.error(noRecordFoundException.getMessage());
		return new ResponseEntity<>(new ResponseVO(null,"ERROR102",noRecordFoundException.getMessage(),null), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handle functional exception.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = FunctionalException.class)
	public ResponseEntity<ResponseVO> handleFunctionalException(FunctionalException exception) {
		log.error(exception.getMessage());
		return new ResponseEntity<>(new ResponseVO(null,"ERROR103",exception.getMessage(),null), HttpStatus.NOT_ACCEPTABLE);
	}
	
	/**
	 * Handle general exception.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseVO> handleGeneralException(Exception exception) {
		log.error(exception.getMessage());
		return new ResponseEntity<>(new ResponseVO(null,"ERROR104","There is issue : "+exception.getMessage()+ " , Please contact application team",null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}