package it.eng.aurigamailbusiness.database.utility.bean.search;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipiNofificaInteropRicevute implements Serializable{
	ECCEZIONE, CONFERMA, AGGIORNAMENTO, ANNULLAMENTO
}
