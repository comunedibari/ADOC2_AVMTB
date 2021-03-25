package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SimpleBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AggiornaInfoFileBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.CalcolaFirmaBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "CalcolaFirmaDatasource")
public class CalcolaFirmaDatasource extends AbstractServiceDataSource<SimpleBean, MimeTypeFirmaBean> {

	@Override
	public MimeTypeFirmaBean call(SimpleBean pInBean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		LinkedHashMap<String, Object> info = (LinkedHashMap) pInBean.getValue();
		String uri = (String) info.get("uri");
		Boolean remote = info.get("remote") != null && (Boolean) info.get("remote");
		String lStrPathFile = null;
		String fileName = null;
		if (remote) {
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uri);
			FileExtractedOut lFileExtractedOut = lRecuperoFile.extractfile(getLocale(), lAurigaLoginBean, lFileExtractedIn);
			File lFile = lFileExtractedOut.getExtracted();
			lStrPathFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFile)).toURI().toString();
			fileName = lFile.getName();
		} else {
			File lFile = StorageImplementation.getStorage().getRealFile(uri);
			lStrPathFile = lFile.toURI().toString();
			fileName = lFile.getName();
		}
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setDaScansione(false);
		try {
			Date lDate = null;
			String lStrDate = (String) info.get("dataRif");
			if (lStrDate != null && !lStrDate.trim().equals("")) {
				SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				lDate = lSimpleDateFormat.parse(lStrDate);
			}
			return lInfoFileUtility.getInfoFromFile(lStrPathFile, fileName, false, lDate);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public AggiornaInfoFileBean aggiornaInfoFileFirmati(AggiornaInfoFileBean bean) {
		// TODEL aggingo il flag firmto su auriga e timbrato
		HashMap<String, CalcolaFirmaBean> nuovaMappaFilseFirmati = new HashMap<String, CalcolaFirmaBean>();;
		HashMap<String, CalcolaFirmaBean> mappaFileFirmati = bean.getFileFirmati();
		List<CalcolaFirmaBean> listaFileVerPreFirma = bean.getFileVerPreFirma();
		
		Set<String> idFileSet = mappaFileFirmati.keySet();
		if (idFileSet != null) {
			for (String idFile : idFileSet) {
				CalcolaFirmaBean fileVerPreFirma = null;
				for (CalcolaFirmaBean file : listaFileVerPreFirma) {
					String idFileVerPreFirma = file.getIdFile();
					if (idFileVerPreFirma != null && idFileVerPreFirma.equalsIgnoreCase(idFile)){
						fileVerPreFirma = file;
					}
				}
				
				MimeTypeFirmaBean infoFilePreFirma = null;
				if (fileVerPreFirma != null && fileVerPreFirma.getInfoFile() != null) {
					infoFilePreFirma = fileVerPreFirma.getInfoFile();
				}
				CalcolaFirmaBean fileFirmato = mappaFileFirmati.get(idFile);
				MimeTypeFirmaBean infoFileFirmato = fileFirmato.getInfoFile();
				boolean presentiFirmeExtraAuriga = infoFilePreFirma != null && infoFilePreFirma.getInfoFirmaMarca() != null ? infoFilePreFirma.getInfoFirmaMarca().isFirmeExtraAuriga() : false;
				if (infoFileFirmato != null && infoFileFirmato.getFirmatari() != null){
					int numeroFirmatariBustaEsterna = 0;
					Firmatari firmatarioPiuRecente =  null;
					for (Firmatari bustaFileFirmato : infoFileFirmato.getBuste()) {
						if (!presentiFirmeExtraAuriga){
							bustaFileFirmato.setFirmaExtraAuriga(false);
						}
						if ("1".equalsIgnoreCase(bustaFileFirmato.getFiglioDi())) {
							numeroFirmatariBustaEsterna ++;
						}
						if (firmatarioPiuRecente == null || (bustaFileFirmato.getDataFirma() != null && firmatarioPiuRecente.getDataFirma() != null && bustaFileFirmato.getDataFirma().before(firmatarioPiuRecente.getDataFirma()))){
							firmatarioPiuRecente = bustaFileFirmato;
						}
					}
					// Se sono in firma verticale la busta l'ha fatta Auriga, altrimenti lascio il valore di bustaCrittograficaFattaDaAuriga inalterato
					if (numeroFirmatariBustaEsterna == 1) {
						infoFileFirmato.getInfoFirmaMarca().setBustaCrittograficaFattaDaAuriga(true);
					} else {
						if (infoFilePreFirma != null && infoFilePreFirma.getInfoFirmaMarca() != null) {
							infoFileFirmato.getInfoFirmaMarca().setBustaCrittograficaFattaDaAuriga(infoFilePreFirma.getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga());
						}
					}
					// Il firmatario più recente ha sicuramente firmato su Auriga
					firmatarioPiuRecente.setFirmaExtraAuriga(false);
				}
				// Setto le impostazioni della marca temporale
				// Se dopo una firma il file è marcato temporalmente allora la marca è stata fatta da Auriga, in quanto se firmo un file già marcato
				// viene fatta sempre una firma CAdES verticale, e quindi la marcca preesistente finiribbe su una busta crittografica interna
				if (infoFileFirmato != null && infoFileFirmato.getInfoFirmaMarca() != null && infoFileFirmato.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
					infoFileFirmato.getInfoFirmaMarca().setMarcaTemporaleAppostaDaAuriga(true);
				}
				fileFirmato.setInfoFile(infoFileFirmato);
				nuovaMappaFilseFirmati.put(fileFirmato.getIdFile(), fileFirmato);
			}
		}
		bean.setFileFirmati(nuovaMappaFilseFirmati);
		return bean;
		
	}
}
