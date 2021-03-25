package it.eng.auriga.database.store.dmpk_core.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetiddraftworkspaceBean")
public class DmpkCoreGetiddraftworkspaceBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETIDDRAFTWORKSPACE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal iduserin;
	private java.lang.String usernamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getUsernamein(){return usernamein;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    