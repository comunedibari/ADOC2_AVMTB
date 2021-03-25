package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttachAndPosizioneCollectionBean implements Serializable {

	private List<AttachAndPosizioneBean> lista;

	public void setLista(List<AttachAndPosizioneBean> lista) {
		this.lista = lista;
	}

	public List<AttachAndPosizioneBean> getLista() {
		return lista;
	}
}
