package it.eng.auriga.database.store.dmpk_wf.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGetbpmnxmlBean")
public class DmpkWfGetbpmnxmlBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETBPMNXML";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String citypeflussowfin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCitypeflussowfin(){return citypeflussowfin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setCitypeflussowfin(java.lang.String value){this.citypeflussowfin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    