package it.eng.auriga.database.store.dmpk_process_types.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesAddattributo_proctyBean")
public class DmpkProcessTypesAddattributo_proctyBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_ADDATTRIBUTO_PROCTY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nomein;
	private java.lang.String labelin;
	private java.lang.String processtypesxmlin;
	private java.lang.Integer nroposizionein;
	private java.lang.Integer flgobbligin;
	private java.math.BigDecimal maxnumvaluesin;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getLabelin(){return labelin;}
    public java.lang.String getProcesstypesxmlin(){return processtypesxmlin;}
    public java.lang.Integer getNroposizionein(){return nroposizionein;}
    public java.lang.Integer getFlgobbligin(){return flgobbligin;}
    public java.math.BigDecimal getMaxnumvaluesin(){return maxnumvaluesin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setLabelin(java.lang.String value){this.labelin=value;}
    public void setProcesstypesxmlin(java.lang.String value){this.processtypesxmlin=value;}
    public void setNroposizionein(java.lang.Integer value){this.nroposizionein=value;}
    public void setFlgobbligin(java.lang.Integer value){this.flgobbligin=value;}
    public void setMaxnumvaluesin(java.math.BigDecimal value){this.maxnumvaluesin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
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