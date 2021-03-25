package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice;

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.BackDetailInterface;

import com.smartgwt.client.data.Record;

public abstract class AllegaDocumentazioneBackDetail extends AllegaDocumentazioneDetail implements BackDetailInterface {
	
	public AllegaDocumentazioneBackDetail(String nomeEntita, String idProcess, Record pRecordEvento, String idUd, DettaglioPraticaLayout dettaglioPraticaLayout) {
	
		super(nomeEntita, idProcess, pRecordEvento, idUd, dettaglioPraticaLayout);
		
	}

}