package it.eng.auriga.database.store.dmpk_modelli_doc.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocTrovamodelliBean")
public class DmpkModelliDocTrovamodelliBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_TROVAMODELLI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idmodelloio;
	private java.lang.String nomemodelloio;
	private java.lang.String descrizionemodelloio;
	private java.lang.String tyobjrelatedio;
	private java.math.BigDecimal idtyobjrelatedio;
	private java.lang.String nometyobjrelatedio;
	private java.lang.String ciprovmodelloio;
	private java.math.BigDecimal idformatoelio;
	private java.lang.String nomeformatoelio;
	private java.lang.String estensioneformatoelio;
	private java.lang.String notemodelloio;
	private java.lang.String codapplownerio;
	private java.lang.String codistapplownerio;
	private java.math.BigDecimal flgrestrapplownerio;
	private java.math.BigDecimal flginclannullatiio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String altricriteriio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.Integer flgabilinsout;
	private java.lang.Integer flgabilupdout;
	private java.lang.Integer flgabildelout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdmodelloio(){return idmodelloio;}
    public java.lang.String getNomemodelloio(){return nomemodelloio;}
    public java.lang.String getDescrizionemodelloio(){return descrizionemodelloio;}
    public java.lang.String getTyobjrelatedio(){return tyobjrelatedio;}
    public java.math.BigDecimal getIdtyobjrelatedio(){return idtyobjrelatedio;}
    public java.lang.String getNometyobjrelatedio(){return nometyobjrelatedio;}
    public java.lang.String getCiprovmodelloio(){return ciprovmodelloio;}
    public java.math.BigDecimal getIdformatoelio(){return idformatoelio;}
    public java.lang.String getNomeformatoelio(){return nomeformatoelio;}
    public java.lang.String getEstensioneformatoelio(){return estensioneformatoelio;}
    public java.lang.String getNotemodelloio(){return notemodelloio;}
    public java.lang.String getCodapplownerio(){return codapplownerio;}
    public java.lang.String getCodistapplownerio(){return codistapplownerio;}
    public java.math.BigDecimal getFlgrestrapplownerio(){return flgrestrapplownerio;}
    public java.math.BigDecimal getFlginclannullatiio(){return flginclannullatiio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getAltricriteriio(){return altricriteriio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.Integer getFlgabilinsout(){return flgabilinsout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.Integer getFlgabildelout(){return flgabildelout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdmodelloio(java.math.BigDecimal value){this.idmodelloio=value;}
    public void setNomemodelloio(java.lang.String value){this.nomemodelloio=value;}
    public void setDescrizionemodelloio(java.lang.String value){this.descrizionemodelloio=value;}
    public void setTyobjrelatedio(java.lang.String value){this.tyobjrelatedio=value;}
    public void setIdtyobjrelatedio(java.math.BigDecimal value){this.idtyobjrelatedio=value;}
    public void setNometyobjrelatedio(java.lang.String value){this.nometyobjrelatedio=value;}
    public void setCiprovmodelloio(java.lang.String value){this.ciprovmodelloio=value;}
    public void setIdformatoelio(java.math.BigDecimal value){this.idformatoelio=value;}
    public void setNomeformatoelio(java.lang.String value){this.nomeformatoelio=value;}
    public void setEstensioneformatoelio(java.lang.String value){this.estensioneformatoelio=value;}
    public void setNotemodelloio(java.lang.String value){this.notemodelloio=value;}
    public void setCodapplownerio(java.lang.String value){this.codapplownerio=value;}
    public void setCodistapplownerio(java.lang.String value){this.codistapplownerio=value;}
    public void setFlgrestrapplownerio(java.math.BigDecimal value){this.flgrestrapplownerio=value;}
    public void setFlginclannullatiio(java.math.BigDecimal value){this.flginclannullatiio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setAltricriteriio(java.lang.String value){this.altricriteriio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setFlgabilinsout(java.lang.Integer value){this.flgabilinsout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setFlgabildelout(java.lang.Integer value){this.flgabildelout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    