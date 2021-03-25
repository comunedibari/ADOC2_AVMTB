package it.eng.utility.export;

import java.io.File;

import it.eng.utility.ui.module.core.server.bean.ExportBean;

public interface IExport {
	
	public void export(File file, ExportBean bean)throws Exception;
	
}
