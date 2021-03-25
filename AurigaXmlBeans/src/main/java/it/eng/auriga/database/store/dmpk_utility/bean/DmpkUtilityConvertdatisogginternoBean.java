package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityConvertdatisogginternoBean")
public class DmpkUtilityConvertdatisogginternoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_CONVERTDATISOGGINTERNO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.Object datisoggintin;
	private java.lang.String[] datisoggintout;
	private java.lang.String tsrifin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnodecodificasoggin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.Object getDatisoggintin(){return datisoggintin;}
    public java.lang.String[] getDatisoggintout(){return datisoggintout;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnodecodificasoggin(){return flgnodecodificasoggin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setDatisoggintin(java.lang.Object value){this.datisoggintin=value;}
    public void setDatisoggintout(java.lang.String[] value){this.datisoggintout=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnodecodificasoggin(java.lang.Integer value){this.flgnodecodificasoggin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    