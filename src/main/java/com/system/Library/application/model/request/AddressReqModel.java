package com.system.Library.application.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressReqModel {

	@NotNull
	@NotBlank
	@Length(max = 80)
	private String street;

	@NotNull
	@NotBlank
	@Length(max = 80)
	private String city;

	@NotNull
	@NotBlank
	@Length(max = 80)
	private String state;

	@Length(min = 5, max = 5)
	private String postalCode;

	public AddressReqModel() {
		// Default constructor
	}
}
