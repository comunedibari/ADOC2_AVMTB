package it.eng.document.function.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class AllegatiBean implements Serializable {

	private static final long serialVersionUID = 2995576840494796487L;
	
	@Attachment
	private List<File> fileAllegati;
	
	private Boolean flgSalvaOrdAllegati;

	private List<Boolean> isNull;
	private List<Boolean> isNewOrChanged;	
	private List<BigDecimal> idDocumento;
	private List<Integer> docType;
	private List<String> sezionePratica;	
	private List<String> descrizione;
	private List<String> displayFilename;
	private List<String> uriFile;	
	private List<FileInfoBean> info;
	private List<Boolean> flgProvEsterna;
	private List<Boolean> flgParteDispositivo;
	private List<Boolean> flgNoPubbl;
	private List<Boolean> flgPubblicaSeparato;
	private List<Boolean> flgDatiProtettiTipo1;
	private List<Boolean> flgDatiProtettiTipo2;
	private List<Boolean> flgDatiProtettiTipo3;
	private List<Boolean> flgDatiProtettiTipo4;
	private List<Boolean> flgDatiSensibili;	
	private List<Boolean> flgSostituisciVerPrec;	
	private List<Boolean> flgDaFirmare;	
	private List<String> idTask;
	private List<String> idTaskVer;
	private List<Boolean> flgOriginaleCartaceo;	
	private List<Boolean> flgCopiaSostitutiva;
	private List<Boolean> flgDaProtocollare;
	private List<String> idUdFrom;
	
	@Attachment
	private List<File> fileAllegatiOmissis;
	
	private List<BigDecimal> idDocumentoOmissis;
	private List<Boolean> isNullOmissis;
	private List<Boolean> isNewOrChangedOmissis;
	private List<String> displayFilenameOmissis;
	private List<FileInfoBean> infoOmissis;			
	private List<Boolean> flgSostituisciVerPrecOmissis;	
	
	public List<File> getFileAllegati() {
		return fileAllegati;
	}
	
	public void setFileAllegati(List<File> fileAllegati) {
		this.fileAllegati = fileAllegati;
	}
	
	public Boolean getFlgSalvaOrdAllegati() {
		return flgSalvaOrdAllegati;
	}

	public void setFlgSalvaOrdAllegati(Boolean flgSalvaOrdAllegati) {
		this.flgSalvaOrdAllegati = flgSalvaOrdAllegati;
	}

	public List<Boolean> getIsNull() {
		return isNull;
	}
	
	public void setIsNull(List<Boolean> isNull) {
		this.isNull = isNull;
	}
	
	public List<Boolean> getIsNewOrChanged() {
		return isNewOrChanged;
	}
	
	public void setIsNewOrChanged(List<Boolean> isNewOrChanged) {
		this.isNewOrChanged = isNewOrChanged;
	}
	
	public List<BigDecimal> getIdDocumento() {
		return idDocumento;
	}
	
	public void setIdDocumento(List<BigDecimal> idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	public List<Integer> getDocType() {
		return docType;
	}
	
	public void setDocType(List<Integer> docType) {
		this.docType = docType;
	}
	
	public List<String> getSezionePratica() {
		return sezionePratica;
	}

	public void setSezionePratica(List<String> sezionePratica) {
		this.sezionePratica = sezionePratica;
	}

	public List<String> getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(List<String> descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<String> getDisplayFilename() {
		return displayFilename;
	}
	
	public void setDisplayFilename(List<String> displayFilename) {
		this.displayFilename = displayFilename;
	}
	
	public List<String> getUriFile() {
		return uriFile;
	}
	
	public void setUriFile(List<String> uriFile) {
		this.uriFile = uriFile;
	}
	
	public List<FileInfoBean> getInfo() {
		return info;
	}
	
	public void setInfo(List<FileInfoBean> info) {
		this.info = info;
	}
	
	public List<Boolean> getFlgProvEsterna() {
		return flgProvEsterna;
	}

	public void setFlgProvEsterna(List<Boolean> flgProvEsterna) {
		this.flgProvEsterna = flgProvEsterna;
	}

	public List<Boolean> getFlgParteDispositivo() {
		return flgParteDispositivo;
	}
	
	public void setFlgParteDispositivo(List<Boolean> flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}
	
	public List<Boolean> getFlgNoPubbl() {
		return flgNoPubbl;
	}
	
	public void setFlgNoPubbl(List<Boolean> flgNoPubbl) {
		this.flgNoPubbl = flgNoPubbl;
	}
	
	public List<Boolean> getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}

	public void setFlgPubblicaSeparato(List<Boolean> flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}
	
	public List<Boolean> getFlgDatiProtettiTipo1() {
		return flgDatiProtettiTipo1;
	}

	public void setFlgDatiProtettiTipo1(List<Boolean> flgDatiProtettiTipo1) {
		this.flgDatiProtettiTipo1 = flgDatiProtettiTipo1;
	}

	public List<Boolean> getFlgDatiProtettiTipo2() {
		return flgDatiProtettiTipo2;
	}

	public void setFlgDatiProtettiTipo2(List<Boolean> flgDatiProtettiTipo2) {
		this.flgDatiProtettiTipo2 = flgDatiProtettiTipo2;
	}

	public List<Boolean> getFlgDatiProtettiTipo3() {
		return flgDatiProtettiTipo3;
	}

	public void setFlgDatiProtettiTipo3(List<Boolean> flgDatiProtettiTipo3) {
		this.flgDatiProtettiTipo3 = flgDatiProtettiTipo3;
	}

	public List<Boolean> getFlgDatiProtettiTipo4() {
		return flgDatiProtettiTipo4;
	}

	public void setFlgDatiProtettiTipo4(List<Boolean> flgDatiProtettiTipo4) {
		this.flgDatiProtettiTipo4 = flgDatiProtettiTipo4;
	}

	public List<Boolean> getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	
	public void setFlgDatiSensibili(List<Boolean> flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}
	
	public List<Boolean> getFlgSostituisciVerPrec() {
		return flgSostituisciVerPrec;
	}
	
	public void setFlgSostituisciVerPrec(List<Boolean> flgSostituisciVerPrec) {
		this.flgSostituisciVerPrec = flgSostituisciVerPrec;
	}
	
	public List<Boolean> getFlgDaFirmare() {
		return flgDaFirmare;
	}
	
	public void setFlgDaFirmare(List<Boolean> flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}
	
	public List<String> getIdTask() {
		return idTask;
	}
	
	public void setIdTask(List<String> idTask) {
		this.idTask = idTask;
	}
	
	public List<String> getIdTaskVer() {
		return idTaskVer;
	}

	public void setIdTaskVer(List<String> idTaskVer) {
		this.idTaskVer = idTaskVer;
	}

	public List<Boolean> getFlgOriginaleCartaceo() {
		return flgOriginaleCartaceo;
	}

	public void setFlgOriginaleCartaceo(List<Boolean> flgOriginaleCartaceo) {
		this.flgOriginaleCartaceo = flgOriginaleCartaceo;
	}

	public List<Boolean> getFlgCopiaSostitutiva() {
		return flgCopiaSostitutiva;
	}

	public void setFlgCopiaSostitutiva(List<Boolean> flgCopiaSostitutiva) {
		this.flgCopiaSostitutiva = flgCopiaSostitutiva;
	}

	public List<Boolean> getFlgDaProtocollare() {
		return flgDaProtocollare;
	}

	public void setFlgDaProtocollare(List<Boolean> flgDaProtocollare) {
		this.flgDaProtocollare = flgDaProtocollare;
	}
	
	public List<String> getIdUdFrom() {
		return idUdFrom;
	}
	
	public void setIdUdFrom(List<String> idUdFrom) {
		this.idUdFrom = idUdFrom;
	}

	public List<File> getFileAllegatiOmissis() {
		return fileAllegatiOmissis;
	}

	public void setFileAllegatiOmissis(List<File> fileAllegatiOmissis) {
		this.fileAllegatiOmissis = fileAllegatiOmissis;
	}
	
	public List<BigDecimal> getIdDocumentoOmissis() {
		return idDocumentoOmissis;
	}

	public void setIdDocumentoOmissis(List<BigDecimal> idDocumentoOmissis) {
		this.idDocumentoOmissis = idDocumentoOmissis;
	}

	public List<Boolean> getIsNullOmissis() {
		return isNullOmissis;
	}

	public void setIsNullOmissis(List<Boolean> isNullOmissis) {
		this.isNullOmissis = isNullOmissis;
	}

	public List<Boolean> getIsNewOrChangedOmissis() {
		return isNewOrChangedOmissis;
	}

	public void setIsNewOrChangedOmissis(List<Boolean> isNewOrChangedOmissis) {
		this.isNewOrChangedOmissis = isNewOrChangedOmissis;
	}

	public List<String> getDisplayFilenameOmissis() {
		return displayFilenameOmissis;
	}

	public void setDisplayFilenameOmissis(List<String> displayFilenameOmissis) {
		this.displayFilenameOmissis = displayFilenameOmissis;
	}

	public List<FileInfoBean> getInfoOmissis() {
		return infoOmissis;
	}

	public void setInfoOmissis(List<FileInfoBean> infoOmissis) {
		this.infoOmissis = infoOmissis;
	}

	public List<Boolean> getFlgSostituisciVerPrecOmissis() {
		return flgSostituisciVerPrecOmissis;
	}

	public void setFlgSostituisciVerPrecOmissis(List<Boolean> flgSostituisciVerPrecOmissis) {
		this.flgSostituisciVerPrecOmissis = flgSostituisciVerPrecOmissis;
	}
	
}
