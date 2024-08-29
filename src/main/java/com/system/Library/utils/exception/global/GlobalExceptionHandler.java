package com.system.Library.utils.exception.global;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.system.Library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.Library.utils.exception.enums.ApiExceptionEnum;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;
import com.system.Library.utils.exception.model.ExceptionHandlerResModel;

@ControllerAdvice
public class GlobalExceptionHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3556500629243800878L;
	// Logger instance for the specified class.
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessLogicViolationException.class)
	public ResponseEntity<ExceptionHandlerResModel> handleBusinessLogicViolationException(
			BusinessLogicViolationException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.BUSINESS_CONSTRAIN_VIOLATION.name(), ex.getMessage(), ex.getDetails());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionHandlerResModel> handleAccessDeniedException(AccessDeniedException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(ApiExceptionEnum.ACCESS_IS_DENIED.name(),
				ex.getMessage());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ExceptionHandlerResModel> handleRuntimeException(RuntimeException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.RUN_TIME_EXCEPTION.name(), ApiErrorMessageEnum.RUN_TIME_EXCEPTION.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionHandlerResModel> handleException(Exception ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.GENERAL_ERROR.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleNullPointerException(NullPointerException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.NULL_POINTER_EXCEPTION.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionHandlerResModel> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		List<Map<String, String>> details = new ArrayList<>();
		// returns a list of FieldError objects, where each FieldError represents a
		// validation error for a specific field in the request.
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			Map<String, String> detailMap = new HashMap<>();
			detailMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			details.add(detailMap);
		}
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(ApiExceptionEnum.BAD_REQUEST.name(),
				ApiErrorMessageEnum.BAD_REQUEST.name(), details);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionHandlerResModel> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {
		List<Map<String, String>> details = new ArrayList<>();
		Map<String, String> detailMap = new HashMap<>();
		detailMap.put(ex.getName(), ex.getMessage().toUpperCase());
		details.add(detailMap);
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(ApiExceptionEnum.BAD_REQUEST.name(),
				ApiErrorMessageEnum.BAD_REQUEST.name(), details);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionHandlerResModel> handleMethodNotSupported(
			HttpRequestMethodNotSupportedException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.METHOD_NOT_ALLOWED.name(), ApiErrorMessageEnum.METHOD_NOT_ALLOWED.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleConstraintViolationException(
			ConstraintViolationException ex) {
		List<Map<String, String>> details = new ArrayList<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			Map<String, String> detailMap = new HashMap<>();
			detailMap.put(violation.getPropertyPath().toString(), violation.getMessage().toUpperCase());
			details.add(detailMap);
		}
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(ApiExceptionEnum.BAD_REQUEST.name(),
				ApiErrorMessageEnum.GENERAL_ERROR.name(), details);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ IncorrectResultSizeDataAccessException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleIncorrectResultSizeDataAccessException(
			IncorrectResultSizeDataAccessException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(),
				ApiErrorMessageEnum.INCORRECT_RESULT_SIZE_DATA_ACCESS.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleNoSuchElementException(NoSuchElementException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.REFRENCE_ID_NOT_FOUND.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ CannotCreateTransactionException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleCannotCreateTransactionException(
			CannotCreateTransactionException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.JDBC_CONNECTION_ERROR.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Accept header in an HTTP request tells the server what media types the client
	// expects in the response.
	// If the server cannot produce any of the media types listed in the Accept
	// header, this exception is thrown. Response
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleHttpMediaTypeNotAcceptableException(
			HttpMediaTypeNotAcceptableException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.MEDIA_TYPE_NOT_ACCEPTED.name(), ApiErrorMessageEnum.MEDIA_TYPE_NOT_ACCEPTED.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
	}

	// If the server cannot process the request’s body because the Content-Type is
	// not supported, this exception is thrown. Request
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public ResponseEntity<ExceptionHandlerResModel> handleHttpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException ex) {
		ExceptionHandlerResModel responseModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.MEDIA_TYPE_NOT_SUPPORTED.name(), ApiErrorMessageEnum.MEDIA_TYPE_NOT_SUPPORTED.name());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handlePersistenceException(DataIntegrityViolationException ex) {

		ExceptionHandlerResModel responseModel = null;
		Throwable cause = ex.getRootCause();
		if (cause != null && (cause instanceof SQLIntegrityConstraintViolationException))
			responseModel = SQLIntegrityConstraintViolation(cause.toString(),
					((SQLIntegrityConstraintViolationException) cause).getErrorCode());
		else if (cause != null && cause instanceof SQLException)
			responseModel = SQLIntegrityConstraintViolation(cause.toString(), ((SQLException) cause).getErrorCode());
		else if (cause instanceof ConstraintViolationException)
			responseModel = new ExceptionHandlerResModel(ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(),
					ApiErrorMessageEnum.DATABASE_CONSTRAINT_VIOLATION.name());

		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(responseModel, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private ExceptionHandlerResModel SQLIntegrityConstraintViolation(String exceptionMessage, int exceptionCode) {
		String message = null;
		String exceptionType = null;

		if (exceptionCode == 1062) {// Unique constraint violation
			message = getConstraintKeyFromExceptionMessage("U_", exceptionMessage);
			exceptionType = ApiExceptionEnum.UNIQUE_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionCode == 1048) {// Column cannot be null
			message = exceptionMessage;
			exceptionType = ApiExceptionEnum.INTERNAL_SERVER_ERROR.name();
		} else if (exceptionCode == 1406) {// Data too long for column
			message = exceptionMessage;
			exceptionType = ApiExceptionEnum.INTERNAL_SERVER_ERROR.name();
		} else if (exceptionCode == 1452) {// Create And FK Not Found
			message = getConstraintKeyFromExceptionMessage("FK_", exceptionMessage);
			exceptionType = ApiExceptionEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionCode == 1451) {// Delete And Child Record Found
			message = getConstraintKeyFromExceptionMessage("FK_", exceptionMessage);
			exceptionType = ApiExceptionEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionCode == 1364) {// Field doesn't have a default value
			message = exceptionMessage;
			exceptionType = ApiExceptionEnum.INTERNAL_SERVER_ERROR.name();
		}
		return new ExceptionHandlerResModel(exceptionType, message);
	}

	private String getConstraintKeyFromExceptionMessage(String prefix, String exceptionMessage) {
		Matcher matcher = Pattern.compile(prefix + "(\\w+)").matcher(exceptionMessage);
		return matcher.find() ? matcher.group() : "";
	}

	public GlobalExceptionHandler() {
		// TODO Auto-generated constructor stub
	}

}
