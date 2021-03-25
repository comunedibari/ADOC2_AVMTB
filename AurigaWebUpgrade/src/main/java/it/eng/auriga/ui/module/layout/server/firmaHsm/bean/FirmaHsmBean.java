package it.eng.auriga.ui.module.layout.server.firmaHsm.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.RettangoloFirmaPadesBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FirmaHsmBean implements Serializable {

	private List<FileDaFirmare> listaFileDaFirmare;
	private List<FileDaFirmare> fileFirmati;
	public FileDaFirmare fileDaFirmare = new FileDaFirmare();
	private String fileDaMarcare;
	private boolean skipControlloFirmaBusta;
	private String tipofirma;
	private String hsmType;
	
	// Lista certificati
	private List<CertificatoHsmBean> listaCertificati;

	// Dati autenticazione servizio firma remota e richiesta codice otp
	private String username;
	private String usernameDelegante;
	private String password;
	private String codiceOtp;
	private String certId;
	private String certSerialNumber;
	private String potereDiFirma;
	private String typeOtp;
	private Boolean parametriHSMFromGui;
	private String providerHsmFromPreference;
	private Boolean useExternalWS = false;

	private boolean esitoOk;
	private String errorMessage;

	// Parametri per la gestione del rettangolo di firma nella firma pades
	private RettangoloFirmaPadesBean rettangoloFirmaPades;

	public List<FileDaFirmare> getListaFileDaFirmare() {
		return listaFileDaFirmare;
	}

	public void setListaFileDaFirmare(List<FileDaFirmare> listaFileDaFirmare) {
		this.listaFileDaFirmare = listaFileDaFirmare;
	}

	public List<FileDaFirmare> getFileFirmati() {
		return fileFirmati;
	}

	public void setFileFirmati(List<FileDaFirmare> fileFirmati) {
		this.fileFirmati = fileFirmati;
	}

	

	public String getFileDaMarcare() {
		return fileDaMarcare;
	}

	public void setFileDaMarcare(String fileDaMarcare) {
		this.fileDaMarcare = fileDaMarcare;
	}

	public boolean isSkipControlloFirmaBusta() {
		return skipControlloFirmaBusta;
	}

	public void setSkipControlloFirmaBusta(boolean skipControlloFirmaBusta) {
		this.skipControlloFirmaBusta = skipControlloFirmaBusta;
	}

	public String getTipofirma() {
		return tipofirma;
	}

	public void setTipofirma(String tipofirma) {
		this.tipofirma = tipofirma;
	}

	public String getHsmType() {
		return hsmType;
	}

	public void setHsmType(String hsmType) {
		this.hsmType = hsmType;
	}

	public List<CertificatoHsmBean> getListaCertificati() {
		return listaCertificati;
	}

	public void setListaCertificati(List<CertificatoHsmBean> listaCertificati) {
		this.listaCertificati = listaCertificati;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsernameDelegante() {
		return usernameDelegante;
	}

	public void setUsernameDelegante(String usernameDelegante) {
		this.usernameDelegante = usernameDelegante;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCodiceOtp() {
		return codiceOtp;
	}

	public void setCodiceOtp(String codiceOtp) {
		this.codiceOtp = codiceOtp;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}
	
	public String getCertSerialNumber() {
		return certSerialNumber;
	}
	
	public void setCertSerialNumber(String certSerialNumber) {
		this.certSerialNumber = certSerialNumber;
	}

	public String getPotereDiFirma() {
		return potereDiFirma;
	}

	public void setPotereDiFirma(String potereDiFirma) {
		this.potereDiFirma = potereDiFirma;
	}

	public String getTypeOtp() {
		return typeOtp;
	}

	public void setTypeOtp(String typeOtp) {
		this.typeOtp = typeOtp;
	}
	
	
	public Boolean getParametriHSMFromGui() {
		return parametriHSMFromGui;
	}

	
	public void setParametriHSMFromGui(Boolean parametriHSMFromGui) {
		this.parametriHSMFromGui = parametriHSMFromGui;
	}

	public FileDaFirmare getFileDaFirmare() {
		return fileDaFirmare;
	}

	public void setFileDaFirmare(FileDaFirmare fileDaFirmare) {
		this.fileDaFirmare = fileDaFirmare;
	}

	public RettangoloFirmaPadesBean getRettangoloFirmaPades() {
		return rettangoloFirmaPades;
	}

	public void setRettangoloFirmaPades(RettangoloFirmaPadesBean rettangoloFirmaPades) {
		this.rettangoloFirmaPades = rettangoloFirmaPades;
	}
	
	public Boolean getUseExternalWS() {
		return useExternalWS;
	}

	public void setUseExternalWS(Boolean useExternalWS) {
		this.useExternalWS = useExternalWS;
	}

	public boolean isEsitoOk() {
		return esitoOk;
	}

	public void setEsitoOk(boolean esitoOk) {
		this.esitoOk = esitoOk;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getProviderHsmFromPreference() {
		return providerHsmFromPreference;
	}

	public void setProviderHsmFromPreference(String providerHsmFromPreference) {
		this.providerHsmFromPreference = providerHsmFromPreference;
	}

	public class FileDaFirmare {

		private String uri;
		private String nomeFile;
		private String idFile;
		private MimeTypeFirmaBean infoFile;
		private TipoFirmaHsm tipoFirmaHsm;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getNomeFile() {
			return nomeFile;
		}

		public void setNomeFile(String nomeFile) {
			this.nomeFile = nomeFile;
		}

		public String getIdFile() {
			return idFile;
		}

		public void setIdFile(String idFile) {
			this.idFile = idFile;
		}

		public MimeTypeFirmaBean getInfoFile() {
			return infoFile;
		}

		public void setInfoFile(MimeTypeFirmaBean infoFile) {
			this.infoFile = infoFile;
		}

		public TipoFirmaHsm getTipoFirmaHsm() {
			return tipoFirmaHsm;
		}

		public void setTipoFirma(TipoFirmaHsm tipoFirmaHsm) {
			this.tipoFirmaHsm = tipoFirmaHsm;
		}

	}

	public enum TipoFirmaHsm {
		FILE_PADES("FilePades"), HASH_PADES("HashPades"), FILE_CADES_CONGIUNTA("FileCadesCongiunta"), HASH_CADES_CONGIUNTA(
				"HashCadesCongiunta"), FILE_CADES_VERTICALE("FileCadesVerticale"), HASH_CADES_VERTICALE("HashCadesVerticale");

		private String descrizione;

		TipoFirmaHsm(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getDescrizione() {
			return descrizione;
		}
	}

}
