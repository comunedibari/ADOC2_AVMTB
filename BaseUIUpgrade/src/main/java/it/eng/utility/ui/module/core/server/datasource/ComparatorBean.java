package it.eng.utility.ui.module.core.server.datasource;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ComparatorBean <T> {

	private T oldBean;
	private T newBean;
	
	
	public T getOldBean() {
		return oldBean;
	}
	public void setOldBean(T oldBean) {
		this.oldBean = oldBean;
	}
	public T getNewBean() {
		return newBean;
	}
	public void setNewBean(T newBean) {
		this.newBean = newBean;
	}
	
	public Class getClassType(){
		return oldBean.getClass();
	}
}
