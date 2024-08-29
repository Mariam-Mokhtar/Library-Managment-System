package com.system.Library.security.config;

public class SecurityConstants {

	public SecurityConstants() {
		// TODO Auto-generated constructor stub
	}

	public static final long EXPIRATION_TIME = 864000000; // 10 Days in milli sec
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SECRET_KEY = "your_secret_key";

}
