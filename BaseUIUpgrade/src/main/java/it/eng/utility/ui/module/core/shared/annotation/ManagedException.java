package it.eng.utility.ui.module.core.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagedException {
	int status() default 50;
	Class<? extends ExceptionManager> gestore() default DefaultExceptionManager.class;
}
