package cz.novros.cp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspect for log every controller and service method invocation.
 */
@Aspect
@Component
@Slf4j(topic = "main")
public class LoggerAspect extends AbstractAspect {

	@Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) && @annotation(mapping) && target(controller)")
	public void logControllerAccess(final RequestMapping mapping, final Object controller) throws Throwable {
		log.info("Executing {} request on {}.", getRequestMapping(mapping), getClassName(controller));
	}

	@Around("execution(* cz.novros.cp.*..*Service+.*(..)) && target(service)")
	public Object logServiceAccess(final ProceedingJoinPoint jp, final Object service) throws Throwable {
		log.info("Executing {}.{}", getClassName(service), getMethodSignatureWithValueArgs(jp)); // FIXME Not good for open passwords.
		return jp.proceed(jp.getArgs());
	}
}
