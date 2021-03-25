package it.eng.database.store.dmpk_blob.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.database.store.bean.StorageStoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBlobGetdocelettronicoforupdBean")
public class DmpkBlobGetdocelettronicoforupdBean extends StorageStoreBean implements Serializable{

	private static final String storeName = "DMPK_BLOB_GETDOCELETTRONICOFORUPD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idstoragein;
	private java.lang.String iddocelettronicoin;
	private byte[] documentoout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdstoragein(){return idstoragein;}
    public java.lang.String getIddocelettronicoin(){return iddocelettronicoin;}
    public byte[] getDocumentoout(){return documentoout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdstoragein(java.lang.String value){this.idstoragein=value;}
    public void setIddocelettronicoin(java.lang.String value){this.iddocelettronicoin=value;}
    public void setDocumentoout(byte[] value){this.documentoout=value;}
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