package it.eng.utility.email.operation.impl.signeroperation;

import java.io.File;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean contenente tutti i dati di controllo della firma di un singolo attachments
 * 
 * @author michele
 * 
 */
@XmlRootElement
public class SignerAttachmentsBean {

	/**
	 * Valori della firma
	 */
	// @XmlJavaTypeAdapter(MapAdapter.class)
	private HashMap<SignerResultType, ValidationAttachmentsInfos> resultoperation;

	/**
	 * Indica se il file è firmato
	 */
	private Boolean issigner;

	/**
	 * Indica se il file firmato è valido (controlla marca, CRL e CA)
	 */
	private Boolean issignervalid;

	/**
	 * Nome del file
	 */
	private String filename;

	/**
	 * Nome del messageid di riferimento
	 */
	private String messageid;

	/**
	 * mimetype dell'allegato
	 */
	private String mimetype;

	/**
	 * dimensione dell'allegato
	 */
	private Long size;

	/**
	 * file dell'allegato
	 */
	private File file;

	/**
	 * Hash
	 */
	private String encodedHash;

	/**
	 * Encoding hash
	 */

	private String encoding;

	/**
	 * Algoritmo hash
	 */

	private String algoritmo;

	public Boolean getIssigner() {
		return issigner;
	}

	public void setIssigner(Boolean issigner) {
		this.issigner = issigner;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public HashMap<SignerResultType, ValidationAttachmentsInfos> getResultoperation() {
		return resultoperation;
	}

	public void setResultoperation(HashMap<SignerResultType, ValidationAttachmentsInfos> resultoperation) {
		this.resultoperation = resultoperation;
	}

	public Boolean getIssignervalid() {
		return issignervalid;
	}

	public void setIssignervalid(Boolean issignervalid) {
		this.issignervalid = issignervalid;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getEncodedHash() {
		return encodedHash;
	}

	public void setEncodedHash(String encodedHash) {
		this.encodedHash = encodedHash;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

}