package it.eng.utility.storageutil.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import it.eng.utility.storageutil.enums.StorageType;

/**
 * Annotation che identifica il tipo di storage che si sta implementando
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoStorage {
	
	StorageType tipo();

}