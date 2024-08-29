package com.system.Library.application.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronReqModel {

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@Valid
	private ContactInfoReqModel contactInfo;

	public PatronReqModel() {
		// TODO Auto-generated constructor stub
	}

}
