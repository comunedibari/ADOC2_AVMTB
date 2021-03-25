package it.eng.auriga.ui.module.layout.client.gestioneatti.attiinlavorazione;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiCompletiLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

/**
 * Visualizza la lista degli atti in lavorazione completi
 * 
 * @author Dancrist
 *
 */
public class AttiCompletiInLavorazioneLayout extends AttiCompletiLayout {

	public AttiCompletiInLavorazioneLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public AttiCompletiInLavorazioneLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public AttiCompletiInLavorazioneLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public AttiCompletiInLavorazioneLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(	nomeEntita,
				new GWTRestDataSource("AttiCompletiInLavorazioneDataSource", "idProcedimento", FieldType.TEXT), 
				createFilter(nomeEntita), 
				new AttiCompletiInLavorazioneList(nomeEntita),
				new AttiDetail(nomeEntita), finalita, flgSelezioneSingola, showOnlyDetail);
	}

	private static AttiCompletiInLavorazioneFilter createFilter(String nomeEntita) {

		Map<String, String> extraParam = new HashMap<String, String>();

		extraParam.put("showFilterSmistamentoAtti", Boolean.toString(isAttivoSmistamentoAtti()));
		extraParam.put("showFilterCentroDiCosto", Boolean.toString(AurigaLayout.isAttivoClienteCOTO()));
		extraParam.put("showFilterDataScadenza", Boolean.toString(AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")));
		
		return new AttiCompletiInLavorazioneFilter(nomeEntita, extraParam);
	}
}