package com.swayam.demo.trx.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* org.springframework.jdbc.core.JdbcOperations.*(..))")
	private void jdbcOperationsPointcut() {
	}

	@Around("jdbcOperationsPointcut()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

		String methodName = pjp.getSignature().getName();
		Object[] args = pjp.getArgs();
		String sql = "";
		if ((args.length >= 1) && (args[0] instanceof String)) {
			sql = (String) args[0];
		}

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object retVal = pjp.proceed();

		stopWatch.stop();

		LOGGER.info("JdbcOperations:{}({}) took {} millis", methodName, sql, stopWatch.getLastTaskTimeMillis());

		return retVal;
	}

}
