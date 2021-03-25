package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class AllegatiOutBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idDoc;
	@NumeroColonna(numero = "2")
	private String idDocType;
	@NumeroColonna(numero = "3")
	private String nomeDocType;
	@NumeroColonna(numero = "4")
	private String descrizioneOggetto;
	@NumeroColonna(numero = "5")
	private String nroUltimaVersioneVisibile;
	@NumeroColonna(numero = "6")
	private String nroUltimaVersione;
	@NumeroColonna(numero = "7")
	private String displayFileName;
	@NumeroColonna(numero = "8")
	private String uri;
	@NumeroColonna(numero = "9")
	private BigDecimal dimensione;
	@NumeroColonna(numero = "10")
	private Flag flgFileFirmato;
	@NumeroColonna(numero = "11")
	private String mimetype;
	@NumeroColonna(numero = "12")
	private Flag flgConvertibilePdf;
	@NumeroColonna(numero = "13")
	private String impronta;
	@NumeroColonna(numero = "14")
	private String algoritmoImpronta;
	@NumeroColonna(numero = "15")
	private String encodingImpronta;
	@NumeroColonna(numero = "16")
	private String idAttachEmail;
	@NumeroColonna(numero = "17")
	private String nomeOriginale;
	@NumeroColonna(numero = "18")
	private String firmatari;	
	@NumeroColonna(numero = "19")
	private Flag flgParteDispositivo;	
	@NumeroColonna(numero = "20")
	private String mimetypeVerPreFirma;
	@NumeroColonna(numero = "21")
	private String uriVerPreFirma;
	@NumeroColonna(numero = "22")
	private String displayFilenameVerPreFirma;
	@NumeroColonna(numero = "23")
	private String flgConvertibilePdfVerPreFirma;
	@NumeroColonna(numero = "24")
	private String improntaVerPreFirma;
	@NumeroColonna(numero = "27")
	private String idTask;	
	@NumeroColonna(numero = "28")
	private Flag flgNoPubbl;		
	@NumeroColonna(numero = "29")
	private String idUd;
	@NumeroColonna(numero = "30")
	private Flag flgOriginaleCartaceo;		
	@NumeroColonna(numero = "31")
	private Flag flgCopiaSostitutiva;		
	@NumeroColonna(numero = "32")
	private String flgPubblicato;
	@NumeroColonna(numero = "33")
	private String idEmail;
	@NumeroColonna(numero = "34")
	private Flag flgTimbratoPostReg;	
	@NumeroColonna(numero = "35")
	private Flag flgDatiSensibili;
	// Vers. con omissis
	@NumeroColonna(numero = "36")
	private String idDocOmissis;
	@NumeroColonna(numero = "37")
	private String nroUltimaVersioneVisibileOmissis;
	@NumeroColonna(numero = "38")
	private String nroUltimaVersioneOmissis;
	@NumeroColonna(numero = "39")
	private String displayFileNameOmissis;
	@NumeroColonna(numero = "40")
	private String uriOmissis;
	@NumeroColonna(numero = "41")
	private BigDecimal dimensioneOmissis;
	@NumeroColonna(numero = "42")
	private Flag flgFileFirmatoOmissis;
	@NumeroColonna(numero = "43")
	private String mimetypeOmissis;
	@NumeroColonna(numero = "44")
	private Flag flgConvertibilePdfOmissis;
	@NumeroColonna(numero = "45")
	private String improntaOmissis;
	@NumeroColonna(numero = "46")
	private String algoritmoImprontaOmissis;
	@NumeroColonna(numero = "47")
	private String encodingImprontaOmissis;
	@NumeroColonna(numero = "48")
	private String nomeOriginaleOmissis;
	@NumeroColonna(numero = "49")
	private String firmatariOmissis;	
	@NumeroColonna(numero = "50")
	private String mimetypeVerPreFirmaOmissis;
	@NumeroColonna(numero = "51")
	private String uriVerPreFirmaOmissis;
	@NumeroColonna(numero = "52")
	private String displayFilenameVerPreFirmaOmissis;
	@NumeroColonna(numero = "53")
	private String flgConvertibilePdfVerPreFirmaOmissis;
	@NumeroColonna(numero = "54")
	private String improntaVerPreFirmaOmissis;
	@NumeroColonna(numero = "55")
	private Flag flgTimbratoPostRegOmissis;	
	@NumeroColonna(numero = "56")
	private String algoritmoImprontaVerPreFirmaOmissis;
	@NumeroColonna(numero = "57")
	private String encodingImprontaVerPreFirmaOmissis;	
	@NumeroColonna(numero = "58")
	private String estremiProtUd;	
	@NumeroColonna(numero = "59")
	private Flag flgUdSenzaFileImportata;	
	@NumeroColonna(numero = "60")
	private Flag flgNonModificabile;	
	@NumeroColonna(numero = "61")
	@TipoData(tipo=Tipo.DATA)
	private Date dataRicezione;
	@NumeroColonna(numero = "62")
	private String nroProtocollo;
	@NumeroColonna(numero = "63")
	private String annoProtocollo;
	@NumeroColonna(numero = "64")
	@TipoData(tipo=Tipo.DATA)
	private Date dataProtocollo;
	@NumeroColonna(numero = "65")
	private Flag flgPubblicaSeparato;
	@NumeroColonna(numero = "66")
	private Flag flgBustaCrittograficaAuriga;
	@NumeroColonna(numero = "67")
	private Flag flgFirmeNonValideBustaCrittografica;
	@NumeroColonna(numero = "68")
	private Flag flgMarcaTemporaleAuriga;
	@NumeroColonna(numero = "69")
	private Flag flgMarcaTemporaleNonValida;
	@NumeroColonna(numero = "70")
	@TipoData(tipo=Tipo.DATA)
	private Date dataOraMarcaTemporale;
	@NumeroColonna(numero = "71")
	private Flag flgBustaCrittograficaAurigaOmissis;
	@NumeroColonna(numero = "72")
	private Flag flgFirmeNonValideBustaCrittograficaOmissis;
	@NumeroColonna(numero = "73")
	private Flag flgMarcaTemporaleAurigaOmissis;
	@NumeroColonna(numero = "74")
	private Flag flgMarcaTemporaleNonValidaOmissis;
	@NumeroColonna(numero = "75")
	@TipoData(tipo=Tipo.DATA)
	private Date dataOraMarcaTemporaleOmissis;
	@NumeroColonna(numero = "76")
	private Flag flgFirmeExtraAuriga;
	@NumeroColonna(numero = "77")
	private Flag flgFirmeExtraAurigaOmissis;
	@NumeroColonna(numero = "78")
	private Flag flgPdfProtetto;
	@NumeroColonna(numero = "79")
	private Flag flgPdfProtettoOmissis;
	@NumeroColonna(numero = "80")
	private Flag flgProvEsterna;
	@NumeroColonna(numero = "81")
	private String esitoInvioACTASerieAttiIntegrali;
	@NumeroColonna(numero = "82")
	private String esitoInvioACTASeriePubbl;
	@NumeroColonna(numero = "83")
	private Flag flgParere;
	@NumeroColonna(numero = "84")
	private Flag flgDatiProtettiTipo1;
	@NumeroColonna(numero = "85")
	private Flag flgDatiProtettiTipo2;
	@NumeroColonna(numero = "86")
	private Flag flgDatiProtettiTipo3;
	@NumeroColonna(numero = "87")
	private Flag flgDatiProtettiTipo4;
	@NumeroColonna(numero = "88")
	private String ruoloUd;
	@NumeroColonna(numero="89")
	private String flgTipoProvXProt;
	
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	public String getDescrizioneOggetto() {
		return descrizioneOggetto;
	}
	public void setDescrizioneOggetto(String descrizioneOggetto) {
		this.descrizioneOggetto = descrizioneOggetto;
	}
	public String getNroUltimaVersioneVisibile() {
		return nroUltimaVersioneVisibile;
	}
	public void setNroUltimaVersioneVisibile(String nroUltimaVersioneVisibile) {
		this.nroUltimaVersioneVisibile = nroUltimaVersioneVisibile;
	}
	public String getNroUltimaVersione() {
		return nroUltimaVersione;
	}
	public void setNroUltimaVersione(String nroUltimaVersione) {
		this.nroUltimaVersione = nroUltimaVersione;
	}
	public String getDisplayFileName() {
		return displayFileName;
	}
	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public BigDecimal getDimensione() {
		return dimensione;
	}
	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}
	public Flag getFlgFileFirmato() {
		return flgFileFirmato;
	}
	public void setFlgFileFirmato(Flag flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setFlgConvertibilePdf(Flag flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
	}
	public Flag getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}
	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}
	public String getEncodingImpronta() {
		return encodingImpronta;
	}
	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}
	public void setIdAttachEmail(String idAttachEmail) {
		this.idAttachEmail = idAttachEmail;
	}
	public String getIdAttachEmail() {
		return idAttachEmail;
	}
	public String getNomeOriginale() {
		return nomeOriginale;
	}
	public void setNomeOriginale(String nomeOriginale) {
		this.nomeOriginale = nomeOriginale;
	}
	public String getFirmatari() {
		return firmatari;
	}
	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}
	public Flag getFlgParteDispositivo() {
		return flgParteDispositivo;
	}
	public void setFlgParteDispositivo(Flag flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}
	public String getMimetypeVerPreFirma() {
		return mimetypeVerPreFirma;
	}
	public void setMimetypeVerPreFirma(String mimetypeVerPreFirma) {
		this.mimetypeVerPreFirma = mimetypeVerPreFirma;
	}
	public String getUriVerPreFirma() {
		return uriVerPreFirma;
	}
	public void setUriVerPreFirma(String uriVerPreFirma) {
		this.uriVerPreFirma = uriVerPreFirma;
	}
	public String getDisplayFilenameVerPreFirma() {
		return displayFilenameVerPreFirma;
	}
	public void setDisplayFilenameVerPreFirma(String displayFilenameVerPreFirma) {
		this.displayFilenameVerPreFirma = displayFilenameVerPreFirma;
	}
	public String getFlgConvertibilePdfVerPreFirma() {
		return flgConvertibilePdfVerPreFirma;
	}
	public void setFlgConvertibilePdfVerPreFirma(
			String flgConvertibilePdfVerPreFirma) {
		this.flgConvertibilePdfVerPreFirma = flgConvertibilePdfVerPreFirma;
	}
	public String getImprontaVerPreFirma() {
		return improntaVerPreFirma;
	}
	public void setImprontaVerPreFirma(String improntaVerPreFirma) {
		this.improntaVerPreFirma = improntaVerPreFirma;
	}
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public Flag getFlgNoPubbl() {
		return flgNoPubbl;
	}
	public void setFlgNoPubbl(Flag flgNoPubbl) {
		this.flgNoPubbl = flgNoPubbl;
	}	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public Flag getFlgOriginaleCartaceo() {
		return flgOriginaleCartaceo;
	}
	public void setFlgOriginaleCartaceo(Flag flgOriginaleCartaceo) {
		this.flgOriginaleCartaceo = flgOriginaleCartaceo;
	}
	public Flag getFlgCopiaSostitutiva() {
		return flgCopiaSostitutiva;
	}
	public void setFlgCopiaSostitutiva(Flag flgCopiaSostitutiva) {
		this.flgCopiaSostitutiva = flgCopiaSostitutiva;
	}
	public String getFlgPubblicato() {
		return flgPubblicato;
	}
	public void setFlgPubblicato(String flgPubblicato) {
		this.flgPubblicato = flgPubblicato;
	}
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public Flag getFlgTimbratoPostReg() {
		return flgTimbratoPostReg;
	}
	public void setFlgTimbratoPostReg(Flag flgTimbratoPostReg) {
		this.flgTimbratoPostReg = flgTimbratoPostReg;
	}
	public Flag getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(Flag flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}	
	public String getIdDocOmissis() {
		return idDocOmissis;
	}
	public void setIdDocOmissis(String idDocOmissis) {
		this.idDocOmissis = idDocOmissis;
	}
	public String getNroUltimaVersioneVisibileOmissis() {
		return nroUltimaVersioneVisibileOmissis;
	}
	public void setNroUltimaVersioneVisibileOmissis(String nroUltimaVersioneVisibileOmissis) {
		this.nroUltimaVersioneVisibileOmissis = nroUltimaVersioneVisibileOmissis;
	}
	public String getNroUltimaVersioneOmissis() {
		return nroUltimaVersioneOmissis;
	}
	public void setNroUltimaVersioneOmissis(String nroUltimaVersioneOmissis) {
		this.nroUltimaVersioneOmissis = nroUltimaVersioneOmissis;
	}
	public String getDisplayFileNameOmissis() {
		return displayFileNameOmissis;
	}
	public void setDisplayFileNameOmissis(String displayFileNameOmissis) {
		this.displayFileNameOmissis = displayFileNameOmissis;
	}
	public String getUriOmissis() {
		return uriOmissis;
	}
	public void setUriOmissis(String uriOmissis) {
		this.uriOmissis = uriOmissis;
	}
	public BigDecimal getDimensioneOmissis() {
		return dimensioneOmissis;
	}
	public void setDimensioneOmissis(BigDecimal dimensioneOmissis) {
		this.dimensioneOmissis = dimensioneOmissis;
	}
	public Flag getFlgFileFirmatoOmissis() {
		return flgFileFirmatoOmissis;
	}
	public void setFlgFileFirmatoOmissis(Flag flgFileFirmatoOmissis) {
		this.flgFileFirmatoOmissis = flgFileFirmatoOmissis;
	}
	public String getMimetypeOmissis() {
		return mimetypeOmissis;
	}
	public void setMimetypeOmissis(String mimetypeOmissis) {
		this.mimetypeOmissis = mimetypeOmissis;
	}
	public Flag getFlgConvertibilePdfOmissis() {
		return flgConvertibilePdfOmissis;
	}
	public void setFlgConvertibilePdfOmissis(Flag flgConvertibilePdfOmissis) {
		this.flgConvertibilePdfOmissis = flgConvertibilePdfOmissis;
	}
	public String getImprontaOmissis() {
		return improntaOmissis;
	}
	public void setImprontaOmissis(String improntaOmissis) {
		this.improntaOmissis = improntaOmissis;
	}
	public String getAlgoritmoImprontaOmissis() {
		return algoritmoImprontaOmissis;
	}
	public void setAlgoritmoImprontaOmissis(String algoritmoImprontaOmissis) {
		this.algoritmoImprontaOmissis = algoritmoImprontaOmissis;
	}
	public String getEncodingImprontaOmissis() {
		return encodingImprontaOmissis;
	}
	public void setEncodingImprontaOmissis(String encodingImprontaOmissis) {
		this.encodingImprontaOmissis = encodingImprontaOmissis;
	}
	public String getNomeOriginaleOmissis() {
		return nomeOriginaleOmissis;
	}
	public void setNomeOriginaleOmissis(String nomeOriginaleOmissis) {
		this.nomeOriginaleOmissis = nomeOriginaleOmissis;
	}
	public String getFirmatariOmissis() {
		return firmatariOmissis;
	}
	public void setFirmatariOmissis(String firmatariOmissis) {
		this.firmatariOmissis = firmatariOmissis;
	}
	public String getMimetypeVerPreFirmaOmissis() {
		return mimetypeVerPreFirmaOmissis;
	}
	public void setMimetypeVerPreFirmaOmissis(String mimetypeVerPreFirmaOmissis) {
		this.mimetypeVerPreFirmaOmissis = mimetypeVerPreFirmaOmissis;
	}
	public String getUriVerPreFirmaOmissis() {
		return uriVerPreFirmaOmissis;
	}
	public void setUriVerPreFirmaOmissis(String uriVerPreFirmaOmissis) {
		this.uriVerPreFirmaOmissis = uriVerPreFirmaOmissis;
	}
	public String getDisplayFilenameVerPreFirmaOmissis() {
		return displayFilenameVerPreFirmaOmissis;
	}
	public void setDisplayFilenameVerPreFirmaOmissis(String displayFilenameVerPreFirmaOmissis) {
		this.displayFilenameVerPreFirmaOmissis = displayFilenameVerPreFirmaOmissis;
	}
	public String getFlgConvertibilePdfVerPreFirmaOmissis() {
		return flgConvertibilePdfVerPreFirmaOmissis;
	}
	public void setFlgConvertibilePdfVerPreFirmaOmissis(String flgConvertibilePdfVerPreFirmaOmissis) {
		this.flgConvertibilePdfVerPreFirmaOmissis = flgConvertibilePdfVerPreFirmaOmissis;
	}
	public String getImprontaVerPreFirmaOmissis() {
		return improntaVerPreFirmaOmissis;
	}
	public void setImprontaVerPreFirmaOmissis(String improntaVerPreFirmaOmissis) {
		this.improntaVerPreFirmaOmissis = improntaVerPreFirmaOmissis;
	}
	public Flag getFlgTimbratoPostRegOmissis() {
		return flgTimbratoPostRegOmissis;
	}
	public void setFlgTimbratoPostRegOmissis(Flag flgTimbratoPostRegOmissis) {
		this.flgTimbratoPostRegOmissis = flgTimbratoPostRegOmissis;
	}
	public String getAlgoritmoImprontaVerPreFirmaOmissis() {
		return algoritmoImprontaVerPreFirmaOmissis;
	}
	public void setAlgoritmoImprontaVerPreFirmaOmissis(String algoritmoImprontaVerPreFirmaOmissis) {
		this.algoritmoImprontaVerPreFirmaOmissis = algoritmoImprontaVerPreFirmaOmissis;
	}
	public String getEncodingImprontaVerPreFirmaOmissis() {
		return encodingImprontaVerPreFirmaOmissis;
	}
	public void setEncodingImprontaVerPreFirmaOmissis(String encodingImprontaVerPreFirmaOmissis) {
		this.encodingImprontaVerPreFirmaOmissis = encodingImprontaVerPreFirmaOmissis;
	}
	public String getEstremiProtUd() {
		return estremiProtUd;
	}
	public void setEstremiProtUd(String estremiProtUd) {
		this.estremiProtUd = estremiProtUd;
	}
	public Flag getFlgUdSenzaFileImportata() {
		return flgUdSenzaFileImportata;
	}
	public void setFlgUdSenzaFileImportata(Flag flgUdSenzaFileImportata) {
		this.flgUdSenzaFileImportata = flgUdSenzaFileImportata;
	}
	public Flag getFlgNonModificabile() {
		return flgNonModificabile;
	}
	public void setFlgNonModificabile(Flag flgNonModificabile) {
		this.flgNonModificabile = flgNonModificabile;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getNroProtocollo() {
		return nroProtocollo;
	}
	public void setNroProtocollo(String nroProtocollo) {
		this.nroProtocollo = nroProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public Flag getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}
	public void setFlgPubblicaSeparato(Flag flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}
	public Flag getFlgBustaCrittograficaAuriga() {
		return flgBustaCrittograficaAuriga;
	}
	public void setFlgBustaCrittograficaAuriga(Flag flgBustaCrittograficaAuriga) {
		this.flgBustaCrittograficaAuriga = flgBustaCrittograficaAuriga;
	}
	public Flag getFlgFirmeNonValideBustaCrittografica() {
		return flgFirmeNonValideBustaCrittografica;
	}
	public void setFlgFirmeNonValideBustaCrittografica(Flag flgFirmeNonValideBustaCrittografica) {
		this.flgFirmeNonValideBustaCrittografica = flgFirmeNonValideBustaCrittografica;
	}
	public Flag getFlgMarcaTemporaleAuriga() {
		return flgMarcaTemporaleAuriga;
	}
	public void setFlgMarcaTemporaleAuriga(Flag flgMarcaTemporaleAuriga) {
		this.flgMarcaTemporaleAuriga = flgMarcaTemporaleAuriga;
	}
	public Flag getFlgMarcaTemporaleNonValida() {
		return flgMarcaTemporaleNonValida;
	}
	public void setFlgMarcaTemporaleNonValida(Flag flgMarcaTemporaleNonValida) {
		this.flgMarcaTemporaleNonValida = flgMarcaTemporaleNonValida;
	}
	public Date getDataOraMarcaTemporale() {
		return dataOraMarcaTemporale;
	}
	public void setDataOraMarcaTemporale(Date dataOraMarcaTemporale) {
		this.dataOraMarcaTemporale = dataOraMarcaTemporale;
	}
	public Flag getFlgBustaCrittograficaAurigaOmissis() {
		return flgBustaCrittograficaAurigaOmissis;
	}
	public void setFlgBustaCrittograficaAurigaOmissis(Flag flgBustaCrittograficaAurigaOmissis) {
		this.flgBustaCrittograficaAurigaOmissis = flgBustaCrittograficaAurigaOmissis;
	}
	public Flag getFlgFirmeNonValideBustaCrittograficaOmissis() {
		return flgFirmeNonValideBustaCrittograficaOmissis;
	}
	public void setFlgFirmeNonValideBustaCrittograficaOmissis(Flag flgFirmeNonValideBustaCrittograficaOmissis) {
		this.flgFirmeNonValideBustaCrittograficaOmissis = flgFirmeNonValideBustaCrittograficaOmissis;
	}
	public Flag getFlgMarcaTemporaleAurigaOmissis() {
		return flgMarcaTemporaleAurigaOmissis;
	}
	public void setFlgMarcaTemporaleAurigaOmissis(Flag flgMarcaTemporaleAurigaOmissis) {
		this.flgMarcaTemporaleAurigaOmissis = flgMarcaTemporaleAurigaOmissis;
	}
	public Flag getFlgMarcaTemporaleNonValidaOmissis() {
		return flgMarcaTemporaleNonValidaOmissis;
	}
	public void setFlgMarcaTemporaleNonValidaOmissis(Flag flgMarcaTemporaleNonValidaOmissis) {
		this.flgMarcaTemporaleNonValidaOmissis = flgMarcaTemporaleNonValidaOmissis;
	}
	public Date getDataOraMarcaTemporaleOmissis() {
		return dataOraMarcaTemporaleOmissis;
	}
	public void setDataOraMarcaTemporaleOmissis(Date dataOraMarcaTemporaleOmissis) {
		this.dataOraMarcaTemporaleOmissis = dataOraMarcaTemporaleOmissis;
	}
	public Flag getFlgFirmeExtraAuriga() {
		return flgFirmeExtraAuriga;
	}
	public void setFlgFirmeExtraAuriga(Flag flgFirmeExtraAuriga) {
		this.flgFirmeExtraAuriga = flgFirmeExtraAuriga;
	}
	public Flag getFlgFirmeExtraAurigaOmissis() {
		return flgFirmeExtraAurigaOmissis;
	}
	public void setFlgFirmeExtraAurigaOmissis(Flag flgFirmeExtraAurigaOmissis) {
		this.flgFirmeExtraAurigaOmissis = flgFirmeExtraAurigaOmissis;
	}
	public Flag getFlgPdfProtetto() {
		return flgPdfProtetto;
	}
	public void setFlgPdfProtetto(Flag flgPdfProtetto) {
		this.flgPdfProtetto = flgPdfProtetto;
	}
	public Flag getFlgPdfProtettoOmissis() {
		return flgPdfProtettoOmissis;
	}
	public void setFlgPdfProtettoOmissis(Flag flgPdfProtettoOmissis) {
		this.flgPdfProtettoOmissis = flgPdfProtettoOmissis;
	}
	public Flag getFlgProvEsterna() {
		return flgProvEsterna;
	}
	public void setFlgProvEsterna(Flag flgProvEsterna) {
		this.flgProvEsterna = flgProvEsterna;
	}
	public String getEsitoInvioACTASerieAttiIntegrali() {
		return esitoInvioACTASerieAttiIntegrali;
	}
	public void setEsitoInvioACTASerieAttiIntegrali(String esitoInvioACTASerieAttiIntegrali) {
		this.esitoInvioACTASerieAttiIntegrali = esitoInvioACTASerieAttiIntegrali;
	}
	public String getEsitoInvioACTASeriePubbl() {
		return esitoInvioACTASeriePubbl;
	}
	public void setEsitoInvioACTASeriePubbl(String esitoInvioACTASeriePubbl) {
		this.esitoInvioACTASeriePubbl = esitoInvioACTASeriePubbl;
	}
	public Flag getFlgParere() {
		return flgParere;
	}
	public void setFlgParere(Flag flgParere) {
		this.flgParere = flgParere;
	}
	public Flag getFlgDatiProtettiTipo1() {
		return flgDatiProtettiTipo1;
	}
	public void setFlgDatiProtettiTipo1(Flag flgDatiProtettiTipo1) {
		this.flgDatiProtettiTipo1 = flgDatiProtettiTipo1;
	}
	public Flag getFlgDatiProtettiTipo2() {
		return flgDatiProtettiTipo2;
	}
	public void setFlgDatiProtettiTipo2(Flag flgDatiProtettiTipo2) {
		this.flgDatiProtettiTipo2 = flgDatiProtettiTipo2;
	}
	public Flag getFlgDatiProtettiTipo3() {
		return flgDatiProtettiTipo3;
	}
	public void setFlgDatiProtettiTipo3(Flag flgDatiProtettiTipo3) {
		this.flgDatiProtettiTipo3 = flgDatiProtettiTipo3;
	}
	public Flag getFlgDatiProtettiTipo4() {
		return flgDatiProtettiTipo4;
	}
	public void setFlgDatiProtettiTipo4(Flag flgDatiProtettiTipo4) {
		this.flgDatiProtettiTipo4 = flgDatiProtettiTipo4;
	}
	public String getRuoloUd() {
		return ruoloUd;
	}
	public void setRuoloUd(String ruoloUd) {
		this.ruoloUd = ruoloUd;
	}
	public String getFlgTipoProvXProt() {
		return flgTipoProvXProt;
	}
	public void setFlgTipoProvXProt(String flgTipoProvXProt) {
		this.flgTipoProvXProt = flgTipoProvXProt;
	}
	
}