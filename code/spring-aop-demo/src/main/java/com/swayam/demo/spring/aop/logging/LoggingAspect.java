package com.swayam.demo.spring.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* org.springframework.jdbc.core.JdbcOperations.*(..))")
    private void jdbcOperationsPointcut() {
    }

    @Around("jdbcOperationsPointcut()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

	LOGGER.info("I am here");
	// start stopwatch
	Object retVal = pjp.proceed();
	// stop stopwatch
	return retVal;
    }

}
