package com.system.Library.utils.exception.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionHandlerResModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713856099352520174L;

	private String exceptionType;
	private String message;
	private List<Map<String, String>> details;

	public ExceptionHandlerResModel(String exceptionType, String message) {
		this.setMessage(message);
		this.setExceptionType(exceptionType);
	}
}
