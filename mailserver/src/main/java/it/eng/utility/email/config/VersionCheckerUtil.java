package it.eng.utility.email.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;

import it.eng.utility.email.config.VersionChecker.Risultato;
import it.eng.utility.email.operation.impl.interoperation.InteroperabilitaType;

public class VersionCheckerUtil {

	private VersionCheckerInterface versionCheckerNew;

	private VersionCheckerInterface versionCheckerOld;

	private Object bean;

	private List<String> errori;

	private boolean dtdValid;

	private boolean xmlValido;

	private String xmlIn;

	private InteroperabilitaType mInteroperabilitaType;
	
	private static Logger log = LogManager.getLogger(VersionCheckerUtil.class);

	public VersionCheckerUtil() throws IOException {
		versionCheckerNew = new NewVersionChecker();
		versionCheckerOld = new OldVersionChecker();
	}

	public boolean isNew(String lStrXmlIn, InteroperabilitaType type) {
		mInteroperabilitaType = type;
		log.debug("Il tipo di interoperabilità è: " + type);
		switch (type) {
		case SEGNATURA:
			return checkSegnatura(lStrXmlIn);
		case AGGIORNAMENTO_CONFERMA:
			return checkAggiornamentoConferma(lStrXmlIn);
		case ANNULLAMENTO_PROTOCOLLAZIONE:
			return checkAnnullamentoProtocollazione(lStrXmlIn);
		case CONFERMA_RICEZIONE:
			return checkConfermaRicezione(lStrXmlIn);
		case NOTIFICA_ECCEZIONE:
			return checkNotificaEccezione(lStrXmlIn);
		default:
			return false;
		}
	}

	private boolean checkNotificaEccezione(String lStrXmlIn) {
		xmlIn = lStrXmlIn;
		Risultato risultatoNew = versionCheckerNew.checkNotificaEccezione(lStrXmlIn);
		Risultato risultatoOld = versionCheckerOld.checkNotificaEccezione(lStrXmlIn);
		return analyzeResultIsNew(risultatoNew, risultatoOld);
	}

	private boolean checkConfermaRicezione(String lStrXmlIn) {
		xmlIn = lStrXmlIn;
		Risultato risultatoNew = versionCheckerNew.checkConfermaRicezione(lStrXmlIn);
		log.debug("Conferma ricezione New");
		Risultato risultatoOld = versionCheckerOld.checkConfermaRicezione(lStrXmlIn);
		log.debug("Conferma ricezione Old");
		return analyzeResultIsNew(risultatoNew, risultatoOld);
	}

	private boolean checkAnnullamentoProtocollazione(String lStrXmlIn) {
		xmlIn = lStrXmlIn;
		Risultato risultatoNew = versionCheckerNew.checkAnnullamentoProtocollazione(lStrXmlIn);
		log.debug("Annullamento protocollazione New");
		Risultato risultatoOld = versionCheckerOld.checkAnnullamentoProtocollazione(lStrXmlIn);
		log.debug("Annullamento protocollazione Old");
		return analyzeResultIsNew(risultatoNew, risultatoOld);
	}

	private boolean checkAggiornamentoConferma(String lStrXmlIn) {
		xmlIn = lStrXmlIn;
		Risultato risultatoNew = versionCheckerNew.checkAggiornamentoConferma(lStrXmlIn);
		log.debug("Aggiornamento conferma New");
		Risultato risultatoOld = versionCheckerOld.checkAggiornamentoConferma(lStrXmlIn);
		log.debug("Aggiornamento conferma Old");
		return analyzeResultIsNew(risultatoNew, risultatoOld);
	}

	private boolean checkSegnatura(String lStrXmlIn) {
		xmlIn = lStrXmlIn;
		Risultato risultatoNew = versionCheckerNew.checkSegnatura(lStrXmlIn);
		log.debug("Segnatura New");
		Risultato risultatoOld = versionCheckerOld.checkSegnatura(lStrXmlIn);
		log.debug("Segnatura Old");
		return analyzeResultIsNew(risultatoNew, risultatoOld);
	}

