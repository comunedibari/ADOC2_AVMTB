package it.eng.auriga.database.store.dmpk_def_security.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityMovepostazioneutenteBean")
public class DmpkDefSecurityMovepostazioneutenteBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_MOVEPOSTAZIONEUTENTE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String cireluseruoio;
	private java.math.BigDecimal iduoin;
	private java.lang.String nrilivelliuoin;
	private java.lang.String denominazioneuoin;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String flgtiporelin;
	private java.lang.Integer flginclsottouoin;
	private java.lang.Integer flginclscrivvirtin;
	private java.lang.String nuovaintestazionescrivaniaio;
	private java.math.BigDecimal idruoloammin;
	private java.lang.String desruoloammin;
	private java.lang.String listaesclusioniuoppin;
	private java.lang.Integer flgprimarioconruoloin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCireluseruoio(){return cireluseruoio;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.String getNrilivelliuoin(){return nrilivelliuoin;}
    public java.lang.String getDenominazioneuoin(){return denominazioneuoin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getFlgtiporelin(){return flgtiporelin;}
    public java.lang.Integer getFlginclsottouoin(){return flginclsottouoin;}
    public java.lang.Integer getFlginclscrivvirtin(){return flginclscrivvirtin;}
    public java.lang.String getNuovaintestazionescrivaniaio(){return nuovaintestazionescrivaniaio;}
    public java.math.BigDecimal getIdruoloammin(){return idruoloammin;}
    public java.lang.String getDesruoloammin(){return desruoloammin;}
    public java.lang.String getListaesclusioniuoppin(){return listaesclusioniuoppin;}
    public java.lang.Integer getFlgprimarioconruoloin(){return flgprimarioconruoloin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCireluseruoio(java.lang.String value){this.cireluseruoio=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setNrilivelliuoin(java.lang.String value){this.nrilivelliuoin=value;}
    public void setDenominazioneuoin(java.lang.String value){this.denominazioneuoin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlgtiporelin(java.lang.String value){this.flgtiporelin=value;}
    public void setFlginclsottouoin(java.lang.Integer value){this.flginclsottouoin=value;}
    public void setFlginclscrivvirtin(java.lang.Integer value){this.flginclscrivvirtin=value;}
    public void setNuovaintestazionescrivaniaio(java.lang.String value){this.nuovaintestazionescrivaniaio=value;}
    public void setIdruoloammin(java.math.BigDecimal value){this.idruoloammin=value;}
    public void setDesruoloammin(java.lang.String value){this.desruoloammin=value;}
    public void setListaesclusioniuoppin(java.lang.String value){this.listaesclusioniuoppin=value;}
    public void setFlgprimarioconruoloin(java.lang.Integer value){this.flgprimarioconruoloin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    