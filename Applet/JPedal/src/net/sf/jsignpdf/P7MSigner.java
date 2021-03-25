package net.sf.jsignpdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CRL;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;
import org.bouncycastle.x509.X509CRLStoreSelector;
import org.bouncycastle.x509.X509Store;

/**
 * Implementa i controlli su firme di tipo P7M.
 * Il contenuto di un file � riconosciuto se implementa le specifiche PKCS #7
 * @author Stefano Zennaro
 *
 */
public class P7MSigner extends AbstractSigner {
	
	/**
	 * Contenuto CMS
	 */
	//protected CMSSignedDataParser cmsSignedData = null;
	//protected File file; 

	protected boolean isSignedType(CMSSignedDataParser signedData) throws CMSException {
		//Resetto il signer
		reset();
//		cmsSignedData = null;
		
		boolean signed = false;
		try {
			signedData.getSignedContent().drain();
		}catch(Exception e) {
			throw new CMSException("Errore firma",e);
		}
		SignerInformationStore signersStore = signedData.getSignerInfos();
		signersStore = signedData.getSignerInfos();
		
		Collection<?> signers = signersStore.getSigners();
		if (signers==null || signers.isEmpty()) {
			signed = false;
		} else {
			//Controllo se l'algoritmo � di tipo SHA1
			for (Object signer:signers) {
				if(signer instanceof SignerInformation){
					if (!CMSSignedDataGenerator.DIGEST_SHA1.equals(((SignerInformation)signer).getDigestAlgOID())) {
						signed = false;
						break;
					}
					signed = true;
				}else{
					signer = false;
				}
			}
		}
		
		return signed;
	}
	
	
	/**
	 * Restituisce true se il contenuto del file � di tipo CMS e
	 * l'algoritmo di digest � di tipo SHA1
	 */
	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		this.file = file;
		File tmp = null;
		try {
			stream = FileUtils.openInputStream(file);	
			CMSSignedDataParser cmsSignedData = new CMSSignedDataParser(stream);
			signed = isSignedType(cmsSignedData);
			stream.close();
		}catch(Exception e) {
			try {
				//Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while(iterator.hasNext()){				
						String line = iterator.nextLine();
						if(!StringUtils.containsIgnoreCase(line,"-----END")){
							writer.write(line);
							writer.newLine();
							writer.flush();							
						}else{
							writer.close();							
						}
					}
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					CMSSignedDataParser parser = new CMSSignedDataParser(streambase64);
					signed = isSignedType(parser);
					setBase64(true);
					
				}else{
						
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					CMSSignedDataParser parser = new CMSSignedDataParser(streambase64);
					signed = isSignedType(parser);
					setBase64(true);
				}
			}catch(Exception er) {
				//er.printStackTrace();
				signed = false;
					try {
						if(stream!=null){
							stream.close();
						}
					} catch (IOException e1) {}
			}finally{
				if(tmp!=null){
				//	FileUtil.deleteFile(tmp);
				}
				
			}	
		} finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
		}
		return signed;
	}
	
