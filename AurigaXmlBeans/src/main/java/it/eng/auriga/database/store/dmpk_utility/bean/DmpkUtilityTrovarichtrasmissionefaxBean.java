package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTrovarichtrasmissionefaxBean")
public class DmpkUtilityTrovarichtrasmissionefaxBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TROVARICHTRASMISSIONEFAX";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idrichtrasmissioneio;
	private java.lang.String cifaxperfaxserverio;
	private java.lang.String faxservermittio;
	private java.lang.Integer flgsolosottomesseutentelavio;
	private java.lang.String codapplicazioneio;
	private java.lang.String codistapplicazioneio;
	private java.lang.String nrofaxdestio;
	private java.lang.String flgstatirichtrasmio;
	private java.lang.String richtrasmdaio;
	private java.lang.String richtrasmaio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdrichtrasmissioneio(){return idrichtrasmissioneio;}
    public java.lang.String getCifaxperfaxserverio(){return cifaxperfaxserverio;}
    public java.lang.String getFaxservermittio(){return faxservermittio;}
    public java.lang.Integer getFlgsolosottomesseutentelavio(){return flgsolosottomesseutentelavio;}
    public java.lang.String getCodapplicazioneio(){return codapplicazioneio;}
    public java.lang.String getCodistapplicazioneio(){return codistapplicazioneio;}
    public java.lang.String getNrofaxdestio(){return nrofaxdestio;}
    public java.lang.String getFlgstatirichtrasmio(){return flgstatirichtrasmio;}
    public java.lang.String getRichtrasmdaio(){return richtrasmdaio;}
    public java.lang.String getRichtrasmaio(){return richtrasmaio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdrichtrasmissioneio(java.math.BigDecimal value){this.idrichtrasmissioneio=value;}
    public void setCifaxperfaxserverio(java.lang.String value){this.cifaxperfaxserverio=value;}
    public void setFaxservermittio(java.lang.String value){this.faxservermittio=value;}
    public void setFlgsolosottomesseutentelavio(java.lang.Integer value){this.flgsolosottomesseutentelavio=value;}
    public void setCodapplicazioneio(java.lang.String value){this.codapplicazioneio=value;}
    public void setCodistapplicazioneio(java.lang.String value){this.codistapplicazioneio=value;}
    public void setNrofaxdestio(java.lang.String value){this.nrofaxdestio=value;}
    public void setFlgstatirichtrasmio(java.lang.String value){this.flgstatirichtrasmio=value;}
    public void setRichtrasmdaio(java.lang.String value){this.richtrasmdaio=value;}
    public void setRichtrasmaio(java.lang.String value){this.richtrasmaio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    