package it.eng.hsm.cades;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.hsm.HsmBaseUtility;
import it.eng.hsm.HsmClientFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;

/**
 * 
 * @author Federico Cacco
 * 
 *         Classe di utility per firma HSM Cades, ricavata dalla vecchia classe it.eng.arubaHsm.cades.ArubaHsmCadesClient
 *
 */

public class HsmCadesUtility extends HsmBaseUtility {

	private static final Logger log = Logger.getLogger(HsmCadesUtility.class);

	public static FirmaHsmBean firmaCadesMultipla(FirmaHsmBean bean, HttpSession session) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("firmaCadesMultipla inizio " + formatter.format(dataCorrente));

		// Creo il client
		Hsm hsmClient = HsmClientFactory.getHsmClient(session, bean);

		try {
			// Hsm hsmClient = HsmClientFactory.getHsmClient(hsmType);
			SignOption signOption = getSignOption();

			StorageService storageService = StorageImplementation.getStorage();

			List<FileDaFirmare> fileFirmati = new ArrayList<FileDaFirmare>();

			for (int i = 0; i < bean.getListaFileDaFirmare().size(); i++) {

				String uriFile = bean.getListaFileDaFirmare().get(i).getUri();

				File fileDaFirmare = storageService.getRealFile(uriFile);
				byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);

				SignResponseBean signResponseBean = hsmClient.firmaCades(bytesFileDaFirmare, signOption);

				if ((signResponseBean != null) && (signResponseBean.getMessage() != null)) {
					log.debug("Response Status: " + signResponseBean.getMessage().getStatus());
					log.debug("Response Return Code: " + signResponseBean.getMessage().getCode());
					if (signResponseBean.getMessage().getDescription() != null) {
						log.debug("Response Description: " + signResponseBean.getMessage().getDescription());
					}
					if (signResponseBean.getMessage().getStatus().equals(ResponseStatus.OK)) {

						fileFirmati.add(creaFileFirmato(hsmClient, bean.getListaFileDaFirmare().get(i), signResponseBean.getFileResponseBean().getFileFirmato(),
								bean.getFileDaMarcare(), bean.isSkipControlloFirmaBusta(), bean.getTipofirma()));

					}

				}

			}

			bean.setFileFirmati(fileFirmati);

		} catch (MalformedURLException e) {
			log.error("Errore in firma multipla cades", e);
		}
		dataCorrente = new Date();
		log.debug("firmaCadesMultipla fine " + formatter.format(dataCorrente));

		return bean;
	}

	public byte[] firmaCadesParallela(FirmaHsmBean bean, HttpSession session) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("firmaCadesParallela inizio " + formatter.format(dataCorrente));

		// Creo il client
		Hsm hsmClient = HsmClientFactory.getHsmClient(session, bean);

		StorageService storageService = StorageImplementation.getStorage();
		String uriFile = bean.getListaFileDaFirmare().get(0).getUri();
		File fileDaFirmare = storageService.getRealFile(uriFile);
		byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);

		try {
			// Hsm hsmClient = HsmClientFactory.getHsmClient(hsmType);
			SignOption signOption = getSignOption();

			SignResponseBean signResponseBean = hsmClient.firmaCadesParallela(bytesFileDaFirmare, signOption);

			if ((signResponseBean != null) && (signResponseBean.getMessage() != null)) {
				log.debug("Response Status: " + signResponseBean.getMessage().getStatus());
				log.debug("Response Return Code: " + signResponseBean.getMessage().getCode());
				if (signResponseBean.getMessage().getDescription() != null) {
					log.debug("Response Description: " + signResponseBean.getMessage().getDescription());
				}
				if (signResponseBean.getMessage().getStatus().equals(ResponseStatus.OK)) {
					FileResponseBean fileResponseBean = signResponseBean.getFileResponseBean();
					if (fileResponseBean != null && fileResponseBean.getFileFirmato() != null) {
						dataCorrente = new Date();
						log.debug("firmaCadesParallela fine " + formatter.format(dataCorrente));

						return fileResponseBean.getFileFirmato();
					}
				}
			}
		} catch (HsmClientConfigException e) {
			log.error("Errore in firma cades parallela", e);
		} catch (HsmClientSignatureException e) {
			log.error("Errore in firma cades parallela", e);
		} catch (UnsupportedOperationException e) {
			log.error("Errore in firma cades parallela", e);
		}
		return null;
	}

}
