package it.eng.auriga.database.store.dmpk_int_cs.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsUpdstatotrasmpdvBean")
public class DmpkIntCsUpdstatotrasmpdvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_UPDSTATOTRASMPDV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idpdvin;
	private java.lang.String statotrasmpdvin;
	private java.lang.String errwarncodein;
	private java.lang.String errwarnmsgin;
	private java.lang.String ricevutatrasmin;
	private java.lang.String idpdvconsevatorein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String labelpdvin;
	private java.lang.String tsricezionericin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdpdvin(){return idpdvin;}
    public java.lang.String getStatotrasmpdvin(){return statotrasmpdvin;}
    public java.lang.String getErrwarncodein(){return errwarncodein;}
    public java.lang.String getErrwarnmsgin(){return errwarnmsgin;}
    public java.lang.String getRicevutatrasmin(){return ricevutatrasmin;}
    public java.lang.String getIdpdvconsevatorein(){return idpdvconsevatorein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getLabelpdvin(){return labelpdvin;}
    public java.lang.String getTsricezionericin(){return tsricezionericin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdpdvin(java.lang.String value){this.idpdvin=value;}
    public void setStatotrasmpdvin(java.lang.String value){this.statotrasmpdvin=value;}
    public void setErrwarncodein(java.lang.String value){this.errwarncodein=value;}
    public void setErrwarnmsgin(java.lang.String value){this.errwarnmsgin=value;}
    public void setRicevutatrasmin(java.lang.String value){this.ricevutatrasmin=value;}
    public void setIdpdvconsevatorein(java.lang.String value){this.idpdvconsevatorein=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setLabelpdvin(java.lang.String value){this.labelpdvin=value;}
    public void setTsricezionericin(java.lang.String value){this.tsricezionericin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    