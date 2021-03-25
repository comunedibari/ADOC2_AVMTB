package it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean;

import java.util.List;

public class LoadAttrDinamiciOutputBean {

	private Integer flgMostraAltriAttr;
	private List<AttributoBean> attributiAdd;
	
	public Integer getFlgMostraAltriAttr() {
		return flgMostraAltriAttr;
	}
	public void setFlgMostraAltriAttr(Integer flgMostraAltriAttr) {
		this.flgMostraAltriAttr = flgMostraAltriAttr;
	}
	public List<AttributoBean> getAttributiAdd() {
		return attributiAdd;
	}
	public void setAttributiAdd(List<AttributoBean> attributiAdd) {
		this.attributiAdd = attributiAdd;
	}
	
}
