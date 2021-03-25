package it.eng.auriga.function.result;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AurigaResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8380255735535138264L;

	private Object[] result;

	public void setResult(Object[] result) {
		this.result = result;
	}

	public Object[] getResult() {
		return result;
	}
	
	public AurigaResultBean(Object[] result){
		this.result = result;
	}
	
	public AurigaResultBean() {
		// TODO Auto-generated constructor stub
	}
	
}
