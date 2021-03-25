package it.eng.auriga.database.store.dmpk_dmlsuoggetti.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDmlsuoggettiFetchqueryBean")
public class DmpkDmlsuoggettiFetchqueryBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DMLSUOGGETTI_FETCHQUERY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String querystrin;
	private java.math.BigDecimal maxnumrecin;
	private java.lang.Object resultqueryout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getQuerystrin(){return querystrin;}
    public java.math.BigDecimal getMaxnumrecin(){return maxnumrecin;}
    public java.lang.Object getResultqueryout(){return resultqueryout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setQuerystrin(java.lang.String value){this.querystrin=value;}
    public void setMaxnumrecin(java.math.BigDecimal value){this.maxnumrecin=value;}
    public void setResultqueryout(java.lang.Object value){this.resultqueryout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    