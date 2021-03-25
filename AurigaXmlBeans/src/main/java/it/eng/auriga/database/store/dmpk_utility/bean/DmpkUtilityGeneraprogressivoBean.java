package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGeneraprogressivoBean")
public class DmpkUtilityGeneraprogressivoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GENERAPROGRESSIVO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String codscopein;
	private java.lang.String condvaluein;
	private java.math.BigDecimal annoin;
	private java.math.BigDecimal progrgeneratoout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCodscopein(){return codscopein;}
    public java.lang.String getCondvaluein(){return condvaluein;}
    public java.math.BigDecimal getAnnoin(){return annoin;}
    public java.math.BigDecimal getProgrgeneratoout(){return progrgeneratoout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCodscopein(java.lang.String value){this.codscopein=value;}
    public void setCondvaluein(java.lang.String value){this.condvaluein=value;}
    public void setAnnoin(java.math.BigDecimal value){this.annoin=value;}
    public void setProgrgeneratoout(java.math.BigDecimal value){this.progrgeneratoout=value;}
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