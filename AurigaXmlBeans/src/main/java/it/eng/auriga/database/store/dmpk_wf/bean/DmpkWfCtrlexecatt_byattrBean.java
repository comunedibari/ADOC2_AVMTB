package it.eng.auriga.database.store.dmpk_wf.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfCtrlexecatt_byattrBean")
public class DmpkWfCtrlexecatt_byattrBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_CTRLEXECATT_BYATTR";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flgwhenctrlin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String activitynamein;
	private java.lang.String codesitoattin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlgwhenctrlin(){return flgwhenctrlin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getCodesitoattin(){return codesitoattin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgwhenctrlin(java.lang.String value){this.flgwhenctrlin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setCodesitoattin(java.lang.String value){this.codesitoattin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    