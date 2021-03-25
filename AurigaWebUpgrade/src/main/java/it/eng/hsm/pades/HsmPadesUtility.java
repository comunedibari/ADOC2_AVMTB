package it.eng.hsm.pades;

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
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.option.SignOption;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;

/**
 * 
 * @author Federico Cacco
 *
 *         Classe di utility per firma HSM Pades, ricavata dalla vecchia classe it.eng.arubaHsm.pades.ArubaHsmPadesClient
 */

public class HsmPadesUtility extends HsmBaseUtility {

	private static final Logger log = Logger.getLogger(HsmPadesUtility.class);

	public static FirmaHsmBean firmaPadesMultipla(FirmaHsmBean bean, HttpSession session) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("firmaPades inizio " + formatter.format(dataCorrente));

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

				SignResponseBean signResponseBean = hsmClient.firmaPades(bytesFileDaFirmare, signOption);

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
			log.warn(e);
		}

		return bean;
	}

}
