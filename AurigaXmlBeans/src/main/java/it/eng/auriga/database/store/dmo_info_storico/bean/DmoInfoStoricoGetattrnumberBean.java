package it.eng.auriga.database.store.dmo_info_storico.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmoInfoStoricoGetattrnumberBean")
public class DmoInfoStoricoGetattrnumberBean extends StoreBean implements Serializable{

	private static final String storeName = "DMO_INFO_STORICO_GETATTRNUMBER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object self;
	private java.lang.String attrnamein;
	private java.lang.Integer nrooccorrenzain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getSelf(){return self;}
    public java.lang.String getAttrnamein(){return attrnamein;}
    public java.lang.Integer getNrooccorrenzain(){return nrooccorrenzain;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setSelf(java.lang.Object value){this.self=value;}
    public void setAttrnamein(java.lang.String value){this.attrnamein=value;}
    public void setNrooccorrenzain(java.lang.Integer value){this.nrooccorrenzain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    