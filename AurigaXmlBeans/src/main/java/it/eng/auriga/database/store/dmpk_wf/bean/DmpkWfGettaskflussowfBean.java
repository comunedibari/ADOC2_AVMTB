package it.eng.auriga.database.store.dmpk_wf.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGettaskflussowfBean")
public class DmpkWfGettaskflussowfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETTASKFLUSSOWF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String bpmnxml;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getBpmnxml(){return bpmnxml;}
    
    public void setBpmnxml(java.lang.String value){this.bpmnxml=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    