package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGettsregnumudBean")
public class DmpkUtilityGettsregnumudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETTSREGNUMUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idudin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregio;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregio(){return siglaregio;}
    
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregio(java.lang.String value){this.siglaregio=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    