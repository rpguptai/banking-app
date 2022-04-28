package com.bank.account.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Gets the message.
 *
 * @return the message
 */
@Getter

/**
 * Sets the message.
 *
 * @param message the new message
 */
@Setter

/**
 * Instantiates a new response VO.
 *
 * @param message the message
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO {

	/** The message. */
	private String message;

	/** The message. */
	private String errorCode;
	
	private String errorMessage;

	/** The validation errors. */
	private List<String> validationErrors;
}
