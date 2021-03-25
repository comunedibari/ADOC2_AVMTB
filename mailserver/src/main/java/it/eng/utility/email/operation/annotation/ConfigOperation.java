package it.eng.utility.email.operation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operazioni di configurazione
 * 
 * @author michele
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigOperation {

	/**
	 * Nome del parametro di configurazione a database
	 * 
	 * @return
	 */
	String name();

	/**
	 * Descrizione del parametro
	 * 
	 * @return
	 */
	String description();

	/**
	 * titolo del parametro
	 * 
	 * @return
	 */
	String title();

}