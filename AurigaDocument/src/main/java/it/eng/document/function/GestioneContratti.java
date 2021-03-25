package it.eng.document.function;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModContrattoInBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.xml.XmlUtilitySerializer;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service(name = "GestioneContratti")
public class GestioneContratti extends GestioneDocumenti {
	
	private static Logger mLogger = Logger.getLogger(GestioneContratti.class);
	
	@Operation(name = "creaContratto")
	public CreaModDocumentoOutBean creaContratto(AurigaLoginBean pAurigaLoginBean, CreaModContrattoInBean pCreaContrattoInBean, 
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
		lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();				
		lAdddocBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(pCreaContrattoInBean));	
		
		lAdddocBean.setFlgautocommitin(0); // blocco l'autocommit
		
		//Preparo la risposta
		BigDecimal idUdResult = null;
		//Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
						
		CreaModDocumentoOutBean lCreaDocumentoOutBean = new CreaModDocumentoOutBean();
		try {
			SubjectBean subject =  SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject); 
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				idUdResult = manageAddDocs(pAurigaLoginBean, pFilePrimarioBean,
						pAllegatiBean, lAdddocBean, lXmlUtilitySerializer,
						versioni, session);
				HibernateUtil.commit(session);				
			}catch(Exception e){
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException){
					BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException)e).getError());
					return lCreaDocumentoOutBean;
				} else {
					lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lCreaDocumentoOutBean;
				}
			}finally{
				HibernateUtil.release(session);
			}
			lCreaDocumentoOutBean.setIdUd(idUdResult);
			//Parte di versionamento
			Map<String, String> fileErrors = aggiungiFiles(pAurigaLoginBean, versioni);
			lCreaDocumentoOutBean.setFileInErrors(fileErrors);
			recuperaEstremi(pAurigaLoginBean, pCreaContrattoInBean, lCreaDocumentoOutBean);
		} catch (Exception e) {
			if (e instanceof StoreException){
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException)e).getError());
				return lCreaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
				return lCreaDocumentoOutBean;
			}
		}

		return lCreaDocumentoOutBean;
	}
	
	@Override
	public String settaBindXml(CreaModDocumentoInBean pModificaDocumentoInBean){
		
		String lStringXml = null;
		try{
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			 lStringXml = lXmlUtilitySerializer.bindXml(pModificaDocumentoInBean);
			 mLogger.debug("attributiVerXml "+ lStringXml);
			} catch(Exception e){
				mLogger.warn(e);
			}
			
		return lStringXml;
	}
	
}
