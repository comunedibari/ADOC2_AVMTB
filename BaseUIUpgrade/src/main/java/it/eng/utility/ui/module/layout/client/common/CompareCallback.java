package it.eng.utility.ui.module.layout.client.common;

import com.smartgwt.client.data.Record;

public interface CompareCallback {

	public void noChanges(Record lRecord);
	
	public void changed(Record lRecord);
	
}
