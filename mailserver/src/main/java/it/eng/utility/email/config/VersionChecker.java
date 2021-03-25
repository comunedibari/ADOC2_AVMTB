package it.eng.utility.email.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.email.util.xml.XmlUtil;
import it.eng.utility.email.util.xml.XmlValidationHandler;

public abstract class VersionChecker implements VersionCheckerInterface {

	private XmlValidationHandler validationHandler;
	private static Logger mLogger = LogManager.getLogger(VersionChecker.class);
	private Object bean;
	private List<String> errori;

	protected VersionChecker(XmlValidationHandler pValidationHandler){
		validationHandler = pValidationHandler;
		errori = new ArrayList<String>();
	}

	public enum Risultato {
		TIPO_NON_VALIDO, ERRORI, OK, EXCEPTION
	}

	@SuppressWarnings("unchecked")
	public <P> Risultato checkVersion(Class<P> pClass, String lStrXml) {
		mLogger.debug("Start check version: " + pClass.getName() + " - " + lStrXml);
		try {
			Object lObjectNew = XmlUtil.xmlToObject(lStrXml, validationHandler);
			mLogger.debug("Recuperato l'object");
			JAXBElement<P> lJAXBElementNew = ((JAXBElement<P>) (lObjectNew));
			P lSegnaturaNew = lJAXBElementNew.getValue();
			try {
				pClass.cast(lSegnaturaNew);
				mLogger.debug("Cast tentato");
			} catch (ClassCastException lClassCastException){
				return Risultato.TIPO_NON_VALIDO;
			}	
			if (validationHandler.getErrori()!=null && validationHandler.getErrori().size()>0){
				errori = validationHandler.getErrori();
				for (String lString : validationHandler.getErrori()){
					mLogger.debug("Errore di validazione" + lString);
				}
				return Risultato.ERRORI;
			}
			setBean(lSegnaturaNew);
			return Risultato.OK;
		} catch (Exception e) {
			return Risultato.EXCEPTION;
		}
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}
	
	@Override
	public List<String> getErrori() {
		// TODO Auto-generated method stub
		return errori;
	}
	
}