//	@Deprecated
//	public boolean isSignedType(byte[] content) {
//		boolean signed = false;
//		try {
//			CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(content);
//			signed = isSignedType(cmsSignedData);
//		} catch (CMSException e) {
//			signed = false;
//		}
//		return signed;
//	}

	
	public TimeStampToken[] getTimeStampTokens() {
		//Ritorna sempre null in quanto il file p7m non ha un TimeStampToken al suo interno.
		return null;
	}

	
	public SignerType getFormat() {
		return SignerType.P7M;
	}
	

	/**
	 * Ritorna il contenuto non firmato da una struttura di tipo CMSSigned
	 */
	public static InputStream getCMSSignedDataUnsignedContent(CMSSignedDataParser sd) {
		return sd.getSignedContent().getContentStream();
	}
	
	protected InputStream getExtractedContent()throws IOException, CMSException {
		CMSSignedDataParser cmsSignedData = null;
		if (isBase64()) {
			File tmp = null;
			//Controllo se il file comincia per -----BEGIN
			LineIterator iterator = FileUtils.lineIterator(file);
			String firstline = iterator.nextLine();
			if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
				tmp = File.createTempFile("tmp", ".tmp");
				BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
				while(iterator.hasNext()){				
					String line = iterator.nextLine();
					if(!StringUtils.containsIgnoreCase(line,"-----END")){
						writer.write(line);
						writer.newLine();
						writer.flush();							
					}else{
						writer.close();							
					}
				}
				Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
				cmsSignedData= new CMSSignedDataParser(streambase64);
				//FileUtil.deleteFile(tmp);
			}else{
				Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
				cmsSignedData= new CMSSignedDataParser(streambase64);
			}
		} else {
			cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
		}
		return cmsSignedData.getSignedContent().getContentStream();
	}
	
	public InputStream getUnsignedContent() {
		try {
			File detachedFile = getDetachedFile();
			
			//Si tratta della firma di un file detached? 
			if (detachedFile != null)
				return FileUtils.openInputStream(detachedFile);
			
			else{
				return getExtractedContent();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm) {
		InputStream unsignedContent = this.getUnsignedContent();
		byte[] buff = new byte[Byte.SIZE*512];
		int length = -1;
		digestAlgorithm.reset();
		try {
			while ((length = unsignedContent.read(buff))!=-1) {
				digestAlgorithm.update(buff, 0, length);
			}
			return digestAlgorithm.digest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (unsignedContent!=null)
				try {
					unsignedContent.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}
	
	/**
	 * Recupera la lista di firme da una struttura di tipo CMS 
	 * settando al contempo il contenuto a cui le firme si riferiscono.
	 * @param signedData contenuto di tipo CMS
	 * @param detachedContent contenuto detached
	 * @return la lista di firme 
	 * @throws CMSException 
	 */
//	public static final List<ISignature> getISigneturesFromCMSSignedData(CMSSignedDataParser signedData, List<File> detachedContent) throws CMSException {
//		
//		List<ISignature> result = new ArrayList<ISignature>();
//		
//		Collection<?> certificates = null;
//		try {
//			Store storecertificate = signedData.getCertificates();
//			certificates = (Collection<?>)storecertificate.getMatches(null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Collection<?> signers = (Collection<?>)signedData.getSignerInfos().getSigners();
//		for (Object signer: signers) {
//			if(signer instanceof SignerInformation){
//				//SignerId signerID  = ((SignerInformation)signer).getSID();
//				ISignature signature = getISignatureFromSignerInformationAndCertificates((SignerInformation)signer, certificates, detachedContent);
//				if (signature!=null){
//					result.add(signature);
//				}
//			}else{
//				throw new CMSException("Tipo "+signer.getClass()+" non supportato!");			
//			}
//		}
//		return result;
//	}
	
//	protected static final List<ISignature> getISigneturesFromCMSSignedDataInternal(CMSSignedData signedData, List<File> detachedContent) throws CMSException {
//		
//		List<ISignature> result = new ArrayList<ISignature>();
//		
//		Collection<?> certificates = null;
//		try {
//			Store storecertificate = signedData.getCertificates();
//			certificates = storecertificate.getMatches(null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Collection<?> signers = (Collection<?>)signedData.getSignerInfos().getSigners();
//		for (Object signer: signers) {
//			if(signer instanceof SignerInformation){
//				//SignerId signerID  = ((SignerInformation)signer).getSID();
//				ISignature signature = getISignatureFromSignerInformationAndCertificates((SignerInformation)signer, certificates, detachedContent);
//				if (signature!=null){
//					result.add(signature);
//				}
//			}else{
//				throw new CMSException("Tipo "+signer.getClass()+" non supportato!");			
//			}
//		}
//		return result;
//	}
	
//	public static ISignature getISignatureFromSignerInformationAndCertificates(SignerInformation signer, Collection<?> certificates, List<File> detachedContent) {
//		
//		SignerId signerID  = signer.getSID();
//		
//		AttributeTable signedTable = signer.getSignedAttributes();
//		Attribute signingCertificateV2Attr = null;
//		Attribute signingCertificateAttr = null;
//		if (signedTable!=null) {
//			signingCertificateV2Attr = (Attribute)signedTable.toHashtable().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
//			signingCertificateAttr = (Attribute)signedTable.toHashtable().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
//		}
//		
//		// Hash da utilizzare per identificare il certificato di firma
//		byte[] certHash  = null;
//		String certHashAlgorithmOid = null;
//		
//		
//		try {
//			if (signingCertificateV2Attr!=null) {
//				
//				// Cerca di recuperare l'hash del certificato da una struttura 'ben fatta'
//				try {
//					SigningCertificateV2 signingCertificateV2 = SigningCertificateV2.getInstance(signingCertificateV2Attr.getAttrValues().getObjectAt(0));
//					if (signingCertificateV2!=null) {
//						ESSCertIDv2[] essCertsV2 = signingCertificateV2.getCerts();
//						ESSCertIDv2 essCertV2 = essCertsV2[0];
//						certHash = essCertV2.getCertHash();
//						certHashAlgorithmOid = essCertV2.getHashAlgorithm().getAlgorithm().getId();
//					}
//				}catch(Exception e) {			
//					
//					// Se c'� stato un errore, cerca di recuperare l'hash dal contenuto stesso 
//					ASN1Sequence signingCertificateV2Encoded = (ASN1Sequence)signingCertificateV2Attr.getAttrValues().getObjectAt(0);
//					ASN1Sequence signingCertificateV2Certs =  ASN1Sequence.getInstance(signingCertificateV2Encoded.getObjectAt(0));
//					certHash = ASN1OctetString.getInstance(signingCertificateV2Certs.getObjectAt(0).getDERObject()).getOctets();
//					// Di default l'algoritmo di hash viene posto a SHA-256
//					certHashAlgorithmOid = CMSSignedDataGenerator.DIGEST_SHA256;
//				}
//			}
//			else if (signingCertificateAttr != null) {
//				// Cerca di recuperare l'hash del certificato da una struttura 'ben fatta'
//				try {
//					SigningCertificate signingCertificate = SigningCertificate.getInstance(signingCertificateAttr.getAttrValues().getObjectAt(0));
//					if (signingCertificateAttr!=null) {
//						ESSCertID[] essCertsV2 = signingCertificate.getCerts();
//						ESSCertID essCert = essCertsV2[0];
//						certHash = essCert.getCertHash();
//						certHashAlgorithmOid = CMSSignedDataGenerator.DIGEST_SHA1;
//					}
//				}catch(Exception e) {			
//					
//					// Se c'� stato un errore, cerca di recuperare l'hash dal contenuto stesso 
//					ASN1Sequence signingCertificateEncoded = (ASN1Sequence)signingCertificateAttr.getAttrValues().getObjectAt(0);
//					ASN1Sequence signingCertificateCerts =  ASN1Sequence.getInstance(signingCertificateEncoded.getObjectAt(0));
//					certHash = ASN1OctetString.getInstance(signingCertificateCerts.getObjectAt(0).getDERObject()).getOctets();
//					certHashAlgorithmOid = CMSSignedDataGenerator.DIGEST_SHA1;
//				}
//			}
//		}catch(Exception e) {
//			// C'� stato un errore durante la decodifica degli attributi contenenti
//			// l'hash del certificato di firma
////			e.printStackTrace();
//		}
//		
//			
//				
//		for (Object obj: certificates) {
//			
//			
//			Certificate certificate = null;
//			
//			if(obj instanceof X509CertificateHolder){
//				try {
//					certificate = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate((X509CertificateHolder)obj);
//				} catch (CertificateException e) {
//					e.printStackTrace();
//				}
//			}else{
//				certificate = (Certificate)obj;
//			}
//						
//			boolean correctCertificate = false;
//			// Identifica il certificato dal suo hash
//			if (certHash!=null) {
//				try {
//					MessageDigest digest = MessageDigest.getInstance(certHashAlgorithmOid);
//					if (digest==null)
//						return null;
//					byte[] computedCertificateHash = digest.digest(certificate.getEncoded());
//					if (org.bouncycastle.util.Arrays.areEqual(certHash, computedCertificateHash))
//						correctCertificate = true;
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//			else if (certificate instanceof X509Certificate && 
//					((X509Certificate)certificate).getIssuerX500Principal().equals(signerID.getIssuer()) &&
//					signerID.getSerialNumber().equals(((X509Certificate)certificate).getSerialNumber()) 
//				) { 
//				correctCertificate =true;
//			}
//			
//			if (correctCertificate) {
//				CMSSignature cmsSignature = new CMSSignature(signer, (X509Certificate)certificate);
//				cmsSignature.setDetachedFiles(detachedContent);
//				
//				// Aggiorno la lista delle controfirme
//				SignerInformationStore counterSignaturesStore = signer.getCounterSignatures();
//				Collection<?> counterSignaturesInfo  = counterSignaturesStore.getSigners();
//				if (counterSignaturesInfo!=null) {
//					List<ISignature> counterSignatures = new ArrayList<ISignature>();
//					for (Object counterSignatureInfo: counterSignaturesInfo){
//						if(counterSignatureInfo instanceof SignerInformation){
//							counterSignatures.add(getISignatureFromSignerInformationAndCertificates((SignerInformation)counterSignatureInfo, certificates, null));
//						}
//					}
//					cmsSignature.setCounterSignatures(counterSignatures);
//				}
//				
//				return cmsSignature;
//			}
//		}
//		return null;
//	}

	
//	public List<ISignature> getSignatures() {
//		try {
//			CMSSignedDataParser cmsSignedData = null;
//			if (isBase64()) {
//				
//				File tmp = null;
//				//Controllo se il file comincia per -----BEGIN
//				LineIterator iterator = FileUtils.lineIterator(file);
//				String firstline = iterator.nextLine();
//				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
//					tmp = File.createTempFile("tmp", ".tmp");
//					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
//					while(iterator.hasNext()){				
//						String line = iterator.nextLine();
//						if(!StringUtils.containsIgnoreCase(line,"-----END")){
//							writer.write(line);
//							writer.newLine();
//							writer.flush();							
//						}else{
//							writer.close();							
//						}
//					}
//					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
//					cmsSignedData= new CMSSignedDataParser(streambase64);
//					FileUtil.deleteFile(tmp);
//				}else{
//					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
//					cmsSignedData= new CMSSignedDataParser(streambase64);	
//				}
//			} else {
//				cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
//			}
//			cmsSignedData.getSignedContent().drain();		
//			return getISigneturesFromCMSSignedData(cmsSignedData, detachedFiles);
//		}catch(Exception e) {
//			e.printStackTrace();
//			//throw new CMSException("Generic",e);
//		}
//		return null;
//	}

	
	public boolean canContentBeSigned() {
		return true;
	}

	public static Collection<CRL> getCRLsFromCMSSignedData(CMSSignedDataParser cmsSignedData) {
		Store store;
		Collection<CRL> crls = null;
		try {
			store = cmsSignedData.getCRLs();
			crls = store.getMatches(new X509CRLStoreSelector());
			return crls;
		} catch (Exception e) {
			return null;
		}
		
	}

	public static Collection<? extends Certificate> getCertificatesFromCMSSignedData(CMSSignedDataParser cmsSignedData) {
			try {
				Store store = cmsSignedData.getCertificates();
				List<Certificate> certificateslist = new ArrayList<Certificate>();
				Collection certificates = store.getMatches(null);
				for (Object obj: certificates) {
					Certificate certificate = null;
					if(obj instanceof X509CertificateHolder){
						try {
							certificate = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate((X509CertificateHolder)obj);
						} catch (CertificateException e) {
							e.printStackTrace();
						}
					}else{
						certificate = (Certificate)obj;
					}
					certificateslist.add(certificate);					
				}
				
				if(certificateslist.isEmpty()){
					certificateslist = null;
				}				
				return certificateslist;
			} catch (Exception e) {
				return null;
			}
	}
	
	public Collection<CRL> getEmbeddedCRLs() {
		try {
			CMSSignedDataParser cmsSignedData = null;
			if (isBase64()) {
				File tmp = null;
				//Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while(iterator.hasNext()){				
						String line = iterator.nextLine();
						if(!StringUtils.containsIgnoreCase(line,"-----END")){
							writer.write(line);
							writer.newLine();
							writer.flush();							
						}else{
							writer.close();							
						}
					}
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					cmsSignedData= new CMSSignedDataParser(streambase64);
					//FileUtil.deleteFile(tmp);
				}else{
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					cmsSignedData= new CMSSignedDataParser(streambase64);
				}
			} else {
				cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
			}
			cmsSignedData.getSignedContent().drain();
			return getCRLsFromCMSSignedData(cmsSignedData);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<? extends Certificate> getEmbeddedCertificates() {
		FileInputStream fis;
		try {
			CMSSignedDataParser cmsSignedData = null;
			if (isBase64()) {
				File tmp = null;
				//Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while(iterator.hasNext()){				
						String line = iterator.nextLine();
						if(!StringUtils.containsIgnoreCase(line,"-----END")){
							writer.write(line);
							writer.newLine();
							writer.flush();							
						}else{
							writer.close();							
						}
					}
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					cmsSignedData= new CMSSignedDataParser(streambase64);
					//FileUtil.deleteFile(tmp);
				}else{
					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					cmsSignedData= new CMSSignedDataParser(streambase64);
				}
			} else {
				cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
			}
			cmsSignedData.getSignedContent().drain();
			return getCertificatesFromCMSSignedData(cmsSignedData);
		} catch (Exception e) {
			
		}
		return null;
	}
}