package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinddoctype_jBean")
public class DmpkUtilityFinddoctype_jBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDDOCTYPE_J";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal iddoctypein;
	private java.lang.String nomedoctypein;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.String provcidoctypein;
	private java.math.BigDecimal iddoctypeout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    public java.lang.String getNomedoctypein(){return nomedoctypein;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.String getProvcidoctypein(){return provcidoctypein;}
    public java.math.BigDecimal getIddoctypeout(){return iddoctypeout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    public void setNomedoctypein(java.lang.String value){this.nomedoctypein=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setProvcidoctypein(java.lang.String value){this.provcidoctypein=value;}
    public void setIddoctypeout(java.math.BigDecimal value){this.iddoctypeout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    