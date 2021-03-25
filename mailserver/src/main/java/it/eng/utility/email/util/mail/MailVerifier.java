package it.eng.utility.email.util.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xml.security.Init;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMESignedParser;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.fileoperation.FileUtilities;
import it.eng.module.foutility.beans.generated.CertVerificationStatusType;
import it.eng.module.foutility.beans.generated.VerificationInfo;
import it.eng.module.foutility.beans.generated.VerificationRequest;
import it.eng.module.foutility.beans.generated.VerificationResultType;
import it.eng.module.foutility.beans.generated.VerificationResults;
import it.eng.module.foutility.beans.generated.VerificationTypes;
import it.eng.module.foutility.cert.CertificateVerifierImpl;

/**
 * classe di verifica del messaggio email che estende quello di AurigaMailBusiness specializzando il metodo di verifica del certificato
 * 
 * @author jravagnan
 *
 */

public final class MailVerifier extends it.eng.aurigamailbusiness.utility.mail.MailVerifier {

	private static Logger logger = LogManager.getLogger(MailVerifier.class);

	public static final String encoding = "base64";

	public static final String algoritmo = "SHA-256";

	private String wsEndpoint;

	private boolean caCheck;

	private boolean crlCheck;

	Provider bcprov;
	JcaSimpleSignerInfoVerifierBuilder verifier;
	JcaX509CertificateConverter jcaX509CertificateConverter;
	DigestCalculatorProvider digestCalculatorProvider = new BcDigestCalculatorProvider();

	public MailVerifier() {

		this.bcprov = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
		if (this.bcprov == null) {
			this.bcprov = new BouncyCastleProvider();
			Security.addProvider(bcprov);
		}

		this.verifier = new JcaSimpleSignerInfoVerifierBuilder();
		this.verifier.setProvider(bcprov);

		this.jcaX509CertificateConverter = new JcaX509CertificateConverter();
		this.jcaX509CertificateConverter.setProvider(bcprov);

		if (!Init.isInitialized()) {
			Init.init();
		}

	}

	@SuppressWarnings("unchecked")
	protected Set<Certificate> verify(final SMIMESignedParser parserSMIMES) throws Exception {
		final Set<Certificate> certificates = new HashSet<Certificate>();
		final org.bouncycastle.util.Store certs = parserSMIMES.getCertificates();
		final SignerInformationStore signers = parserSMIMES.getSignerInfos();
		final Collection<SignerInformation> collection = signers.getSigners();
		for (SignerInformation signer : collection) {
			final Collection<X509Certificate> certCollection = (Collection<X509Certificate>) certs.getMatches(signer.getSID());
			final Object cert = certCollection.iterator().next();
			try {
				if (cert instanceof X509Certificate) {
					boolean testCert = verificaCert((X509Certificate) cert);
					if (testCert)
						certificates.add((Certificate) cert);
					else
						throw new Exception("signature invalid");
				} else if (cert instanceof X509CertificateHolder) {
					X509Certificate certx509 = this.jcaX509CertificateConverter.getCertificate((X509CertificateHolder) cert);
					try {
						boolean testCert = verificaCert(certx509);
						if (!testCert)
							throw new Exception("signature invalid");
					} catch (Exception e) {
						if (!signer.verify(this.verifier.build(certx509))) {
							throw new Exception("signature invalid");
						}
					}
				}
			} catch (Exception e) {
				logger.error("Impossibile verificare il certificato ", e);
				throw e;
			}
		}
		return certificates;
	}

	private boolean verificaCert(X509Certificate cert) throws MalformedURLException, CertificateEncodingException {

		VerificationRequest request = new VerificationRequest();
		request.setX509Cert(cert.getEncoded());
		VerificationInfo vinfo = new VerificationInfo();
		vinfo.setVerificationID(VerificationTypes.CERTIFICATE_EXPIRATION);
		request.getVerificationInfo().add(vinfo);
		if (caCheck) {
			VerificationInfo vinfoca = new VerificationInfo();
			vinfoca.setVerificationID(VerificationTypes.CA_VERIFY);
			request.getVerificationInfo().add(vinfoca);
		}
		if (crlCheck) {
			VerificationInfo vinfocrl = new VerificationInfo();
			vinfocrl.setVerificationID(VerificationTypes.CRL_VERIFY);
			request.getVerificationInfo().add(vinfocrl);
		}
		VerificationResults result = new CertificateVerifierImpl().check(request);
		if (result != null) {
			List<VerificationResultType> results = result.getVerificationResult();
			for (VerificationResultType verificationResultType : results) {
				if (verificationResultType != null) {
					logger.info("verificationResultType.getVerificationID() " + verificationResultType.getVerificationID());
					logger.info("verificationResultType.getVerificationStatus() " + verificationResultType.getVerificationStatus());
					if (verificationResultType.getVerificationID().equals(VerificationTypes.CERTIFICATE_EXPIRATION)
							&& !verificationResultType.getVerificationStatus().equals(CertVerificationStatusType.OK)) {
						logger.info("errorMessage " + verificationResultType.getErrorMessage());
						return false;
					}
					if (caCheck) {
						if (verificationResultType.getVerificationID().equals(VerificationTypes.CA_VERIFY)
								&& !verificationResultType.getVerificationStatus().equals(CertVerificationStatusType.OK)) {
							logger.info("errorMessage " + verificationResultType.getErrorMessage());
							return false;
						}
					}
					if (crlCheck) {
						if (verificationResultType.getVerificationID().equals(VerificationTypes.CRL_VERIFY)
								&& !verificationResultType.getVerificationStatus().equals(CertVerificationStatusType.OK)) {
							logger.info("errorMessage " + verificationResultType.getErrorMessage());
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}

	public boolean isCaCheck() {
		return caCheck;
	}

	public void setCaCheck(boolean caCheck) {
		this.caCheck = caCheck;
	}

	public boolean isCrlCheck() {
		return crlCheck;
	}

	public void setCrlCheck(boolean crlCheck) {
		this.crlCheck = crlCheck;
	}

	/**
	 * Copia l'attachment su un file temporaneo e ne assegna la dimensione, calcola inoltre un hash in SHA256 in modo da identificare i file, il nome del file
	 * infatti non è detto che sia univoco
	 * 
	 * @param stream
	 * @param attachmentBean
	 * @return
	 * @throws IOException
	 */

	@Override
	protected MailAttachmentsBean processAttachment(InputStream stream, File tempDir, MailAttachmentsBean attachmentBean) throws Exception {
		FileOutputStream output = null;
		try {
			File tempFile = File.createTempFile("Attachments", ".tmp", tempDir);
			output = new FileOutputStream(tempFile);
			IOUtils.copy(stream, output);
			IOUtils.closeQuietly(output);
			attachmentBean.setSize(tempFile.length());
			attachmentBean.setFile(tempFile);
			String encodedHash = FileUtilities.calcolaImprontaWithoutFileOp(tempFile, algoritmo, encoding);
			attachmentBean.setEncodedHash(encodedHash);
			attachmentBean.setAlgoritmo(algoritmo);
			attachmentBean.setEncoding(encoding);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					logger.warn("Errore nella chiusura dello stream", e);
				}
			}
		}
		return attachmentBean;
	}

}
