package it.eng.utility.email.operation.impl.archiveoperation.composer;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.utility.XmlFieldUtil;
import it.eng.utility.email.old.segnatura.AOO;
import it.eng.utility.email.old.segnatura.Amministrazione;
import it.eng.utility.email.old.segnatura.Denominazione;
import it.eng.utility.email.old.segnatura.Destinatario;
import it.eng.utility.email.old.segnatura.Destinazione;
import it.eng.utility.email.old.segnatura.Documento;
import it.eng.utility.email.old.segnatura.IndirizzoTelematico;
import it.eng.utility.email.old.segnatura.PerConoscenza;
import it.eng.utility.email.old.segnatura.Persona;
import it.eng.utility.email.operation.impl.archiveoperation.utils.MimeType;
import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;
import it.eng.utility.email.operation.impl.interoperation.InterBeanOld;
import it.eng.utility.email.process.exception.MailServerException;

/**
 * verifica la segnatura con vecchio xsd
 * 
 * @author jravagnan
 * 
 */
public class OldSegnaturaVerifier extends AbstractVerifier {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final Logger log = LogManager.getLogger(OldSegnaturaVerifier.class);

	@Override
	public InteropVerificationData verify(AbstractInterbean busta, String email, String codAOO, String codEnte) {
		log.debug("verifico la presenza di codAOO: " + codAOO + " e di codEnte: " + codEnte + " nella email");
		InteropVerificationData retData = new InteropVerificationData();
		boolean datiObbligatoriOk = false;
		boolean presenzaEmailCodiceAOOOk = false;
		if (busta.getIsdtdValid()) {
			datiObbligatoriOk = verificaSegnaturaCorrettezzaDatiObbligatori((InterBeanOld) busta);
			presenzaEmailCodiceAOOOk = verificaSegnaturaPresenzaEmaiCodiceAOO(((InterBeanOld) busta), email, codAOO, codEnte);
		}
		if (datiObbligatoriOk && presenzaEmailCodiceAOOOk) {
			retData.setIsConforme(true);
		} else {
			retData.setIsConforme(false);
			String mexErrore = "";
			if (!datiObbligatoriOk) {
				mexErrore = mexErrore.concat(DATI_OBBLIGATORI_KO);
			}
			if (!presenzaEmailCodiceAOOOk) {
				mexErrore = mexErrore.concat(PRESENZA_EMAIL_AOO_KO);
			}
			retData.setAvvertimento(mexErrore);
		}
		return retData;
	}

