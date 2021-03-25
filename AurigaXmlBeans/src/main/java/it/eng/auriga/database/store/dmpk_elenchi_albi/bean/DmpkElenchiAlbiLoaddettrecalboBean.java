package it.eng.auriga.database.store.dmpk_elenchi_albi.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkElenchiAlbiLoaddettrecalboBean")
public class DmpkElenchiAlbiLoaddettrecalboBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ELENCHI_ALBI_LOADDETTRECALBO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idrecelencoalboin;
	private java.lang.String attributirecxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String finalitain;
	private java.lang.Integer flgselezionabileout;
	private java.lang.Integer flgattivoout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdrecelencoalboin(){return idrecelencoalboin;}
    public java.lang.String getAttributirecxmlout(){return attributirecxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFinalitain(){return finalitain;}
    public java.lang.Integer getFlgselezionabileout(){return flgselezionabileout;}
    public java.lang.Integer getFlgattivoout(){return flgattivoout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdrecelencoalboin(java.math.BigDecimal value){this.idrecelencoalboin=value;}
    public void setAttributirecxmlout(java.lang.String value){this.attributirecxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    public void setFlgselezionabileout(java.lang.Integer value){this.flgselezionabileout=value;}
    public void setFlgattivoout(java.lang.Integer value){this.flgattivoout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    