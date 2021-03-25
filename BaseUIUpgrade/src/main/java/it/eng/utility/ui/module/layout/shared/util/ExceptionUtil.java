package it.eng.utility.ui.module.layout.shared.util;

public class ExceptionUtil {
	
	public ExceptionUtil(){}

	public String getStackTrace(Throwable e) {
	    if(e != null) {
	    	StringBuffer sb = new StringBuffer(500);
		    if(e.getMessage() != null && !"".equals(e.getMessage())) { 
		    	sb.append(e.getClass().getName() + ": " + e.getMessage() + "\n");
		    } else {
		    	sb.append(e.getClass().getName() + "\n");
		    }
		    StackTraceElement[] st = e.getStackTrace();
		    if(st != null) {
			    for (int i = 0; i < st.length; i++) {
			      sb.append("\t at " + st[i].toString() + "\n");
			    }
		    }
		    return sb.toString();
	    }
	    return null;
	}
	
}
