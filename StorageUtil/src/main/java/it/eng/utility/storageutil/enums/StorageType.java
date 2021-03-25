package it.eng.utility.storageutil.enums;

import java.io.Serializable;

/**
 * Enumeration che indica il tipo di storage utilizzato per salvare i file (FS, Centera)
 * 
 * @author Mattia Zanin
 * 
 */
public enum StorageType implements Serializable {

	/**
	 * Storage su FileSystem
	 * 
	 */
	FS("FS"),

	/**
	 * Storage su FileSystem versione zip
	 * 
	 */
	FSZIP("FSZIP"),

	/**
	 * Storage su FileSystem versione czip
	 * 
	 */
	FSCZIP("FSCZIP"),
	
	/**
	 * Storage su FileSystem versione 7Z
	 * 
	 */
	FS7Z("FS7Z"),

	/**
	 * Storage su Centera
	 * 
	 */
	CENTERA("CENTERA"),

	/**
	 * Storage su SharePoint
	 * 
	 */
	SHAREPOINT("SHAREPOINT"),

	/**
	 * Storage su Blob
	 */
	BLOB("BLOB"),

	/**
	 * Storage su Blob
	 */
	BFILE("BFILE"),

	/**
	 * Storage su ORACLEWCC
	 */
	ORACLEWCC("ORACLEWCC"),

	/**
	 * Storage su SFTP
	 */
	SFTP("SFTP"),

	/**
	 * Storage su FTP - FTPS
	 */
	FTP("FTP"),
	
	/**
	 * Storage su DOCAREA
	 */
	DOCAREA("DOCAREA")
	;

	private final String tipo;

	StorageType(String tipo) {
		this.tipo = tipo;
	}

	public String type() {
		return this.tipo;
	}

}