package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityDecodetabcolvaluefromdictBean")
public class DmpkUtilityDecodetabcolvaluefromdictBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_DECODETABCOLVALUEFROMDICT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.lang.String targettablenamein;
	private java.lang.String targetcolnamein;
	private java.lang.String codicein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.lang.String getTargettablenamein(){return targettablenamein;}
    public java.lang.String getTargetcolnamein(){return targetcolnamein;}
    public java.lang.String getCodicein(){return codicein;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setFlgtpdominioautin(java.lang.Integer value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setTargettablenamein(java.lang.String value){this.targettablenamein=value;}
    public void setTargetcolnamein(java.lang.String value){this.targetcolnamein=value;}
    public void setCodicein(java.lang.String value){this.codicein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    