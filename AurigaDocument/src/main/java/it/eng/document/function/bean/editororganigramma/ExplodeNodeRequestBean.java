package it.eng.document.function.bean.editororganigramma;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExplodeNodeRequestBean {

	private String idNodeToExplode;
	
	private BigDecimal nroProgrRootNode;

	public String getIdNodeToExplode() {
		return idNodeToExplode;
	}

	public void setIdNodeToExplode(String idNodeToExplode) {
		this.idNodeToExplode = idNodeToExplode;
	}

	public BigDecimal getNroProgrRootNode() {
		return nroProgrRootNode;
	}

	public void setNroProgrRootNode(BigDecimal nroProgrRootNode) {
		this.nroProgrRootNode = nroProgrRootNode;
	}
	
}
