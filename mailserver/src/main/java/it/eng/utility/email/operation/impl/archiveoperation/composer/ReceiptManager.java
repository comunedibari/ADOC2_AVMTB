package it.eng.utility.email.operation.impl.archiveoperation.composer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.Categoria;
import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.ReceiptBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.bean.TipoRicevuta;
import it.eng.aurigamailbusiness.database.dao.DaoTDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTRelEmailFolder;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.send.FolderEmail;
import it.eng.aurigamailbusiness.database.utility.send.StatoConsolidamentoEmail;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.XmlFieldUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.util.ListUtil;
import it.eng.utility.email.operation.impl.archiveoperation.utils.BeanUtils;
import it.eng.utility.email.operation.impl.archiveoperation.utils.Utilities;
import it.eng.utility.email.process.exception.MailServerException;

public class ReceiptManager {

	private Logger log = LogManager.getLogger(ReceiptManager.class);

	private Utilities util = null;

	/**
	 * 
	 * si occupa di gestire le ricevute delle email
	 * 
	 * @param emailCompleta
	 * @param orig
	 * @return
	 * @throws Exception
	 */
	public EmailBean process(EmailBean emailCompleta, TEmailMgoBean orig, MessageInfos infos, String idFruitore, String idCasella, Boolean cancellaPecInviata)
			throws Exception {
		log.debug("eseguo le logiche di ricevuta");
		DaoTDestinatariEmailMgo daoDestinatari = (DaoTDestinatariEmailMgo) DaoFactory.getDao(DaoTDestinatariEmailMgo.class);
		TFilterFetch<TDestinatariEmailMgoBean> filterFetch = new TFilterFetch<>();
		TDestinatariEmailMgoBean filtro = new TDestinatariEmailMgoBean();
		filtro.setIdEmail(orig.getIdEmail());
		filterFetch.setFilter(filtro);
		List<TDestinatariEmailMgoBean> destinatari = new ArrayList<>();
		ReceiptBean recBean = null;
		String mittenteRicevuta = null;
		StatoConsolidamentoEmail statoDestinatario = null;
		// NON E' UNA RICEVUTA DI LETTURA, NE' DI CANCELLAZIONE
		Boolean dispositionDisplayed = infos.isDispositionNotificationDisplayed();
		if (!infos.isDispositionNotificationDelete() && !dispositionDisplayed) {
			Categoria categoria = Categoria.valueOfValue(emailCompleta.getMail().getCategoria());
			// AGGIORNO LO STATO DI CONSOLIDAMENTO DELLE RICEVUTE
			// COME PRIMA COSA AGGIORNO QUELLO DEL DESTINATARIO DELLA EMAIL ARRIVATA
			try {
				destinatari = daoDestinatari.search(filterFetch).getData();
				switch (categoria) {
				case PEC_RIC_ACC:
					statoDestinatario = StatoConsolidamentoEmail.ACCETTATA;
					recBean = effettuaLogicaAccettazione(destinatari, orig, statoDestinatario, idFruitore, idCasella);
					// modifica 21/10/2014: gestione identificativo di trasporto per le PEC
					String messageIdTrasporto = XmlFieldUtil.cleanField(infos.getDaticert().getDati().getIdentificativo());
					log.info("MessageIdTrasporto: " + messageIdTrasporto);
					// aggiorno message-id nella mail originale, che è stata appena valorizzata nel precedente metodo
					recBean.getMailOriginaria().setMessageIdTrasporto(messageIdTrasporto);
					break;
				case PEC_RIC_NO_ACC:
					statoDestinatario = StatoConsolidamentoEmail.NON_ACCETTATA;
					recBean = effettuaLogicaAccettazione(destinatari, orig, statoDestinatario, idFruitore, idCasella);
					break;
				case PEC_RIC_PRESA_C:
					// Non si fa niente sullo stato consolidamento perch� rarissime
					break;
				case PEC_RIC_CONS:
					statoDestinatario = StatoConsolidamentoEmail.CONSEGNATA;
					// RICAVO IL MITTENTE DELLA RICEVUTA
					mittenteRicevuta = XmlFieldUtil.cleanField(infos.getDaticert().getDati().getConsegna());
					recBean = effettuaLogicaConsegna(destinatari, orig, statoDestinatario, infos, mittenteRicevuta, idFruitore, idCasella);
					if (BooleanUtils.isTrue(cancellaPecInviata) && BooleanUtils.isNotTrue(recBean.getMailOriginaria().getFlgUriRicevuta())
							&& TipoRicevuta.COMPLETA.getValue()
									.equalsIgnoreCase(XmlFieldUtil.cleanField(infos.getDaticert().getDati().getRicevuta().getTipo()))) {
						// aggiorno URI della mail originale, che è già stata valorizzata nel metodo precedente
						// anche l'URI della ricevuta è già stato valorizzato
						recBean.getMailOriginaria().setUriEmail(emailCompleta.getMail().getUriEmail());
						// indico anche che l'URI è stato sovrascritto con quello della ricevuta di consegna completa
						recBean.getMailOriginaria().setFlgUriRicevuta(true);
					}
					break;
				case PEC_RIC_PREAVV_NO_CONS:
					// RICAVO IL MITTENTE DELLA RICEVUTA
					mittenteRicevuta = XmlFieldUtil.cleanField(infos.getDaticert().getDati().getConsegna());
					statoDestinatario = StatoConsolidamentoEmail.AVVERTIMENTI_IN_CONSEGNA;
					recBean = effettuaLogicaConsegna(destinatari, orig, statoDestinatario, infos, mittenteRicevuta, idFruitore, idCasella);
					break;
				case PEC_RIC_NO_CONS:
					// RICAVO IL MITTENTE DELLA RICEVUTA
					mittenteRicevuta = XmlFieldUtil.cleanField(infos.getDaticert().getDati().getConsegna());
					statoDestinatario = StatoConsolidamentoEmail.ERRORI_IN_CONSEGNA;
					recBean = effettuaLogicaConsegna(destinatari, orig, statoDestinatario, infos, mittenteRicevuta, idFruitore, idCasella);
					break;
				case DELIVERY_STATUS:
					// RICAVO IL MITTENTE DELLA RICEVUTA
					mittenteRicevuta = ricavaIndirizzoDalCorpo(emailCompleta.getMail().getCorpo());
					statoDestinatario = StatoConsolidamentoEmail.ERRORI_IN_CONSEGNA;
					recBean = effettuaLogicaDelivery(destinatari, orig, statoDestinatario, mittenteRicevuta, idFruitore, idCasella);
					break;
				}
			} catch (Exception e) {
				log.error("Impossibile processare correttamente la ricevuta", e);
				throw e;
			}
			emailCompleta.setReceipt(recBean);
			if (!(emailCompleta.getMail().getFlgNoAssocAuto())) {
				util = new Utilities();
				TValDizionarioBean dizCategoria = util.ricavaDizionarioDaValore(Dizionario.CAUSALE);
				TValDizionarioBean dizRuolo = util.ricavaDizionarioDaValore(Dizionario.RICEVUTA_PEC);
				List<TRelEmailMgoBean> relEmail = BeanUtils.populateRelEmail(emailCompleta, dizCategoria, dizRuolo, mittenteRicevuta, orig.getIdEmail(),
						idCasella);
				emailCompleta.setRelMail(relEmail);
			}
		}
		// SE INVECE E' UNA RICEVUTA DI LETTURA FACCIAMO ALTRE LOGICHE
		else if (dispositionDisplayed) {
			try {
				log.debug("effettuo le logiche di ricevuta di lettura");
				mittenteRicevuta = infos.getMittenteWithPrincipalMail();
				destinatari = daoDestinatari.search(filterFetch).getData();
				statoDestinatario = StatoConsolidamentoEmail.CONSEGNATA;
				recBean = effettuaLogicaRicevutaLettura(destinatari, orig, statoDestinatario, infos, mittenteRicevuta, idCasella);
				emailCompleta.setReceipt(recBean);
				if (!(emailCompleta.getMail().getFlgNoAssocAuto())) {
					util = new Utilities();
					TValDizionarioBean dizCategoria = util.ricavaDizionarioDaValore(Dizionario.CAUSALE);
					TValDizionarioBean dizRuolo = util.ricavaDizionarioDaValore(Dizionario.NOTIFICA_CONFERMA);
					List<TRelEmailMgoBean> relEmail = BeanUtils.populateRelEmail(emailCompleta, dizCategoria, dizRuolo, mittenteRicevuta, orig.getIdEmail(),
							idCasella);
					emailCompleta.setRelMail(relEmail);
				}

			} catch (Exception e) {
				log.error("Impossibile processare correttamente la ricevuta", e);
				throw e;
			}
		}
		return emailCompleta;
	}

