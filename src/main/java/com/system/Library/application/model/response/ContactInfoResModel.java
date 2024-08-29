package com.system.Library.application.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoResModel {

	private String phoneNum;

	private String email;

	private AddressResModel address;

	private AddressResModel workAddress;

	public ContactInfoResModel() {
		// TODO Auto-generated constructor stub
	}
}
