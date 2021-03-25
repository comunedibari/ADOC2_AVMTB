package it.eng.document.function.bean.sicra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraMessaggiErrore implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> errori = new ArrayList<String>(0);

	public List<String> getErrori() {
		return errori;
	}

	public void setErrori(List<String> errori) {
		this.errori = errori;
	}

}
