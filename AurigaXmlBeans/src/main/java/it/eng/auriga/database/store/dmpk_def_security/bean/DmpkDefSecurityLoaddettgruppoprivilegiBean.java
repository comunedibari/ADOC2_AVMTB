package it.eng.auriga.database.store.dmpk_def_security.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoaddettgruppoprivilegiBean")
public class DmpkDefSecurityLoaddettgruppoprivilegiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADDETTGRUPPOPRIVILEGI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idgruppoprivio;
	private java.lang.String nomegruppoprivio;
	private java.lang.String notegruppoprivout;
	private java.lang.String ciprovgruppoprivout;
	private java.lang.Integer flglockedout;
	private java.lang.Integer flgnolistaprivsin;
	private java.lang.String xmlprivilegiout;
	private java.lang.Integer bachsizeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdgruppoprivio(){return idgruppoprivio;}
    public java.lang.String getNomegruppoprivio(){return nomegruppoprivio;}
    public java.lang.String getNotegruppoprivout(){return notegruppoprivout;}
    public java.lang.String getCiprovgruppoprivout(){return ciprovgruppoprivout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.Integer getFlgnolistaprivsin(){return flgnolistaprivsin;}
    public java.lang.String getXmlprivilegiout(){return xmlprivilegiout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdgruppoprivio(java.math.BigDecimal value){this.idgruppoprivio=value;}
    public void setNomegruppoprivio(java.lang.String value){this.nomegruppoprivio=value;}
    public void setNotegruppoprivout(java.lang.String value){this.notegruppoprivout=value;}
    public void setCiprovgruppoprivout(java.lang.String value){this.ciprovgruppoprivout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setFlgnolistaprivsin(java.lang.Integer value){this.flgnolistaprivsin=value;}
    public void setXmlprivilegiout(java.lang.String value){this.xmlprivilegiout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    