package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetdatilivellisoBean")
public class DmpkUtilityGetdatilivellisoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETDATILIVELLISO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioautin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    