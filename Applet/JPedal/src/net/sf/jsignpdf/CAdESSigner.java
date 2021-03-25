package net.sf.jsignpdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Arrays;
import org.jpedal.examples.viewer.utils.ValidationInfos;

/**
 * Implementa i controlli su firme di tipo CAdES.
 * Il contenuto di un file � riconosciuto se implementa le specifiche RFC5126
 * @author Stefano Zennaro
 *
 */
public class CAdESSigner extends P7MSigner {
	
	private SignerType type = null;
	
	private Map<byte[], TimeStampToken> timestamptokensBySignature = null;
	
//	private CMSSignedData signedData= null;
		
//	public TimeStampToken[] getTimeStampTokens() {
//		InputStream stream = null;
//		ArrayList<TimeStampToken> timestampTokensList= new ArrayList<TimeStampToken>();  
//		if (timestamptokens==null) {
//			try {
//				CMSSignedDataParser cmsSignedData = null;
//				if (isBase64()) {
//					Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
//					cmsSignedData= new CMSSignedDataParser(streambase64);	
//				} else {
//					cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
//				}
//				cmsSignedData.getSignedContent().drain();
//				SignerInformationStore signersStore = cmsSignedData.getSignerInfos();
//				Collection<? extends SignerInformation> signers = signersStore.getSigners();
//				
//				timestamptokensBySignature = new HashMap<byte[], TimeStampToken>();
//				if (signers != null) {
//					for (SignerInformation signer: signers) {
//						AttributeTable table  = signer.getUnsignedAttributes();
//						if (table==null)
//							return null;
//						Attribute attribute = (Attribute)table.toHashtable().get(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.47"));
//						if (attribute!=null && attribute.getAttrValues()!=null) {
//							TimeStampToken timestamptoken = null;
//							try {
//								timestamptoken = new TimeStampToken(new CMSSignedData(attribute.getAttrValues().getObjectAt(0).getDERObject().getEncoded()));
//							} catch (Exception e) {
//								e.printStackTrace();
//								//TODO costa formato marca non corretto!?
//							}
//							if (timestamptoken!=null) {
//								timestampTokensList.add(timestamptoken);
//								timestamptokensBySignature.put(signer.getSignature(), timestamptoken);
//							}
//						}
//					}
//				}
//				if (timestampTokensList.size()!=0)
//					timestamptokens =  timestampTokensList.toArray(new TimeStampToken[timestampTokensList.size()]);
//			}catch(Exception e) {
//				e.printStackTrace();
//				timestamptokens=null;
//			} finally {
//				if (stream!=null) {
//					IOUtils.closeQuietly(stream);
//				}
//			}
//			
//		}
//		return timestamptokens;
//	}

	
	/**
	 * Restituisce true se il contenuto del file � di tipo CMS e rispetta
	 * le seguenti condizioni:
	 * <ul>
	 * 	<li>L'algoritmo di digest deve essere SHA256</li>
	 *  <li>Il certificato di firma deve essere presente come attributo signing-certificate oppure ESS-signing-certificate-v2</li>
	 * </ul>
	 * Recupera inoltre il timestamp se presente come attributo non firmato (CAdES-T)
	 */
	protected boolean isSignedType(CMSSignedDataParser cmsSignedDataInternal) {
		//Resetto il signer
		reset();
		type = null;
		timestamptokensBySignature = null;
		
		boolean signed = false;
		try {
			cmsSignedDataInternal.getSignedContent().drain();
						
			SignerInformationStore signersStore = cmsSignedDataInternal.getSignerInfos();
			Collection<SignerInformation> signers = signersStore.getSigners();
			if (signers==null || signers.isEmpty()) {
				signed = false;
			} else {
				//Controllo se l'algoritmo � di tipo SHA256 e che sia presente l'attributo contenente il certificato
				for (SignerInformation signer:signers) {
					AttributeTable signedTable = signer.getSignedAttributes();
					boolean certv2 = signedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
					boolean cert = signedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signingCertificate);
					if (certv2 && cert) {
						signed = false;
						break;
					} else {	
						// L'algoritmo di digest deve essere SHA256, inoltre deve essere presente il certificato
						// come attributo signing-certificate oppure ESS-signing-certificate-v2
						if (! (CMSSignedDataGenerator.DIGEST_SHA256.equals(signer.getDigestAlgOID()) && (certv2 || !cert)) ) {
							signed = false;
							break;
						}
					}
					signed = true;
					
					// I formati CAdES_T e CAdES_C sono pi� restrittivi di CAdES_BES
					if (type == null)
						type = SignerType.CAdES_BES;
					
					//TODO Controllo da verificare
					AttributeTable unsignedTable  = signer.getUnsignedAttributes();
					if (unsignedTable!=null && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken)) {
						type = SignerType.CAdES_T;
						//Controllo se sono presenti gli atttibuti CRL negli attributi unsigned
						if (unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_certificateRefs) && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_revocationRefs)) {
							type = SignerType.CAdES_C;
						}
					}
					
				}
				
				
			}
		}catch(Exception e) {
			signed = false;
			
		}
		return signed;
	}
	
	

	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		this.file = file;
		File tmp = null;
		try {
			stream = FileUtils.openInputStream(file);
			CMSSignedDataParser parser = new CMSSignedDataParser(stream);
			signed = isSignedType(parser);
			stream.close();
			
			//Controolo se è un base64
			if(!signed){
				throw new Exception();			
			}	
			
		}catch(Exception e) {
			//controllo se è in base64
			try {
				
				//Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if(StringUtils.containsIgnoreCase(firstline,"-----BEGIN")){
					//Riscrivo il file				
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
				signed = false;
					try {
						if(stream!=null){
							stream.close();
						}
					} catch (IOException e1) {}
			}finally{
				if(tmp!=null){
					//FileUtil.deleteFile(tmp);
				}
				
			}
		}
		
		return signed;
	}
	

	public SignerType getFormat() {
		return type;
	}


	/*
	 * @see it.eng.utility.cryptosigner.data.AbstractSigner#validateTimeStampTokenEmbedded()
	 */
