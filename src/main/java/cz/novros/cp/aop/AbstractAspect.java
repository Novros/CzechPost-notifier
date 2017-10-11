package cz.novros.cp.aop;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.bind.annotation.RequestMapping;

public class AbstractAspect {

	protected String getRequestMapping(RequestMapping mapping) {
		final StringBuilder builder = new StringBuilder();

		if (mapping != null) {
			if (mapping.method() != null) {
				builder.append(mapping.method()[0]);
			}
			if (mapping.value() != null) {
				if (!builder.toString().isEmpty()) {
					builder.append(" ");
				}
				builder.append("\"").append(mapping.value()[0]).append("\"");
			}
		}

		return builder.toString();
	}

	protected String getClassName(final Object object) {
		return object.getClass().getSimpleName();
	}

	protected String getMethodSignatureWithValueArgs(@Nonnull final ProceedingJoinPoint jp) {
		return jp.getSignature().getName() + "(" + Arrays.toString(jp.getArgs()) + ")";
	}
}
