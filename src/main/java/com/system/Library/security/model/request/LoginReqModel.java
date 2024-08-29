package com.system.Library.security.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqModel {
	private String username;
	private String password;

	public LoginReqModel() {
		// TODO Auto-generated constructor stub
	}

}
