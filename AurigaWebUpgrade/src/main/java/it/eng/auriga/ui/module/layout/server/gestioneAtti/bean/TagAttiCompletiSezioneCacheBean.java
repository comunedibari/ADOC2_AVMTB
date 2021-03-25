package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.document.XmlVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class TagAttiCompletiSezioneCacheBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@XmlVariabile(nome="@Tag", tipo=XmlVariabile.TipoVariabile.LISTA)
	private List<TagAttoXmlBean> listaTag;


	public List<TagAttoXmlBean> getListaTag() {
		return listaTag;
	}


	public void setListaTag(List<TagAttoXmlBean> listaTag) {
		this.listaTag = listaTag;
	}

}