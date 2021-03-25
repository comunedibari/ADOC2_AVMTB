package it.eng.utility.export;

import java.io.File;

import it.eng.utility.export.type.ExportCSV;
import it.eng.utility.export.type.ExportPDF;
import it.eng.utility.export.type.ExportXLS;
import it.eng.utility.export.type.ExportXML;
import it.eng.utility.ui.module.core.server.bean.ExportBean;

/**
 * Classe mananger per l'esportazione dei dati dalle liste
 * @author Administrator
 *
 */
public class ExportManager {

	public boolean export(File file, ExportBean bean)throws Exception{
		switch(ExportType.valueOf(bean.getFormatoExport().toUpperCase())){		
			case CSV:
				new ExportCSV().export(file, bean);
				return true;
			case PDF:
				new ExportPDF().export(file, bean);	
				return true;			
			case XML:
				new ExportXML().export(file, bean);
				return true;				
			case XLS:
				new ExportXLS().export(file, bean);	
				return true;		
		}
		return false;
	}
}