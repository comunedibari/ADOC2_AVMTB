package it.eng.utility.storageutil.impl.centera;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface ContentPath {
	String access() default "";
}
