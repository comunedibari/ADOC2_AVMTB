package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio;

import com.smartgwt.client.data.Record;

public interface TaskFlussoInterface extends LoadDetailInterface, BackDetailInterface {
	
	public Record getRecordEvento();
	public void salvaDatiProvvisorio();
	public void salvaDatiDefinitivo();
	public String getNomeTastoSalvaProvvisorio();
	public String getNomeTastoSalvaDefinitivo();
	public boolean hasDocumento();

}