	/**
	 * ottiene lo stato di consolidamento della email originale partendo dallo stato dei destinatari solamente da quelli PEC
	 * 
	 * @param destinatari
	 * @return
	 * @throws MailServerException
	 */
	private StatoConsolidamentoEmail calcolaStatoDaDestinatari(List<TDestinatariEmailMgoBean> destinatari, String idFruitore) throws MailServerException {
		List<TDestinatariEmailMgoBean> destinatariConRicevuta = new ArrayList<>();
		boolean tutteRicevute = false;
		if (idFruitore == null) {
			if (destinatari.isEmpty()) {
				return StatoConsolidamentoEmail.CONSEGNATA;
			}
			for (TDestinatariEmailMgoBean dest : destinatari) {
				if (dest.getStatoConsolidamento() != null && !StringUtils.isEmpty(dest.getStatoConsolidamento())) {
					destinatariConRicevuta.add(dest);
				}
			}
			if (destinatariConRicevuta.size() == destinatari.size()) {
				return StatoConsolidamentoEmail.CONSEGNATA;
			}
			List<StatoConsolidamentoEmail> lista = new ArrayList<>();
			for (TDestinatariEmailMgoBean destRic : destinatariConRicevuta) {
				lista.add(StatoConsolidamentoEmail.valueOfValue(destRic.getStatoConsolidamento()));
			}
			if (lista.contains(StatoConsolidamentoEmail.ERRORI_IN_CONSEGNA)) {
				return StatoConsolidamentoEmail.ERRORI_IN_CONSEGNA;
			} else {
				return StatoConsolidamentoEmail.CONSEGNATA_PARZIALMENTE;
			}
		} else {
			for (TDestinatariEmailMgoBean dest : destinatari) {
				if (!StringUtils.isBlank(dest.getStatoConsolidamento())) {
					destinatariConRicevuta.add(dest);
				}
			}
			if (destinatariConRicevuta.size() == destinatari.size()) {
				tutteRicevute = true;
			}
			List<StatoConsolidamentoEmail> lista = new ArrayList<>();
			for (TDestinatariEmailMgoBean destRic : destinatariConRicevuta) {
				lista.add(StatoConsolidamentoEmail.valueOfValue(destRic.getStatoConsolidamento()));
			}
			if (lista.contains(StatoConsolidamentoEmail.ERRORI_IN_CONSEGNA)) {
				return StatoConsolidamentoEmail.ERRORI_IN_CONSEGNA;
			} else {
				if (lista.contains(StatoConsolidamentoEmail.AVVERTIMENTI_IN_CONSEGNA)) {
					return StatoConsolidamentoEmail.AVVERTIMENTI_IN_CONSEGNA;
				} else {
					if (lista.contains(StatoConsolidamentoEmail.NON_ACCETTATA)) {
						return StatoConsolidamentoEmail.NON_ACCETTATA;
					} else {
						if (lista.contains(StatoConsolidamentoEmail.ACCETTATA) && lista.contains(StatoConsolidamentoEmail.CONSEGNATA)) {
							return StatoConsolidamentoEmail.CONSEGNATA_PARZIALMENTE;
						} else {
							if (lista.contains(StatoConsolidamentoEmail.ACCETTATA)) {
								return StatoConsolidamentoEmail.ACCETTATA;
							} else if (lista.contains(StatoConsolidamentoEmail.CONSEGNATA)) {
								if (tutteRicevute)
									return StatoConsolidamentoEmail.CONSEGNATA;
								else
									return StatoConsolidamentoEmail.CONSEGNATA_PARZIALMENTE;
							} else {
								log.error("Impossibile identificare lo stato di consolidamento della email");
								return null;
							}
						}
					}
				}

			}
		}

	}

