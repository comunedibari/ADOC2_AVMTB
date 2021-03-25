package it.eng.auriga.database.store.dmpk_def_security.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityFindfunzioneBean")
public class DmpkDefSecurityFindfunzioneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_FINDFUNZIONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String codfunzin;
	private java.lang.String desfunzin;
	private java.lang.Integer flgsolodispin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getCodfunzin(){return codfunzin;}
    public java.lang.String getDesfunzin(){return desfunzin;}
    public java.lang.Integer getFlgsolodispin(){return flgsolodispin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setCodfunzin(java.lang.String value){this.codfunzin=value;}
    public void setDesfunzin(java.lang.String value){this.desfunzin=value;}
    public void setFlgsolodispin(java.lang.Integer value){this.flgsolodispin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    