package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaPaginataRequest extends ContabiliaBaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer numeroElementiPerPagina;
	private Integer numeroPagina;
	
	public Integer getNumeroElementiPerPagina() {
		return numeroElementiPerPagina;
	}
	
	public void setNumeroElementiPerPagina(Integer numeroElementiPerPagina) {
		this.numeroElementiPerPagina = numeroElementiPerPagina;
	}
	
	public Integer getNumeroPagina() {
		return numeroPagina;
	}
	
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaPaginataRequest [numeroElementiPerPagina=" + numeroElementiPerPagina
				+ ", numeroPagina=" + numeroPagina + "]";
	}
	
}
