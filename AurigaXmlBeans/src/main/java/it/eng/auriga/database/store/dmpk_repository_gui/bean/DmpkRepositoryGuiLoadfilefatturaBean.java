package it.eng.auriga.database.store.dmpk_repository_gui.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiLoadfilefatturaBean")
public class DmpkRepositoryGuiLoadfilefatturaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_LOADFILEFATTURA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudin;
	private java.lang.Integer flgfilefatturain;
	private java.lang.Integer flgricevutein;
	private java.lang.String datiudxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnoscambioallprimarioin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.Integer getFlgfilefatturain(){return flgfilefatturain;}
    public java.lang.Integer getFlgricevutein(){return flgricevutein;}
    public java.lang.String getDatiudxmlout(){return datiudxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnoscambioallprimarioin(){return flgnoscambioallprimarioin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setFlgfilefatturain(java.lang.Integer value){this.flgfilefatturain=value;}
    public void setFlgricevutein(java.lang.Integer value){this.flgricevutein=value;}
    public void setDatiudxmlout(java.lang.String value){this.datiudxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnoscambioallprimarioin(java.lang.Integer value){this.flgnoscambioallprimarioin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    