package it.eng.utility.emailui.core.client.i18n.defaults;



/**
 * Interfaccia che espone i le costanti delle label da inserire nelle gui
 * @author michele
 *
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
	
	@Key("addColumn_title")
	String addColumn_title();
	
	@Key("removeColumn_title")
	String removeColumn_title();
	
	@Key("language")
	String language();
}