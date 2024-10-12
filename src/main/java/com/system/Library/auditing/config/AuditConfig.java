package com.system.Library.auditing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.system.Library.security.entity.UserCredential;

@Configuration
@EnableJpaAuditing

public class AuditConfig {

	@Bean
	public AuditorAware<UserCredential> auditorProvider() {
		return new CustomAuditorAware();
	}
}
