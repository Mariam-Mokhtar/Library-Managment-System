package com.system.Library.application.model.request;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoReqModel {

	@NotNull
	@Length(min = 11, max = 11)
	private String phoneNum;

	@NotNull
	@Email(message = "Email should be valid")
	private String email;

	@NotNull
	@Valid
	private AddressReqModel address;

	@Nullable
	@Valid
	private AddressReqModel workAddress;

	public ContactInfoReqModel() {
		// TODO Auto-generated constructor stub
	}

}
