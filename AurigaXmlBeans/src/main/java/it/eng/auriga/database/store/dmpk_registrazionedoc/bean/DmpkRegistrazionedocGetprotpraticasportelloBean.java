package it.eng.auriga.database.store.dmpk_registrazionedoc.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocGetprotpraticasportelloBean")
public class DmpkRegistrazionedocGetprotpraticasportelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_GETPROTPRATICASPORTELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String codpraticain;
	private java.lang.String ideventoin;
	private java.math.BigDecimal nroprotocolloout;
	private java.lang.String dataoraprotocolloout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getCodpraticain(){return codpraticain;}
    public java.lang.String getIdeventoin(){return ideventoin;}
    public java.math.BigDecimal getNroprotocolloout(){return nroprotocolloout;}
    public java.lang.String getDataoraprotocolloout(){return dataoraprotocolloout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setCodpraticain(java.lang.String value){this.codpraticain=value;}
    public void setIdeventoin(java.lang.String value){this.ideventoin=value;}
    public void setNroprotocolloout(java.math.BigDecimal value){this.nroprotocolloout=value;}
    public void setDataoraprotocolloout(java.lang.String value){this.dataoraprotocolloout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    