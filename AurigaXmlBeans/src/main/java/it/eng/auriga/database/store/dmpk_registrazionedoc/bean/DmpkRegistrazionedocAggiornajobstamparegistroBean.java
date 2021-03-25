package it.eng.auriga.database.store.dmpk_registrazionedoc.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocAggiornajobstamparegistroBean")
public class DmpkRegistrazionedocAggiornajobstamparegistroBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_AGGIORNAJOBSTAMPAREGISTRO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idjobin;
	private java.lang.String esitostampain;
	private java.lang.String motivierrorein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flginvioconservazionein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdjobin(){return idjobin;}
    public java.lang.String getEsitostampain(){return esitostampain;}
    public java.lang.String getMotivierrorein(){return motivierrorein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlginvioconservazionein(){return flginvioconservazionein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdjobin(java.math.BigDecimal value){this.idjobin=value;}
    public void setEsitostampain(java.lang.String value){this.esitostampain=value;}
    public void setMotivierrorein(java.lang.String value){this.motivierrorein=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlginvioconservazionein(java.lang.Integer value){this.flginvioconservazionein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    