package it.eng.auriga.ui.module.layout.client.protocollazione;

import com.smartgwt.client.data.Record;

public abstract class PreviewWindowPageSelectionCallback {

	public abstract void executeSalva(Record record);
		
	public abstract void executeSalvaVersConOmissis(Record record);
	
	public abstract void executeOnError();

}