	/**
	 * verifica la presenza nella segnatura della email della mailbox o del codice dell'amministrazione relativa
	 * 
	 * @param email
	 * @param codAOO
	 * @param codEnte
	 * @param busta
	 * @return
	 * @throws MailServerException
	 */
	private boolean verificaSegnaturaPresenzaEmaiCodiceAOO(InterBeanOld busta, String email, String codAOO, String codEnte) {
		boolean segnaturaOk = false;
		List<Destinazione> to = busta.getSegnatura().getIntestazione().getDestinazione();
		List<PerConoscenza> cc = busta.getSegnatura().getIntestazione().getPerConoscenza();
		for (Destinazione dest : to) {
			String indirizzo = dest.getIndirizzoTelematico().getContent();
			if (indirizzo.contains(email)) {
				segnaturaOk = true;
				break;
			}
			if (!segnaturaOk) {
				List<Destinatario> destinatari = dest.getDestinatario();
				for (Destinatario uff : destinatari) {
					List<JAXBElement<?>> lista = uff.getContent();
					for (JAXBElement<?> ogg : lista) {
						if (ogg.getValue() instanceof Amministrazione) {
							if (((Amministrazione) ogg.getValue()).getCodiceAmministrazione() != null
									&& ((Amministrazione) ogg.getValue()).getCodiceAmministrazione().getContent() != null) {
								if (((Amministrazione) ogg.getValue()).getCodiceAmministrazione().getContent().contains(codAOO)
										|| ((Amministrazione) ogg.getValue()).getCodiceAmministrazione().getContent().contains(codEnte)) {
									segnaturaOk = true;
									break;
								}
							}
						} else {
							if (ogg.getValue() instanceof AOO) {
								if ((((AOO) ogg.getValue()).getCodiceAOO() !=null && ((AOO) ogg.getValue()).getCodiceAOO().getContent() != null
										&& (((AOO) ogg.getValue()).getCodiceAOO().getContent().contains(codAOO) || ((AOO) ogg.getValue())
										.getCodiceAOO().getContent().contains(codEnte)))) {
									segnaturaOk = true;
									break;
								}
							} else {
								if (ogg.getValue() instanceof Denominazione) {
									if (((Denominazione) ogg.getValue())!=null && ((Denominazione) ogg.getValue()).getContent() != null
											&& (((Denominazione) ogg.getValue()).getContent().contains(codAOO) || ((Denominazione) ogg
													.getValue()).getContent().contains(codEnte))) {
										segnaturaOk = true;
										break;
									}
								} else {
									if (ogg.getValue() instanceof Persona) {
										Object rife = ((Persona) ogg.getValue()).getRife();
										if (rife instanceof Denominazione) {
											if (((Denominazione) ogg.getValue())!=null && ((Denominazione) ogg.getValue()).getContent() != null
													&& (((Denominazione) ogg.getValue()).getContent().contains(codAOO) || ((Denominazione) ogg
															.getValue()).getContent().contains(codEnte))) {
												segnaturaOk = true;
												break;
											}
										}

									} else {
										if (ogg.getValue() instanceof IndirizzoTelematico) {
											String mail = ((IndirizzoTelematico) ogg.getValue()).getContent();
											if (mail.contains(email)) {
												segnaturaOk = true;
												break;
											}

										}
									}
								}
							}
						}

					}

				}
			}
			for (PerConoscenza pco : cc) {
				String ind = pco.getIndirizzoTelematico().getContent();
				if (ind.contains(email)) {
					segnaturaOk = true;
					break;
				}
				if (!segnaturaOk) {
					List<Destinatario> destinatari = pco.getDestinatario();
					for (Destinatario uff : destinatari) {
						List<JAXBElement<?>> lista = uff.getContent();
						for (JAXBElement<?> ogg : lista) {
							if (ogg.getValue() instanceof Amministrazione) {
								if (((Amministrazione) ogg.getValue()).getCodiceAmministrazione() != null && 
										((Amministrazione) ogg.getValue()).getCodiceAmministrazione().getContent() != null) {
									if (((Amministrazione) ogg.getValue()).getCodiceAmministrazione().getContent().contains(codAOO)
											|| ((Amministrazione) ogg.getValue()).getCodiceAmministrazione().getContent().contains(codEnte)) {
										segnaturaOk = true;
										break;
									}
								}
							} else {
								if (ogg.getValue() instanceof AOO) {
									if (((AOO) ogg.getValue()).getCodiceAOO()!=null && ((AOO) ogg.getValue()).getCodiceAOO().getContent()!=null &&
											(((AOO) ogg.getValue()).getCodiceAOO().getContent().contains(codAOO)
											|| ((AOO) ogg.getValue()).getCodiceAOO().getContent().contains(codEnte))) {
										segnaturaOk = true;
										break;
									}
								} else {
									if (ogg.getValue() instanceof Denominazione) {
										if (((Denominazione) ogg.getValue()).getContent() !=null && (((Denominazione) ogg.getValue()).getContent().contains(codAOO)
												|| ((Denominazione) ogg.getValue()).getContent().contains(codEnte))) {
											segnaturaOk = true;
											break;
										}
									} else {
										if (ogg.getValue() instanceof Persona) {
											Object rife = ((Persona) ogg.getValue()).getRife();
											if (rife instanceof Denominazione) {
												if (((Denominazione) ogg.getValue()).getContent()!=null && (((Denominazione) ogg.getValue()).getContent().contains(codAOO)
														|| ((Denominazione) ogg.getValue()).getContent().contains(codEnte))) {
													segnaturaOk = true;
													break;
												}
											}

										} else {
											if (ogg.getValue() instanceof IndirizzoTelematico) {
												String mail = ((IndirizzoTelematico) ogg.getValue()).getContent();
												if (mail.contains(email)) {
													segnaturaOk = true;
													break;
												}

											}
										}
									}
								}
							}

						}

					}
				}
			}
		}
		return segnaturaOk;

	}

	/**
	 * verifica la correttezza dei dati obbligatori della segnatura
	 * 
	 * @param busta
	 * @return
	 * @throws MailServerException
	 */
	private boolean verificaSegnaturaCorrettezzaDatiObbligatori(InterBeanOld busta) {
		Documento doc = busta.getSegnatura().getDescrizione().getDocumento();
		if (doc != null && doc.getTipoMIME() != null) {
			String mimeType = XmlFieldUtil.cleanField(doc.getTipoMIME());
			if (!mimeType.equalsIgnoreCase(MimeType.CARTACEO.getValue()) && !mimeType.equalsIgnoreCase(MimeType.TELEMATICO.getValue())
					&& !mimeType.equalsIgnoreCase(MimeType.MIME.getValue())) {
				return false;
			}
		}
		String numeroRegistrazione = busta.getSegnatura().getIntestazione().getIdentificatore().getNumeroRegistrazione().getContent();
		String dataRegistrazione = busta.getSegnatura().getIntestazione().getIdentificatore().getDataRegistrazione().getContent();
		if (!XmlFieldUtil.checkIfNumber(numeroRegistrazione) || XmlFieldUtil.cleanField(numeroRegistrazione).length() > 7)
			return false;
		if (!XmlFieldUtil.checkIfDate(dataRegistrazione, DATE_FORMAT))
			return false;
		// TODO: verificare anche l'impronta del file (collocazione telematica)
		return true;
	}

}
