package it.eng.auriga.database.store.dmpk_efact.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactFinddescspaooBean")
public class DmpkEfactFinddescspaooBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_FINDDESCSPAOO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String idciapplicazionein;
	private java.lang.String idciistapplicazionein;
	private java.lang.String descspaooout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getIdciapplicazionein(){return idciapplicazionein;}
    public java.lang.String getIdciistapplicazionein(){return idciistapplicazionein;}
    public java.lang.String getDescspaooout(){return descspaooout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdciapplicazionein(java.lang.String value){this.idciapplicazionein=value;}
    public void setIdciistapplicazionein(java.lang.String value){this.idciistapplicazionein=value;}
    public void setDescspaooout(java.lang.String value){this.descspaooout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    