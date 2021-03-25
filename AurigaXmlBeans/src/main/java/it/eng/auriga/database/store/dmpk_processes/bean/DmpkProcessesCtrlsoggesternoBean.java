package it.eng.auriga.database.store.dmpk_processes.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesCtrlsoggesternoBean")
public class DmpkProcessesCtrlsoggesternoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_CTRLSOGGESTERNO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal idprocessin;
	private java.lang.Integer flgvldsoggvsrubricain;
	private java.lang.String datisoggxmlio;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgescludictrlsucfin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.Integer getFlgvldsoggvsrubricain(){return flgvldsoggvsrubricain;}
    public java.lang.String getDatisoggxmlio(){return datisoggxmlio;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgescludictrlsucfin(){return flgescludictrlsucfin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setFlgvldsoggvsrubricain(java.lang.Integer value){this.flgvldsoggvsrubricain=value;}
    public void setDatisoggxmlio(java.lang.String value){this.datisoggxmlio=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgescludictrlsucfin(java.lang.Integer value){this.flgescludictrlsucfin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    