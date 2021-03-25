package it.eng.auriga.ui.module.layout.client.protocollazione;

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class PreviewWindowWithCallback extends PreviewWindow {
	
	private ServiceCallback<Record> callback;

	public PreviewWindowWithCallback(String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, String filename, ServiceCallback<Record> callback) {
		
		super(pUri, remoteUri, lInfoFileRecord, recordType, filename);
		this.callback = callback;
		
	}
	
	@Override
	public void manageOkClick() {
		
		if(callback != null) {
			callback.execute(null);
		}
	}
	
}
