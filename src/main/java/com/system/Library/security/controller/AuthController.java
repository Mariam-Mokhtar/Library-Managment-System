package com.system.Library.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.system.Library.security.config.SecurityConstants;
import com.system.Library.security.model.request.LoginReqModel;
import com.system.Library.security.model.response.LoginResModel;
import com.system.Library.security.service.CustomUserDetailsService;
import com.system.Library.utils.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Operation(summary = "Authenticate user and return JWT token", description = "Allows a user to log in by providing username and password. Returns a JWT token upon successful authentication.")
	@PostMapping("/login")
	public LoginResModel login(@RequestBody LoginReqModel loginReqModel) throws Exception {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginReqModel.getUsername(), loginReqModel.getPassword()));
		if (authentication.isAuthenticated()) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginReqModel.getUsername());
			return new LoginResModel(SecurityConstants.TOKEN_PREFIX + jwtUtil.generateToken(userDetails));
		} else {
			throw new Exception("Invalid credentials");
		}
	}
}
