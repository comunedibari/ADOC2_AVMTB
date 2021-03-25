package it.eng.utility.ui.module.layout.client.common.items;

import java.util.LinkedHashMap;

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;

public class SelectItemWithDisplay extends SelectItem {

	public SelectItemWithDisplay(String name, SelectGWTRestDataSource datasource) {
		super();
	    setName(name);  
	    setOptionDataSource(datasource);
	    setDisplayField("displayValue");
    }

    public SelectItemWithDisplay(String name, String title, SelectGWTRestDataSource datasource) {
    	super();
        setName(name);
		setTitle(title);
		setOptionDataSource(datasource);
		setDisplayField("displayValue");
    }
    
    public SelectItemWithDisplay(String name, LinkedHashMap valueMap) {
		super();
	    setName(name);  
	    setValueMap(valueMap);
	    setDisplayField("displayValue");
    }
    
    public SelectItemWithDisplay(String name, String title, LinkedHashMap valueMap) {
		super();
	    setName(name);  
	    setTitle(title);
	    setValueMap(valueMap);
	    setDisplayField("displayValue");
    }
    
}
