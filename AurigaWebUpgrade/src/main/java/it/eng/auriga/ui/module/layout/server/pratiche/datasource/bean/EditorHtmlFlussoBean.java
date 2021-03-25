package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;

import java.util.List;

public class EditorHtmlFlussoBean extends AttProcBean {

	private List<IdFileBean> files;
	
	public List<IdFileBean> getFiles() {
		return files;
	}

	public void setFiles(List<IdFileBean> files) {
		this.files = files;
	}

}