	private ReceiptBean effettuaLogicaAccettazione(List<TDestinatariEmailMgoBean> destinatari, TEmailMgoBean orig, StatoConsolidamentoEmail statoDestinatario,
			String idFruitore, String idCasella) throws Exception {
		ReceiptBean ret = null;
		if (destinatari != null) {
			ret = new ReceiptBean();
			List<TDestinatariEmailMgoBean> destDaAgg = new ArrayList<TDestinatariEmailMgoBean>();
			try {
				// AGGIORNO LO STATO DI TUTTI I DESTINATARI CHE ABBIANO UNO STATO UGUALE A NULL
				for (TDestinatariEmailMgoBean dest : destinatari) {
					if (dest.getStatoConsolidamento() == null) {
						dest.setStatoConsolidamento(statoDestinatario.getValue());
						destDaAgg.add(dest);
					}
				}
				ret.setDestinatari(destinatari);
				// ESEGUO UN UPDATE SULLA EMAIL ORIGINALE PER AGGIORNARE LO STATO DI CONSOLIDAMENTO
				// (LO RICALCOLO OGNI VOLTA)
				StatoConsolidamentoEmail stato = calcolaStatoDaDestinatari(destinatari, idFruitore);
				orig.setStatoConsolidamento(stato.getValue());
				ret.setMailOriginaria(orig);
				// se la mail orginaria era nella cartella standard.invio.pec.da_controllare
				// la spostiamo nella cartella standard.archiviata
				if (stato == StatoConsolidamentoEmail.CONSEGNATA) {
					DaoTRelEmailFolder daoRelEmailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
					List<TRelEmailFolderBean> relazioni = null;
					List<TFolderCaselleBean> folders = BeanUtils.getFolderFromEmail(orig, idCasella);
					for (TFolderCaselleBean folder : folders) {
						if (folder.getClassificazione().equalsIgnoreCase(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue())) {
							TFilterFetch<TRelEmailFolderBean> ff = new TFilterFetch<>();
							TRelEmailFolderBean filtro = new TRelEmailFolderBean();
							filtro.setIdEmail(orig.getIdEmail());
							ff.setFilter(filtro);
							relazioni = daoRelEmailFolder.search(ff).getData();
							// setto le relazioni da cancellare
							if (ListUtil.isNotEmpty(relazioni)) {
								ret.setFolderOriginarie(relazioni);
							}
							// creo le nuove relazioni da creare
							List<TRelEmailFolderBean> nuoveRelazioni = new ArrayList<>();
							TRelEmailFolderBean nuovaRel = new TRelEmailFolderBean();
							if (ListUtil.isNotEmpty(relazioni)) {
								nuovaRel.setIdEmail(relazioni.get(0).getIdEmail());
							}
							TFolderCaselleBean cartella = BeanUtils.ricavaEmailFolder(orig.getIdCasella(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_INVIO,
									idCasella);
							nuovaRel.setIdFolderCasella(cartella.getIdFolderCasella());
							nuoveRelazioni.add(nuovaRel);
							ret.setNuoveFolder(nuoveRelazioni);
						}
					}
				}
			} catch (Exception e) {
				log.error("Impossibile effettuare la logica per la ricevuta di accettazione", e);
				throw e;
			}
		}
		return ret;
	}

	private ReceiptBean effettuaLogicaConsegna(List<TDestinatariEmailMgoBean> destinatari, TEmailMgoBean orig, StatoConsolidamentoEmail statoDestinatario,
			MessageInfos infos, String mittenteRicevuta, String idFruitore, String idCasella) throws Exception {
		ReceiptBean ret = null;
		if (destinatari != null) {
			try {
				ret = aggiornaConsolidamento(destinatari, orig, statoDestinatario, mittenteRicevuta, idFruitore, idCasella);
			} catch (Exception e) {
				log.error("Impossibile effettuare la logica per la ricevuta di consegna", e);
				throw e;
			}
		}
		return ret;
	}

	private ReceiptBean effettuaLogicaRicevutaLettura(List<TDestinatariEmailMgoBean> destinatari, TEmailMgoBean orig,
			StatoConsolidamentoEmail statoDestinatario, MessageInfos infos, String mittenteRicevuta, String idCasella) throws Exception {
		ReceiptBean ret = null;
		if (destinatari != null) {
			log.debug("logiche di ricevuta per " + destinatari.size() + " destinatari, mail originale con idEmail: " + orig.getIdEmail()
					+ " ,mittenteRicevuta: " + mittenteRicevuta);
			try {
				ret = aggiornaConsolidamentoRicevutaLettura(destinatari, orig, statoDestinatario, mittenteRicevuta, idCasella);
			} catch (Exception e) {
				log.error("Impossibile effettuare la logica per la ricevuta di consegna", e);
				throw e;
			}
		}
		return ret;
	}

	private ReceiptBean effettuaLogicaDelivery(List<TDestinatariEmailMgoBean> destinatari, TEmailMgoBean orig, StatoConsolidamentoEmail statoDestinatario,
			String mittenteRicevuta, String idFruitore, String idCasella) throws Exception {
		ReceiptBean ret = null;
		if (destinatari != null) {
			try {
				ret = aggiornaConsolidamento(destinatari, orig, statoDestinatario, mittenteRicevuta, idFruitore, idCasella);
				DaoTRelEmailFolder daoRelEmailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
				// se la mail originaria è nella cartella standard.archiviata.invio
				// la spostiamo nella cartella standard.invio.peo.da_controllare
				List<TRelEmailFolderBean> relazioni = null;
				List<TFolderCaselleBean> folders = BeanUtils.getFolderFromEmail(orig, idCasella);
				for (TFolderCaselleBean folder : folders) {
					if (folder.getClassificazione().equalsIgnoreCase(FolderEmail.STANDARD_FOLDER_ARCHIVIATA_INVIO.getValue())) {
						TFilterFetch<TRelEmailFolderBean> ff = new TFilterFetch<>();
						TRelEmailFolderBean filtro = new TRelEmailFolderBean();
						filtro.setIdEmail(orig.getIdEmail());
						ff.setFilter(filtro);
						relazioni = daoRelEmailFolder.search(ff).getData();
						// setto le relazioni da cancellare
						if (ListUtil.isNotEmpty(relazioni)) {
							ret.setFolderOriginarie(relazioni);
						}
						// creo le nuove relazioni da creare
						List<TRelEmailFolderBean> nuoveRelazioni = new ArrayList<>();
						TRelEmailFolderBean nuovaRel = new TRelEmailFolderBean();
						if (ListUtil.isNotEmpty(relazioni)) {
							nuovaRel.setIdEmail(relazioni.get(0).getIdEmail());
						}
						TFolderCaselleBean cartella = BeanUtils.ricavaEmailFolder(orig.getIdCasella(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_INVIO, idCasella);
						nuovaRel.setIdFolderCasella(cartella.getIdFolderCasella());
						nuoveRelazioni.add(nuovaRel);
						ret.setNuoveFolder(nuoveRelazioni);

					}
				}
			} catch (Exception e) {
				log.error("Impossibile effettuare la logica per la ricevuta di delivery status notification", e);
				throw e;
			}
		}
		return ret;
	}

	private ReceiptBean aggiornaConsolidamento(List<TDestinatariEmailMgoBean> destinatari, TEmailMgoBean orig, StatoConsolidamentoEmail statoDestinatario,
			String mittenteRicevuta, String idFruitore, String idCasella) throws Exception {
		ReceiptBean recBean = new ReceiptBean();
		TDestinatariEmailMgoBean destToUpdate = null;
		// VERIFICO CHE IL MITTENTE COLLIMI CON UNO DEI DESTINATARI DELLA PEC ORIGINALE
		for (TDestinatariEmailMgoBean dest : destinatari) {
			if ((dest.getAccountDestinatario().trim()).equalsIgnoreCase(mittenteRicevuta.trim())) {
				dest.setStatoConsolidamento(statoDestinatario.getValue());
				destToUpdate = dest;
				break;
			}
		}
		// AGGIORNO LO STATO DI CONSOLIDAMENTO DELLA EMAIL ORIGINARIA
		StatoConsolidamentoEmail stato = calcolaStatoDaDestinatari(destinatari, idFruitore);
		if (stato != null) {
			orig.setStatoConsolidamento(stato.getValue());
		}
		recBean.setMailOriginaria(orig);
		List<TDestinatariEmailMgoBean> listaDest = new ArrayList<>();
		listaDest.add(destToUpdate);
		recBean.setDestinatari(listaDest);
		if (stato != null && stato == StatoConsolidamentoEmail.CONSEGNATA) {
			List<TRelEmailFolderBean> relazioni = null;
			DaoTRelEmailFolder daoRelEmailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
			List<TFolderCaselleBean> folders = BeanUtils.getFolderFromEmail(orig, idCasella);
			for (TFolderCaselleBean folder : folders) {
				if (folder.getClassificazione().equalsIgnoreCase(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue())) {
					TFilterFetch<TRelEmailFolderBean> ff = new TFilterFetch<>();
					TRelEmailFolderBean filtro = new TRelEmailFolderBean();
					filtro.setIdEmail(orig.getIdEmail());
					ff.setFilter(filtro);
					relazioni = daoRelEmailFolder.search(ff).getData();

					// setto le relazioni da cancellare
					if (ListUtil.isNotEmpty(relazioni)) {
						recBean.setFolderOriginarie(relazioni);
					}
					// creo le nuove relazioni da creare
					List<TRelEmailFolderBean> nuoveRelazioni = new ArrayList<>();
					TRelEmailFolderBean nuovaRel = new TRelEmailFolderBean();
					if (ListUtil.isNotEmpty(relazioni)) {
						nuovaRel.setIdEmail(relazioni.get(0).getIdEmail());
					}
					TFolderCaselleBean cartella = BeanUtils.ricavaEmailFolder(orig.getIdCasella(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_INVIO, idCasella);
					nuovaRel.setIdFolderCasella(cartella.getIdFolderCasella());
					nuoveRelazioni.add(nuovaRel);
					recBean.setNuoveFolder(nuoveRelazioni);
				}
			}
		}
		return recBean;
	}

	private ReceiptBean aggiornaConsolidamentoRicevutaLettura(List<TDestinatariEmailMgoBean> destinatari, TEmailMgoBean orig,
			StatoConsolidamentoEmail statoDestinatario, String mittenteRicevuta, String idCasella) throws Exception {
		ReceiptBean recBean = new ReceiptBean();
		TDestinatariEmailMgoBean destToUpdate = null;
		// VERIFICO CHE IL MITTENTE COLLIMI CON UNO DEI DESTINATARI DELLA PEC ORIGINALE
		for (TDestinatariEmailMgoBean dest : destinatari) {
			if (StringUtils.isNotBlank(mittenteRicevuta) && (dest.getAccountDestinatario().trim()).equalsIgnoreCase(mittenteRicevuta.trim())) {
				dest.setStatoConsolidamento(statoDestinatario.getValue());
				destToUpdate = dest;
				break;
			}
		}
		// AGGIORNO LO STATO DI CONSOLIDAMENTO DELLA EMAIL ORIGINARIA
		StatoConsolidamentoEmail stato = calcolaStatoDaDestinatari(destinatari, null);
		if (stato != null) {
			log.debug("stato consolidamento mail originaria: " + stato.getValue());
			orig.setStatoConsolidamento(stato.getValue());
		}
		recBean.setMailOriginaria(orig);
		List<TDestinatariEmailMgoBean> listaDest = new ArrayList<TDestinatariEmailMgoBean>();
		listaDest.add(destToUpdate);
		recBean.setDestinatari(listaDest);
		if (stato != null && stato == StatoConsolidamentoEmail.CONSEGNATA) {
			List<TRelEmailFolderBean> relazioni = null;
			List<TFolderCaselleBean> folders = BeanUtils.getFolderFromEmail(orig, idCasella);
			DaoTRelEmailFolder daoRelEmailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
			for (TFolderCaselleBean folder : folders) {
				if (folder.getClassificazione().equalsIgnoreCase(FolderEmail.STANDARD_FOLDER_INVIO_PEO.getValue())) {
					TFilterFetch<TRelEmailFolderBean> ff = new TFilterFetch<TRelEmailFolderBean>();
					TRelEmailFolderBean filtro = new TRelEmailFolderBean();
					filtro.setIdEmail(orig.getIdEmail());
					ff.setFilter(filtro);
					relazioni = daoRelEmailFolder.search(ff).getData();

					// setto le relazioni da cancellare
					if (relazioni != null && relazioni.size() != 0) {
						recBean.setFolderOriginarie(relazioni);
					}
					// creo le nuove relazioni da creare
					List<TRelEmailFolderBean> nuoveRelazioni = new ArrayList<TRelEmailFolderBean>();
					TRelEmailFolderBean nuovaRel = new TRelEmailFolderBean();
					if (relazioni != null && relazioni.size() != 0) {
						nuovaRel.setIdEmail(relazioni.get(0).getIdEmail());
					}
					TFolderCaselleBean cartella = BeanUtils.ricavaEmailFolder(orig.getIdCasella(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_INVIO, idCasella);
					nuovaRel.setIdFolderCasella(cartella.getIdFolderCasella());
					nuoveRelazioni.add(nuovaRel);
					recBean.setNuoveFolder(nuoveRelazioni);
				}
			}
		}
		return recBean;
	}

	private String ricavaIndirizzoDalCorpo(String body) {
		String address = null;
		log.debug("body della delivery: " + body);
		try {
			address = body.substring(body.indexOf("<") + 1, body.indexOf(">"));
		} catch (StringIndexOutOfBoundsException e) {
			// 06-02-2015: evito di mandare in errore il sw, ma non permetto alcun match della delivery
			address = " ";
		}
		return address;
	}

}
