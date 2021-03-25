package it.eng.auriga.ui.module.layout.client.timbra;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * 
 * @author DANCRIST
 *
 */

public class TimbroUtil {
	
	public static void buildTimbro(Record lRecord) {
		Layout.showWaitPopup("Timbratura in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
		lGwtRestService.performCustomOperation("getTipologia", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {			
					Record result = response.getData()[0];
					result.setAttribute("skipScelteOpzioniCopertina", "true");
					generaTimbro(result);
				}else{
					Layout.addMessage(new MessageBean("Non è stato possibile apporre la timbratura","",MessageType.WARNING));
				}
			}
				
		},new DSRequest());
	}
	
	public static void buildDatiSegnatura(Record lRecord) {
		Layout.showWaitPopup("Timbratura in corso: potrebbe richiedere qualche secondo. Attendere...");
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
		lGwtRestService.performCustomOperation("getSegnatura", lRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {				
					Record result = response.getData()[0];
					generaTimbro(result);
				}else{
					Layout.addMessage(new MessageBean("Non è stato possibile apporre la segnatura","",MessageType.WARNING));
				}
			}
				
		},new DSRequest());
	}
	
	public static void generaTimbro(Record input){
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
		lGwtRestService.call(input, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				if (object.getAttributeAsBoolean("result")){	
					timbraCallBack(object);
				} else {
					Layout.addMessage(new MessageBean(object.getAttribute("error"), object.getAttribute("error"), MessageType.ERROR));
				}
			}
		});
	}
	
	
	protected static void timbraCallBack(Record object) {
		String uri = object.getAttribute("uri");
		String display = object.getAttribute("nomeFile");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", false);					
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");			
	}

}
