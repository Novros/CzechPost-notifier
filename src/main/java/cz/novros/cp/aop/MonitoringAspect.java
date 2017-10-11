package cz.novros.cp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Monitor every service and measure time of invocation methods.
 */
@Aspect
@Component
@Slf4j(topic = "monitoring")
public class MonitoringAspect extends AbstractAspect {

	@Around("execution(* cz.novros.cp.*..*Service+.*(..)) && target(service)")
	public Object logServiceAccess(final ProceedingJoinPoint jp, final Object service) throws Throwable {
		final long startTime = System.currentTimeMillis();
		Object result = jp.proceed();
		final long totalTime = System.currentTimeMillis() - startTime;

		log.info("{} | Invocation time {} ms ", jp.getSignature().toShortString(), totalTime);

		return result;
	}
}