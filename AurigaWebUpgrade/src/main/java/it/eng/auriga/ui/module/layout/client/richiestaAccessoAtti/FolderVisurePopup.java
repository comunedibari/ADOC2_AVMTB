package it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

import it.eng.auriga.ui.module.layout.client.archivio.ArchivioLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class FolderVisurePopup extends ModalWindow {
		
	private ArchivioLayout portletLayout;
	
	public FolderVisurePopup(final Record record) {
		
		super("archivio", true);
		
		final String idFolder = record.getAttributeAsString("idUdFolder");
		
		final String estremiVisura = "visura SUE richiesta " + record.getAttribute("documentoInizialeProcedimento");
		
		setTitle("Documenti " + estremiVisura);
		
		portletLayout = new ArchivioLayout(null, null, null, null) {
			
			@Override  
			public void setPercorsoIniziale() {		
				tree.getDataSource().performCustomOperation("getPercorsoIniziale", new Record(), new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList percorso = record.getAttributeAsRecordList("percorso");
				    		navigator.setPercorso(percorso);	
				    		navigator.setFlgMostraContenuti(record.getAttributeAsBoolean("flgMostraContenuti"));
				    		flgMostraContenuti = navigator.getFlgMostraContenuti();
				    		altriParametri = record.getAttributeAsMap("altriParametri");
				    		esploraFromList(idFolder);				    		
						} 				
					}
				}, new DSRequest());					
			}
		};			
		portletLayout.setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
   
        setIcon("menu/archivio.png");            
	}	
}