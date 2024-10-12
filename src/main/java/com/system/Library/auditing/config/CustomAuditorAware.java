package com.system.Library.auditing.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.system.Library.security.entity.UserCredential;

public class CustomAuditorAware implements AuditorAware<UserCredential> {

	@Override
	public Optional<UserCredential> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		System.out.print(authentication.getPrincipal().getClass());
		return Optional.of((UserCredential) authentication.getPrincipal());
	}

}
