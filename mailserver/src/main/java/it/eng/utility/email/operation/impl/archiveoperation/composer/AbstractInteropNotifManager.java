package it.eng.utility.email.operation.impl.archiveoperation.composer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.InterOpBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.database.utility.send.FolderEmail;
import it.eng.aurigamailbusiness.database.utility.send.StatoConsolidamentoEmail;
import it.eng.utility.email.operation.impl.archiveoperation.utils.BeanUtils;
import it.eng.utility.email.operation.impl.archiveoperation.utils.Utilities;
import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;
import it.eng.utility.email.operation.impl.interoperation.InteroperabilitaType;
import it.eng.utility.email.process.exception.MailServerException;

public abstract class AbstractInteropNotifManager {

	private Logger log = LogManager.getLogger(AbstractInteropNotifManager.class);

	private static final String STANDARD_PREFIX = "standard.";

	protected static final String PROTOCOLLO_GENERALE_VALUE = "Prot. Gen.";

	protected static final String PROTOCOLLO_GENERALE_ACRONIMO = "PG";

	protected static final String COD_AMMINISTRAZIONE_PATTERN = "^[a-zA-Z0-9-]*$";

	protected static final Integer COD_AMMINISTRAZIONE_MAX_LENGTH = 8;

	protected static final String COD_AOO_PATTERN = "^[a-zA-Z0-9-]*$";

	protected static final Integer COD_AOO_MAX_LENGTH = 8;

	protected static final Integer NUMERO_REGISTRAZIONE_MAX_LENGTH = 7;

	protected Utilities util = new Utilities();

	public abstract EmailBean notifInteropManage(AbstractInterbean busta, EmailBean bean, String idEmail, String idDestinatarioEmail, String idCasella)
			throws MailServerException;

	public InterOpBean eseguiLogicheCambioFolder(InterOpBean interopBean, InteroperabilitaType type, String idAccount, String idCasella) throws Exception {
		if (interopBean != null) {
			List<TEmailMgoBean> mailAgganciate = interopBean.getMailAgganciate();
			if (mailAgganciate != null) {
				for (TEmailMgoBean mail : mailAgganciate) {
					if (mail != null) {
						log.debug("IdEmail agganciata: " + mail.getIdEmail());
					}
				}
			}
			List<TRelEmailFolderBean> relDaInserire = new ArrayList<TRelEmailFolderBean>();
			List<TRelEmailFolderBean> relOriginali = new ArrayList<TRelEmailFolderBean>();
			if (mailAgganciate != null && type == InteroperabilitaType.CONFERMA_RICEZIONE) {
				for (TEmailMgoBean mail : mailAgganciate) {
					if (mail.getStatoConsolidamento() != null && mail.getStatoConsolidamento().equalsIgnoreCase(StatoConsolidamentoEmail.CONSEGNATA.getValue())
							&& mail.getFlgRichConferma()) {
						List<TDestinatariEmailMgoBean> destins = BeanUtils.getListaDestinatari(mail, idCasella);
						boolean ok = true;
						if (destins != null) {
							for (TDestinatariEmailMgoBean des : destins) {
								if (des.getFlgNotifInteropConf() && des.getIdVoceRubricaDest() != null) {
									List<TRubricaEmailBean> rubricaVoci = BeanUtils.getVoceRubricaFromDestinatario(des, idCasella);
									for (TRubricaEmailBean voce : rubricaVoci) {
										if (voce.getCodAoo() == null) {
											ok = false;
											break;
										}
									}
									if (!ok) {
										break;
									}
								}
							}
						}
						if (ok) {
							List<TFolderCaselleBean> folders = BeanUtils.getFolderFromEmail(mail, idCasella);
							for (TFolderCaselleBean folder : folders) {
								if (folder.getClassificazione().equalsIgnoreCase(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue())) {
									List<TRelEmailFolderBean> relazioniOriginarie = BeanUtils.getRelEmailFolderFromEmail(mail, idCasella);
									if (relazioniOriginarie != null) {
										relOriginali.addAll(relazioniOriginarie);
									}
									// nuova relazione con la folder
									TRelEmailFolderBean newRelazione = new TRelEmailFolderBean();
									newRelazione.setIdEmail(mail.getIdEmail());
									String idFolder = BeanUtils.ricavaEmailFolder(idAccount, FolderEmail.STANDARD_FOLDER_ARCHIVIATA_INVIO, idCasella)
											.getIdFolderCasella();
									newRelazione.setIdFolderCasella(idFolder);
									relDaInserire.add(newRelazione);
								}
							}
						}

					}
				}
			} else {
				if (mailAgganciate != null) {
					for (TEmailMgoBean mail : mailAgganciate) {
						List<TFolderCaselleBean> folders = BeanUtils.getFolderFromEmail(mail, idCasella);
						for (TFolderCaselleBean folder : folders) {
							if (folder.getClassificazione().startsWith(STANDARD_PREFIX)) {
								List<TRelEmailFolderBean> relazioniOriginarie = BeanUtils.getRelEmailFolderFromEmail(mail, idCasella);
								// memorizzo le precedenti relazioni per poi poterle rimuovere
								if (relazioniOriginarie != null) {
									relOriginali.addAll(relazioniOriginarie);
								}
								// nuova relazione con la folder
								TRelEmailFolderBean newRelazione = new TRelEmailFolderBean();
								newRelazione.setIdEmail(mail.getIdEmail());
								String idFolder = BeanUtils.ricavaEmailFolder(idAccount, FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE, idCasella)
										.getIdFolderCasella();
								newRelazione.setIdFolderCasella(idFolder);
								relDaInserire.add(newRelazione);
							}
						}
					}
				}
			}
			// memorizzo le precedenti relazioni per poi poterle rimuovere
			interopBean.setCaselleMailOriginarie(relOriginali);
			interopBean.setCaselleMailAgganciate(relDaInserire);
		}
		return interopBean;
	}
}
