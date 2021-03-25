package it.eng.utility.ui.module.layout.shared.bean;

import java.util.List;
import java.util.Map;



public abstract interface FilterPrivilegiUtil {
	
	public boolean isRequired(String nomeFiltro, String[] privilegi);
	public Map<String, List<String>> getMap();
	public void setContainer(FilterPrivilegiContainer pFilterPrivilegiContainer);
	public FilterPrivilegiContainer getContainer();
}
