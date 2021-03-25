package it.eng.auriga.database.store.dmpk_policy.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestprofudgrantaccessBean")
public class DmpkPolicyTestprofudgrantaccessBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTPROFUDGRANTACCESS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idudin;
	private java.lang.String accesstypein;
	private java.lang.Integer flgignoredellogin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getAccesstypein(){return accesstypein;}
    public java.lang.Integer getFlgignoredellogin(){return flgignoredellogin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setAccesstypein(java.lang.String value){this.accesstypein=value;}
    public void setFlgignoredellogin(java.lang.Integer value){this.flgignoredellogin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    