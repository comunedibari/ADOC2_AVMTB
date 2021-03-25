package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityIusezionaleBean")
public class DmpkUtilityIusezionaleBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_IUSEZIONALE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idsezionaleio;
	private java.lang.String codicesezionalein;
	private java.lang.String descrizionesezionalein;
	private java.math.BigDecimal annosezionalein;
	private java.math.BigDecimal rangesezionaledain;
	private java.math.BigDecimal rangesezionaleain;
	private java.lang.Integer flgannullatoin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdsezionaleio(){return idsezionaleio;}
    public java.lang.String getCodicesezionalein(){return codicesezionalein;}
    public java.lang.String getDescrizionesezionalein(){return descrizionesezionalein;}
    public java.math.BigDecimal getAnnosezionalein(){return annosezionalein;}
    public java.math.BigDecimal getRangesezionaledain(){return rangesezionaledain;}
    public java.math.BigDecimal getRangesezionaleain(){return rangesezionaleain;}
    public java.lang.Integer getFlgannullatoin(){return flgannullatoin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsezionaleio(java.math.BigDecimal value){this.idsezionaleio=value;}
    public void setCodicesezionalein(java.lang.String value){this.codicesezionalein=value;}
    public void setDescrizionesezionalein(java.lang.String value){this.descrizionesezionalein=value;}
    public void setAnnosezionalein(java.math.BigDecimal value){this.annosezionalein=value;}
    public void setRangesezionaledain(java.math.BigDecimal value){this.rangesezionaledain=value;}
    public void setRangesezionaleain(java.math.BigDecimal value){this.rangesezionaleain=value;}
    public void setFlgannullatoin(java.lang.Integer value){this.flgannullatoin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    