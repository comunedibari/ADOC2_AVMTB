package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityIuintopograficoBean")
public class DmpkUtilityIuintopograficoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_IUINTOPOGRAFICO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idtoponimoio;
	private java.lang.String nometoponimoin;
	private java.lang.String codrapidotoponimoin;
	private java.lang.String descrizionein;
	private java.lang.String ciprovtoponimoin;
	private java.lang.Integer flglockedin;
	private java.lang.String notetoponimoin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdtoponimoio(){return idtoponimoio;}
    public java.lang.String getNometoponimoin(){return nometoponimoin;}
    public java.lang.String getCodrapidotoponimoin(){return codrapidotoponimoin;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.lang.String getCiprovtoponimoin(){return ciprovtoponimoin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getNotetoponimoin(){return notetoponimoin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdtoponimoio(java.math.BigDecimal value){this.idtoponimoio=value;}
    public void setNometoponimoin(java.lang.String value){this.nometoponimoin=value;}
    public void setCodrapidotoponimoin(java.lang.String value){this.codrapidotoponimoin=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setCiprovtoponimoin(java.lang.String value){this.ciprovtoponimoin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setNotetoponimoin(java.lang.String value){this.notetoponimoin=value;}
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