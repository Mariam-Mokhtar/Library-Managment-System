package com.system.Library.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LogTimeAspect {
	public static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.system.Library.application.repository.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis(); // Start time
		log.info("Entering method: " + joinPoint.getSignature().getName());
		Object result;
		try {
			result = joinPoint.proceed(); // Proceed with the method call
		} catch (Throwable throwable) {
			throw throwable; // Re-throw the exception after logging
		} finally {
			long duration = System.currentTimeMillis() - startTime; // Calculate execution time
			log.info("Execution time of method: " + joinPoint.getSignature().getName() + " is " + duration + " ms");
		}
		return result;
	}
}
