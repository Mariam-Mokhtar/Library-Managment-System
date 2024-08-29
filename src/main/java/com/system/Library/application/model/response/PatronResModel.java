package com.system.Library.application.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronResModel {

	private int id;
	private String name;
	private ContactInfoResModel contactInfo;

	public PatronResModel() {
		// TODO Auto-generated constructor stub
	}

}
