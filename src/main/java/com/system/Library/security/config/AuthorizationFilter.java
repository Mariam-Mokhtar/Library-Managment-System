package com.system.Library.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.system.Library.security.service.CustomUserDetailsService;
import com.system.Library.utils.security.JwtUtil;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	/*
	 * This filter ensures that every incoming request is checked for a valid JWT
	 * and sets up the Spring Security context accordingly, allowing secure access
	 * to protected resources.
	 */

	private final JwtUtil jwtUtil;

	private final CustomUserDetailsService userDetailsService;

	public AuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			CustomUserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);

		String username = null;
		String jwtToken = null;

		if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			jwtToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
			username = jwtUtil.extractUsername(jwtToken); // Extract username from token
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		}
		chain.doFilter(request, response); // Continue the request
	}

}
