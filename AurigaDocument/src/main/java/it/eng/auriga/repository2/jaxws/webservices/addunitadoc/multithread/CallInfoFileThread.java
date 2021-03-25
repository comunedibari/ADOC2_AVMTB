package it.eng.auriga.repository2.jaxws.webservices.addunitadoc.multithread;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.AttachWSBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.AddUdUtils;
import it.eng.document.storage.DocumentStorage;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CallInfoFileThread implements Callable<AttachWSBean> {
	
	private static Logger aLogger = Logger.getLogger(CallInfoFileThread.class.getName());
	
	private boolean flgImpresaInUnGiorno; 
	private String xml; 
	private int indiceFile; 
	private File fileAttach;
	private AurigaLoginBean pAurigaLoginBean;
	
    public CallInfoFileThread(boolean flgImpresaInUnGiorno, String xml, int indiceFile, File fileAttach, AurigaLoginBean pAurigaLoginBean) {
		super();
		this.flgImpresaInUnGiorno = flgImpresaInUnGiorno;
		this.xml = xml;
		this.indiceFile = indiceFile;
		this.fileAttach = fileAttach;
		this.pAurigaLoginBean = pAurigaLoginBean;
	}

	@Override
    public AttachWSBean call() throws Exception {
        try {
        	AttachWSBean attachWSBean =  AddUdUtils.buildAttachWSBean(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);  
        	
        	boolean isValid = AddUdUtils.checkRequiredAttribute(fileAttach.getName(), attachWSBean);
			
			if(!isValid) {
				AddUdUtils.retryCallFileOp(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);
			}
			
			return attachWSBean;
        } catch (Exception e) {
        	 throw new Exception(e.getMessage(), e);
        }
    }
	
}
