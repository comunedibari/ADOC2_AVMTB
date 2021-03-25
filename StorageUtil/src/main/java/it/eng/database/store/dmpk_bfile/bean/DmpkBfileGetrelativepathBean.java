package it.eng.database.store.dmpk_bfile.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.database.store.bean.StorageStoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBfileGetrelativepathBean")
public class DmpkBfileGetrelativepathBean extends StorageStoreBean implements Serializable{

	private static final String storeName = "DMPK_BFILE_GETRELATIVEPATH";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String fileuriin;
	private java.lang.String relativepathout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFileuriin(){return fileuriin;}
    public java.lang.String getRelativepathout(){return relativepathout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFileuriin(java.lang.String value){this.fileuriin=value;}
    public void setRelativepathout(java.lang.String value){this.relativepathout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    