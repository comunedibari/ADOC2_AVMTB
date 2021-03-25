package it.eng.auriga.ui.module.layout.client;

/**
 * 
 * @author DANCRIST
 *
 */

public class GWTDebug {
	
	 private static boolean enabled = false;
	    
	 public static void enable() { 
	  enabled = true; 
	 }
	 
	 public static void disable() { 
	  enabled = false; 
	 }
	 
	 public static void printLog( final String s ) { 
		 try{
			 if( enabled ) nativeConsoleLog( s ); 
		 }catch (Exception e) {
		   
		 }
	 }

	 public static native void nativeConsoleLog(String text)
	 /*-{
	     console.log(text);
	 }-*/;
	 
}
