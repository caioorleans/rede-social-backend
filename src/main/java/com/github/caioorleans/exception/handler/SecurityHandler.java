package com.github.caioorleans.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.github.caioorleans.exception.InvalidJwtAuthenticationExcepion;

public class SecurityHandler {
	
	@ExceptionHandler(InvalidJwtAuthenticationExcepion.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticacionException(
			Exception ex, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
}
