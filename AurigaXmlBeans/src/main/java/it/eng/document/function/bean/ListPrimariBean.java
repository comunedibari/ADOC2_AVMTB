package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ListPrimariBean implements Serializable{

	private static final long serialVersionUID = -5711006155661894702L;
	private List<FilePrimarioBean> primari;

	public List<FilePrimarioBean> getPrimari() {
		return primari;
	}

	public void setPrimari(List<FilePrimarioBean> primari) {
		this.primari = primari;
	}
}
