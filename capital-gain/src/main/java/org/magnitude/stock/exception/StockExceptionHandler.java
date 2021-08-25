package org.magnitude.stock.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller Advice to handle exception of global level.
 * 
 * @author Ravi
 *
 */
@ControllerAdvice
public class StockExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SystemFailureException.class)
	@Nullable
	public final ResponseEntity<ExceptionResponse> handleSystemFailureException(SystemFailureException e,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@Nullable
	public final ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
