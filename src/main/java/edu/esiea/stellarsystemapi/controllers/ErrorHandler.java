package edu.esiea.stellarsystemapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

import edu.esiea.stellarsystemapi.controllers.dto.ErrorResponse;
import edu.esiea.stellarsystemapi.controllers.dto.error.EndPointException;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import edu.esiea.stellarsystemapi.controllers.dto.mappers.ErrorMapper;

@ControllerAdvice
public class ErrorHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
	
	@ExceptionHandler(EndPointException.class)
	public ResponseEntity<ErrorResponse> handleException(EndPointException e){
		ErrorResponse resp = ErrorMapper.endPointExceptionToErrorDTO(e);
		LOGGER.error(resp.getResourceType().toString().concat(" : ").concat(resp.getMessage()));
		return ResponseEntity.status(e.getStatus()).body(resp);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e){
		ErrorResponse resp = new ErrorResponse();
		resp.setMessage("Access denied");
		resp.setResourceType(ResourceType.USER);
		LOGGER.error("Access denied : ".concat(e.getMessage()));
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
		ErrorResponse resp = ErrorMapper.methodArgumentNotValideExceptionToErrorDTO(ex);
		LOGGER.warn(resp.getResourceType().toString().concat(" : ").concat(resp.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
	}
}
