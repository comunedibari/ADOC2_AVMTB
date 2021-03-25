package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2Bean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;

public class TaskNuovaPropostaAtto2FileFirmatiBean {

	private NuovaPropostaAtto2Bean protocolloOriginale;
	private TaskFileDaFirmareBean fileFirmati;
	
	public NuovaPropostaAtto2Bean getProtocolloOriginale() {
		return protocolloOriginale;
	}
	public void setProtocolloOriginale(NuovaPropostaAtto2Bean protocolloOriginale) {
		this.protocolloOriginale = protocolloOriginale;
	}
	public TaskFileDaFirmareBean getFileFirmati() {
		return fileFirmati;
	}
	public void setFileFirmati(TaskFileDaFirmareBean fileFirmati) {
		this.fileFirmati = fileFirmati;
	}
	
}