//	public ValidationInfos validateTimeStampTokensEmbedded() throws CMSException {
//		ValidationInfos validationInfos = new ValidationInfos();
//		
//		if (type ==SignerType.CAdES_BES) {
//			validationInfos.addError("Il formato: "+this.type +" non contiene una marca temporale");
//			return validationInfos;
//		}
//		
//		if (this.timestamptokens == null) {
//			if (!this.isSignedType(file)) {
//				validationInfos.addError("File non in formato: " + this.getFormat());
//				return validationInfos;
//			}
//			else getTimeStampTokens();
//		}
//		CMSSignedDataParser cmsSignedData=null;
//		try {
//			if (isBase64()) {
//				Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
//				cmsSignedData= new CMSSignedDataParser(streambase64);	
//			} else {
//				cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file));
//			}
//		} catch (IOException e1) {
//			throw new CMSException("Generic",e1);
//		}
//		
//		SignerInformationStore signersStore = cmsSignedData.getSignerInfos();
//		Collection<SignerInformation> signers = signersStore.getSigners();
//		if (signers!=null) {
//			for (SignerInformation signerInfo: signers) {
//				byte[] signature = signerInfo.getSignature();
//				Set<byte[]> signatures = timestamptokensBySignature.keySet();
//				TimeStampToken timestamptoken = null;
//				for (byte[] byteSignature: signatures) {
//					if (Arrays.areEqual(byteSignature, signature)) {
//						timestamptoken = timestamptokensBySignature.get(byteSignature);
//						break;
//					}
//				}
//				String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID();	
//				MessageDigest digest;
//				try {
//					digest = MessageDigest.getInstance(hashAlgOID);
//					TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
//					TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(signature));
//					
//					checkTimeStampTokenOverRequest(validationInfos, timestamptoken, request);
//					
//				} catch (NoSuchAlgorithmException e) {
//					validationInfos.addError("Impossibile validare la marca poich� l'algoritmo di calcolo non � supportato: " + hashAlgOID );
//				}
//			}
//		}
//
//		return validationInfos;
//	}
}