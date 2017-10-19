package cz.novros.cp.aop;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j(topic = "error")
public class ExceptionHandlerAspect extends AbstractAspect {

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) && target(controller)")
	public ModelAndView handleException(final ProceedingJoinPoint jp, final Object controller) throws Throwable {
		Object view;

		try {
			view = jp.proceed();
		} catch (final Exception exception) {
			log.error("Error occurred in {}.", jp.getSignature().toShortString(), exception);

			return new ModelAndView("error", getModelMap(exception), getStatusCode(exception));
		}
		
		if (view instanceof String) {
			view = new ModelAndView((String) view);
		}

		return (ModelAndView) view;
	}

	private Map<String, Object> getModelMap(final Exception exception) {
		return ImmutableMap.of("errorMessage", getClassName(exception) + ":" + exception.getMessage(), "status", getStatusCode(exception));
	}

	private HttpStatus getStatusCode(final Exception exception) {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}