	private boolean analyzeResultIsNew(Risultato risultatoNew, Risultato risultatoOld) {
		// versione vecchia e' ok o presenta errori di validazione mentre quella nuova ha generato un'eccezione nel parsing
		if ((risultatoNew == Risultato.TIPO_NON_VALIDO || risultatoNew == Risultato.EXCEPTION)
				&& (risultatoOld == Risultato.OK || risultatoOld == Risultato.ERRORI)) {
			setBean(versionCheckerOld.getBean());
			setErrori(versionCheckerOld.getErrori());
			setXmlValido(risultatoOld != Risultato.ERRORI);
			setDtdValid(true);
			return false;
		}
		// versione nuova e' ok o presenta errori di validazione mentre quella vecchia ha generato un'eccezione nel parsing
		if ((risultatoOld == Risultato.TIPO_NON_VALIDO || risultatoOld == Risultato.EXCEPTION)
				&& (risultatoNew == Risultato.OK || risultatoNew == Risultato.ERRORI)) {
			setBean(versionCheckerNew.getBean());
			setErrori(versionCheckerNew.getErrori());
			setXmlValido(risultatoNew != Risultato.ERRORI);
			setDtdValid(true);
			return true;
		}
		// entrambe le versioni non sono valide
		if (risultatoOld == Risultato.EXCEPTION && risultatoNew == Risultato.EXCEPTION) {
			setDtdValid(false);
			return false;
		}
		// versione nuova e' valida
		if (risultatoNew == Risultato.OK && (risultatoOld == Risultato.ERRORI || risultatoOld == Risultato.EXCEPTION || risultatoOld == Risultato.TIPO_NON_VALIDO)){
			setBean(versionCheckerNew.getBean());
			setErrori(versionCheckerNew.getErrori());
			setXmlValido(true);
			setDtdValid(true);
			return true;
		}
		// versione vecchia e' valida
		if (risultatoOld == Risultato.OK && (risultatoNew == Risultato.ERRORI || risultatoNew == Risultato.EXCEPTION || risultatoNew == Risultato.TIPO_NON_VALIDO)){
			setBean(versionCheckerOld.getBean());
			setErrori(versionCheckerOld.getErrori());
			setXmlValido(true);
			setDtdValid(true);
			return false;
		}
		// entrambe le versioni presentano degli errori di validazione
		if ((risultatoNew == Risultato.ERRORI && risultatoOld == Risultato.ERRORI)) {
			boolean isPresentCodiceRegistro = verifyCodiceRegistro();
			setXmlValido(false);
			setDtdValid(true);
			// si verifica se nell'xml è presente il codice registro
			if (isPresentCodiceRegistro){
				//nuova versione
				setBean(versionCheckerNew.getBean());
				setErrori(versionCheckerNew.getErrori());
				return true;
			} else {
				setBean(versionCheckerOld.getBean());
				setErrori(versionCheckerOld.getErrori());
				return false;
			}
		}
		setDtdValid(false);
		return false;
	}

	private boolean verifyCodiceRegistro() {
		if (mInteroperabilitaType == InteroperabilitaType.SEGNATURA || mInteroperabilitaType == InteroperabilitaType.AGGIORNAMENTO_CONFERMA || mInteroperabilitaType == InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE
			|| mInteroperabilitaType == InteroperabilitaType.CONFERMA_RICEZIONE || mInteroperabilitaType == InteroperabilitaType.NOTIFICA_ECCEZIONE){
			DocumentBuilderFactory builderFactory =
				DocumentBuilderFactory.newInstance();
			try {
				builderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			} catch (ParserConfigurationException e1) {
				log.error("Eccezione verifyCodiceRegistro - setFeature: " + e1.getMessage(),e1);
			}
			DocumentBuilder builder = null;
			try {
				builder = builderFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				log.error("Eccezione verifyCodiceRegistro - newDocumentBuilder: " + e.getMessage(),e);
			}
			XPath xPath =  XPathFactory.newInstance().newXPath();
			String expression = "//CodiceRegistro";
			//read a string value
			Node codiceRegistro = null;
			try {
				codiceRegistro = (Node) xPath.compile(expression).evaluate(builder.parse(new ByteArrayInputStream(xmlIn.getBytes())), XPathConstants.NODE);
			} catch (Throwable e) {
				return false;
			}
			return (codiceRegistro != null);
		} else return false;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}

	public void setErrori(List<String> errori) {
		this.errori = errori;
	}

	public List<String> getErrori() {
		return errori;
	}

	public void setDtdValid(boolean dtdValid) {
		this.dtdValid = dtdValid;
	}

	public boolean isDtdValid() {
		return dtdValid;
	}

	public void setXmlValido(boolean xmlValido) {
		this.xmlValido = xmlValido;
	}

	public boolean isXmlValido() {
		return xmlValido;
	}

}
