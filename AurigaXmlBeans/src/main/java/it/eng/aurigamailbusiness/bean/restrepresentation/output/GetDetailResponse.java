package it.eng.aurigamailbusiness.bean.restrepresentation.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.SezioneCacheXMLDatiEmailRecuperaDettaglioEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaRecuperaDettaglioEmail")
public class GetDetailResponse {
	
	@XmlElement(name="dettaglioEmail")
    private SezioneCacheXMLDatiEmailRecuperaDettaglioEmail datiEmail;

	public SezioneCacheXMLDatiEmailRecuperaDettaglioEmail getDatiEmail() {
		return datiEmail;
	}

	public void setDatiEmail(SezioneCacheXMLDatiEmailRecuperaDettaglioEmail datiEmail) {
		this.datiEmail = datiEmail;
	}

}
