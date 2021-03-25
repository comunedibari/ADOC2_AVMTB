package it.eng.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class InfoGenericFile {

	private static final Logger log = Logger.getLogger(InfoGenericFile.class);

	public GenericFile get(RebuildedFile lRebuildedFile, String idUserScansione) throws Exception {

		GenericFile lGenericFile = new GenericFile();
		try {

			InfoFileUtility lFileUtility = new InfoFileUtility();
			MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(),
					false, null);

			if (lMimeTypeFirmaBean.getFirmatari() != null) {
				List<Firmatario> lList = new ArrayList<Firmatario>();
				for (String lString : lMimeTypeFirmaBean.getFirmatari()) {
					Firmatario lFirmatario = new Firmatario();
					lFirmatario.setCommonName(lString);
					lList.add(lFirmatario);
				}
				lGenericFile.setFirmatari(lList);
				lGenericFile.setFirmato(Flag.SETTED);
			}
			
			if (lMimeTypeFirmaBean.getBuste() != null) {
				List<Firmatario> lList = new ArrayList<Firmatario>();
				for (Firmatari busta : lMimeTypeFirmaBean.getBuste()) {
					Firmatario lFirmatario = new Firmatario();
					lFirmatario.setCommonName(busta.getSubject());
					lFirmatario.setDataOraFirma(busta.getDataFirma());
					lFirmatario.setFirmatoDaAuriga(busta.isFirmaExtraAuriga() ? Flag.NOT_SETTED : Flag.SETTED);
					lFirmatario.setDataOraVerificaFirma(busta.getDataVerificaFirma());
					lFirmatario.setFirmaNonValida(busta.isFirmaValida() ? Flag.NOT_SETTED : Flag.SETTED);
					lFirmatario.setInfoFirma(busta.getInfoFirma());
					lFirmatario.setTipoFirma(busta.getTipoFirma());
					lFirmatario.setBustaEsterna((busta.getFiglioDi() != null && "1".equalsIgnoreCase(busta.getFiglioDi())) ? Flag.SETTED : Flag.NOT_SETTED);
					lList.add(lFirmatario);
				}
				lGenericFile.setFirmatari(lList);
				lGenericFile.setFirmato(Flag.SETTED);
				lGenericFile.setFirmaNonValida(lMimeTypeFirmaBean.isFirmaValida() ? Flag.NOT_SETTED : Flag.SETTED);
			}
			
			if (lMimeTypeFirmaBean.getInfoFirmaMarca() != null) {
				InfoFirmaMarca lInfoFirmaMarca = lMimeTypeFirmaBean.getInfoFirmaMarca();
				
				lGenericFile.setFlgBustaCrittograficaDocElFattaDaAuriga(lInfoFirmaMarca.isBustaCrittograficaFattaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
				lGenericFile.setTipoBustaCrittograficaDocEl(lInfoFirmaMarca.getTipoBustaCrittografica());
				lGenericFile.setInfoVerificaBustaCrittograficaDocEl(lInfoFirmaMarca.getInfoBustaCrittografica());
				lGenericFile.setFlgBustaCrittograficaDocElFattaDaAuriga(lInfoFirmaMarca.isFirmeExtraAuriga() ? Flag.NOT_SETTED : Flag.SETTED);
				lGenericFile.setTsVerificaBustaCrittograficaDocEl(lInfoFirmaMarca.getDataOraVerificaFirma());
			
				lGenericFile.setFlgMarcaTemporaleAppostaDaAuriga(lInfoFirmaMarca.isMarcaTemporaleAppostaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
				lGenericFile.setTsMarcaTemporale(lInfoFirmaMarca.getDataOraMarcaTemporale());
				lGenericFile.setTipoMarcaTemporaleDocEl(lInfoFirmaMarca.getTipoMarcaTemporale());
				lGenericFile.setInfoVerificaMarcaTemporaleDocEl(lInfoFirmaMarca.getInfoMarcaTemporale());
				lGenericFile.setFlgMarcaTemporaleDocElNonValida(lInfoFirmaMarca.isMarcaTemporaleNonValida() ? Flag.SETTED : Flag.NOT_SETTED);
				lGenericFile.setTsVerificaMarcaTempDocEl(lInfoFirmaMarca.getDataOraVerificaMarcaTemporale());
			}

			if (lMimeTypeFirmaBean.isDaScansione()) {
				lGenericFile.setDaScansione(Flag.SETTED);
				lGenericFile.setDataScansione(new Date());
				lGenericFile.setIdUserScansione(idUserScansione);
			} else {
				lGenericFile.setDaScansione(Flag.NOT_SETTED);
			}
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(lGenericFile.getDisplayFilename());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
			lGenericFile.setEncoding(lMimeTypeFirmaBean.getEncoding());
		} catch (Exception e) {
			throw new Exception("ERRORE nella funzione InfoGenericFile = " + e.getMessage());
		}

		return lGenericFile;
	}

}
