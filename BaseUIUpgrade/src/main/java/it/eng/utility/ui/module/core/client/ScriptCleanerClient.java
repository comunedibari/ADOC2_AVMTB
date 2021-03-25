package it.eng.utility.ui.module.core.client;

import com.google.gwt.regexp.shared.RegExp;

public class ScriptCleanerClient {
	
	private static RegExp[] patterns = new RegExp[]{
	        // Script fragments
			RegExp.compile("<script>(.*?)</script>", "gim"),
	        // src='...'
			RegExp.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", "gim"),
			RegExp.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", "gim"),
			RegExp.compile("src[\r\n]*=[\r\n]*\'(.*?)\'", "gim"),
			RegExp.compile("src[\r\n]*=[\r\n]*\"(.*?)\"", "gim"),
	        // lonely script tags
			RegExp.compile("</script>", "gi"),
			RegExp.compile("<script(.*?)>", "gim"),
			// lonely iFrame tags
			RegExp.compile("<iframe(.*?)>", "gim"),
			RegExp.compile("</iframe>", "gi"),
	        // eval(...)
			RegExp.compile("eval\\((.*?)\\)", "gim"),
	        // expression(...)
			RegExp.compile("expression\\((.*?)\\)", "gim"),
	        // javascript:...
			RegExp.compile("javascript:", "gi"),
	        // vbscript:...
			RegExp.compile("vbscript:", "gi"),
	        // onload(...)=...
			RegExp.compile("onload(.*?)=", "gim")
	    };


	private boolean enabled;
	
	public ScriptCleanerClient() {
		this.enabled = false;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String clearFromScript(String stringToClean) {		
		if (enabled && stringToClean != null && !"".equalsIgnoreCase(stringToClean)) {
			// Avoid null characters
			stringToClean = stringToClean.replaceAll("\0", "");
			
			// Elimino gli script
			for (RegExp scriptPattern : patterns){
				stringToClean = scriptPattern.replace(stringToClean, "");
			}
			
		}
       
       return stringToClean;
	};
	
	public Object clearFromScript(Object objectToClean) {
		if (enabled && objectToClean instanceof String) {
			objectToClean = clearFromScript((String) objectToClean);
		}
		
		return objectToClean;
	};
	
}
