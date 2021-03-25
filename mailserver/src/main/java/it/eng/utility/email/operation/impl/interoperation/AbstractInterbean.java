package it.eng.utility.email.operation.impl.interoperation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe che riunisce tutte gli attributi degli interbean comuni a vecchio e nuovo xsd
 * 
 * @author jravagnan
 *
 */
@XmlRootElement
public class AbstractInterbean {

	public static final String ERRORE_XML_DUPLICATO = "XML presente più di una volta\r\n";

	public static final String ERRORE_DTD_NON_VALIDO = "DTD non valido\r\n";

	public static final String ERRORE_XML_CONTRASTANTI = "E' presente più di un XML\r\n";

	public static final String ERRORE_XML_IDENTIFICATORE = "Identificatore del protocollo non presente\r\n";

	public static final String ERRORE_XML_MESSAGGIO_RICEVUTO = "Messaggio ricevuto non presente\r\n";

	public static final String ERRORE_XML_DATA_REGISTRAZIONE_RICEZIONE = "Data registrazione del protocollo ricevuto non presente o non valida\r\n";

	public static final String ERRORE_XML_DATA_REGISTRAZIONE_INVIO = "Data registrazione del protocollo inviato non presente o non valida\r\n";

	public static final String ERRORE_XML_NUMERO_REGISTRAZIONE_RICEZIONE = "Numero di registrazione del protocollo ricevuto non presente o non valido\r\n";

	public static final String ERRORE_XML_NUMERO_REGISTRAZIONE_INVIO = "Numero di registrazione del protocollo inviato non presente o non valido\r\n";

	public static final String ERRORE_XML_MOTIVO_ECCEZIONE = "Motivo eccezione non presente o non valido\r\n";

	public static final String ERRORE_XML_COD_AOO_INVIO = "Codice AOO di registrazione del protocollo inviato non presente o non valido\r\n";

	public static final String ERRORE_XML_COD_AOO_RICEZIONE = "Codice AOO di registrazione del protocollo ricevuto non presente o non valido\r\n";

	public static final String ERRORE_XML_COD_AMMINISTRAZIONE_INVIO = "Codice Amministrazione del protocollo inviato non presente o non valido\r\n";

	public static final String ERRORE_XML_COD_AMMINISTRAZIONE_RICEZIONE = "Codice Amministrazione del protocollo ricevuto non presente o non valido\r\n";

	private InteroperabilitaType type;

	private Boolean isXmlDuplicate;

	private Boolean isdtdValid;

	private Boolean isInteroperabile;

	private List<String> erroriConformita;

	private List<String> erroriXML;

	private Boolean isNew;

	public InteroperabilitaType getType() {
		return type;
	}

	public void setType(InteroperabilitaType type) {
		this.type = type;
	}

	public Boolean getIsXmlDuplicate() {
		return isXmlDuplicate;
	}

	public void setIsXmlDuplicate(Boolean isXmlDuplicate) {
		this.isXmlDuplicate = isXmlDuplicate;
	}

	public Boolean getIsdtdValid() {
		return isdtdValid;
	}

	public void setIsdtdValid(Boolean isdtdValid) {
		this.isdtdValid = isdtdValid;
	}

	public Boolean getIsInteroperabile() {
		return isInteroperabile;
	}

	public void setIsInteroperabile(Boolean isInteroperabile) {
		this.isInteroperabile = isInteroperabile;
	}

	public List<String> getErroriConformita() {
		return erroriConformita;
	}

	public void setErroriConformita(List<String> erroriConformita) {
		this.erroriConformita = erroriConformita;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public List<String> getErroriXML() {
		return erroriXML;
	}

	public void setErroriXML(List<String> erroriXML) {
		this.erroriXML = erroriXML;
	}

	public void addErroreXML(String erroreXML) {
		if (this.erroriXML == null) {
			this.erroriXML = new ArrayList<String>();
		}
		this.erroriXML.add(erroreXML);
	}

}
