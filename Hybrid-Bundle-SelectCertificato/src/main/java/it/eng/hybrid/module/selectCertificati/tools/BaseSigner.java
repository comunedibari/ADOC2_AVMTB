package it.eng.hybrid.module.selectCertificati.tools;

import it.eng.hybrid.module.selectCertificati.bean.PrivateKeyAndCert;
import it.eng.hybrid.module.selectCertificati.exception.SmartCardException;
import it.eng.hybrid.module.selectCertificati.preferences.PreferenceKeys;
import it.eng.hybrid.module.selectCertificati.preferences.PreferenceManager;

import java.security.AuthProvider;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;

/**
 * Classe Base signer che implementa i metodi base di recupero dati della smartcard
 * @author michele
 *
 */
public class BaseSigner extends AbstractSigner {

	public final static Logger logger = Logger.getLogger(BaseSigner.class);
	
	public BaseSigner(AuthProvider provider) {
		super(provider);
	}

	@Override
	public X509Certificate[] getSigningCertificates(char[] pin, char[] certPin) throws SmartCardException {
		List<X509Certificate> certificateList = new ArrayList<X509Certificate>();
		try{
			provider.login(new Subject(), new PasswordHandler(pin));

			KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
			keyStore.load(null,null);

			boolean emulation = false;
			if( PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE ) )
				emulation = true;
			
			Enumeration enumeration = keyStore.aliases();
			while(enumeration.hasMoreElements()){
				String alias = enumeration.nextElement().toString();
				logger.info("ALIAS:"+alias);
				X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
				boolean[] usage = certificate.getKeyUsage();
				logger.info("usage[1]:"+usage[1]);
				if (usage[1] || emulation)
					certificateList.add(certificate);
			}
		} catch(Exception e){
			logger.info("Errore ", e);	
			throw new SmartCardException(e);
		} finally{
			try{
				provider.logout();
			} catch(Exception e){}
		}
		return certificateList.toArray(new X509Certificate[certificateList.size()]);
	}
	
	@Override
	public PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin, char[] certPin) throws SmartCardException {
		List<PrivateKeyAndCert> pkcList = new ArrayList<PrivateKeyAndCert>();
		try{
			provider.login(new Subject(), new PasswordHandler(pin));

			KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
			logger.info("Cerco di recuperare il key store ");
			keyStore.load(null,certPin);

//			boolean emulation = false;
//			if( PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE ) )
//				emulation = true;
			
			Enumeration enumeration = keyStore.aliases();
			while(enumeration.hasMoreElements()){
				String alias = enumeration.nextElement().toString();
				logger.info("ALIAS:"+alias);
				PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, certPin); 
				logger.info("recuperata la chiave");
				X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
				boolean[] usage = certificate.getKeyUsage();
//				logger.info("non considero usage");
				if (usage[1]){//|| emulation){
					Date dataScadenza = certificate.getNotAfter();
					Date dataInizioValidita = certificate.getNotBefore();
					Date dataCorrente = new Date();
					if( dataCorrente.before( dataScadenza ) && 
							dataCorrente.after(dataInizioValidita) ){
						logger.info("Certificato con data valida e bit di non ripudio");
						PrivateKeyAndCert privAndCert=new PrivateKeyAndCert();
						privAndCert.setCertificate(certificate);
		            	privAndCert.setPrivateKey(privateKey);
		            	privAndCert.setChain( keyStore.getCertificateChain(alias));
		            	privAndCert.setAlias(alias);
						pkcList.add(privAndCert);
					} else {
						logger.info("Certificato con data non valida e bit di non ripudio");
					}
					
				} else {
					logger.info("Certificato senza bit di non ripudio");
			}
			}
		} catch(Exception e){
			logger.info("Errore ", e);	
			throw new SmartCardException(e);
		} finally{
			try{
				provider.logout();
			} catch(Exception e){}
		}
		return pkcList.toArray(new PrivateKeyAndCert[pkcList.size()]);
	}

	@Override
	public X509Certificate[] getSigningCertificates(char[] pin)
			throws SmartCardException {
		return getSigningCertificates(pin, null);
	}

	@Override
	public PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin)
			throws SmartCardException {
		return getPrivateKeyAndCert(pin, null);
	}



}