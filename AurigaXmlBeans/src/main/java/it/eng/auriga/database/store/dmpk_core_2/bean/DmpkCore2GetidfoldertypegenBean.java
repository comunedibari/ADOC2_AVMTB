package it.eng.auriga.database.store.dmpk_core_2.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2GetidfoldertypegenBean")
public class DmpkCore2GetidfoldertypegenBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_GETIDFOLDERTYPEGEN";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idfoldertypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdfoldertypein(){return idfoldertypein;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdfoldertypein(java.math.BigDecimal value){this.idfoldertypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    