package com.system.Library.aspects;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class LoggingAspect {
	public static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@AfterThrowing(pointcut = "execution(* com.system.Library.application.service.*.*(..))", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("\n===== EXCEPTION LOGGING =====").append("\nSignature    : ")
				.append(joinPoint.getSignature()).append("\nArguments    : ")
				.append(StringUtils.join(joinPoint.getArgs(), ", ")).append("\nException    : ")
				.append(ex.getClass().getSimpleName()).append("\nMessage      : ").append(ex.getMessage())
				.append("\n================================\n");
		log.error(logMessage.toString());
	}
}
