package it.eng.document.function.bean.sicra;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraInputSetEsecutivitaAtto implements Serializable {

	private static final long serialVersionUID = 1L;

	private SicraImpegnoDaRendereEsecutivo impegno;
	private String messaggioXml;

	public SicraImpegnoDaRendereEsecutivo getImpegno() {
		return impegno;
	}

	public void setImpegno(SicraImpegnoDaRendereEsecutivo impegno) {
		this.impegno = impegno;
	}

	public String getMessaggioXml() {
		return messaggioXml;
	}

	public void setMessaggioXml(String messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

}
