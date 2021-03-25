package it.eng.document.function.bean.sicra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraMessaggiAvviso implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> avvisi = new ArrayList<String>(0);

	public List<String> getAvvisi() {
		return avvisi;
	}

	public void setAvvisi(List<String> avvisi) {
		this.avvisi = avvisi;
	}

}
