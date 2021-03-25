package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RecuperaDocumentoInBean implements Serializable {

	private BigDecimal idUd;
	private String tsAnnDati;
	private String flgSoloAbilAzioni;
	private BigDecimal idProcess;
	private String taskName;
	private String idRichPubbl;
	
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public String getTsAnnDati() {
		return tsAnnDati;
	}

	public void setTsAnnDati(String tsAnnDati) {
		this.tsAnnDati = tsAnnDati;
	}

	public String getFlgSoloAbilAzioni() {
		return flgSoloAbilAzioni;
	}

	public void setFlgSoloAbilAzioni(String flgSoloAbilAzioni) {
		this.flgSoloAbilAzioni = flgSoloAbilAzioni;
	}

	public BigDecimal getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(BigDecimal idProcess) {
		this.idProcess = idProcess;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getIdRichPubbl() {
		return idRichPubbl;
	}

	public void setIdRichPubbl(String idRichPubbl) {
		this.idRichPubbl = idRichPubbl;
	}
	
}
