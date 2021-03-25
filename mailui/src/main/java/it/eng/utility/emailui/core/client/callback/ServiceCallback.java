package it.eng.utility.emailui.core.client.callback;

/**
 * Interfaccia di callback
 * @author michele
 *
 * @param <E>
 */
public interface ServiceCallback<E> {

	public void execute(E object); 	
	
}
