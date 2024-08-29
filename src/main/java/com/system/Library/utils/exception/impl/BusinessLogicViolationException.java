package com.system.Library.utils.exception.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessLogicViolationException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -744222167923991614L;
	private List<Map<String, String>> details;

	public BusinessLogicViolationException(String message) {
		super(message);
	}

	public BusinessLogicViolationException(String message, List<Map<String, String>> details) {
		super(message);
		this.setDetails(details);
	}
}
