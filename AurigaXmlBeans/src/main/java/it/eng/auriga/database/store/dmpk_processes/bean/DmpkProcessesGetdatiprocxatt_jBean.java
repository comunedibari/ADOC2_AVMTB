package it.eng.auriga.database.store.dmpk_processes.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesGetdatiprocxatt_jBean")
public class DmpkProcessesGetdatiprocxatt_jBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_GETDATIPROCXATT_J";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idprocessin;
	private java.lang.String xmldatiprocout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getXmldatiprocout(){return xmldatiprocout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setXmldatiprocout(java.lang.String value){this.xmldatiprocout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    