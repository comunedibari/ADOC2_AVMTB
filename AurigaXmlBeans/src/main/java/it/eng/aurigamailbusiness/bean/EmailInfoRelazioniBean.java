package it.eng.aurigamailbusiness.bean;

import it.eng.core.business.beans.AbstractBean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmailInfoRelazioniBean extends AbstractBean {

	private static final long serialVersionUID = -3550804011262243976L;

	private List<TRelEmailMgoBean> relazioni;

	public List<TRelEmailMgoBean> getRelazioni() {
		return relazioni;
	}

	public void setRelazioni(List<TRelEmailMgoBean> relazioni) {
		this.relazioni = relazioni;
	}


}
