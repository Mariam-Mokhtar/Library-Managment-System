package com.system.Library.application.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2799931945160962682L;

	@NotNull
	@Column(nullable = false, length = 80)
	private String street;

	@NotNull
	@Column(nullable = false, length = 80)
	private String city;

	@NotNull
	@Column(nullable = false, length = 80)
	private String state;

	@Column(nullable = true, length = 5)
	private String postalCode;
}
