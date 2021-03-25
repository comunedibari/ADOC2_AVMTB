package it.eng.auriga.ui.module.layout.server.passwordScaduta.datasource;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginCambiopasswordBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginResetpasswordloginBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginResetpasswordnologinBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailBean;
import it.eng.auriga.ui.module.layout.server.passwordScaduta.datasource.bean.PasswordScadutaBean;
import it.eng.auriga.ui.module.layout.server.passwordScaduta.datasource.bean.ResetPasswordBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.aurigasendmail.bean.AurigaDummyMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaResultSendMail;
import it.eng.client.DmpkLoginCambiopassword;
import it.eng.client.DmpkLoginResetpasswordlogin;
import it.eng.client.DmpkLoginResetpasswordnologin;
import it.eng.client.SimpleSendMailService;
import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "AurigaCambioPasswordDataSource")
public class AurigaCambioPasswordDataSource extends AbstractServiceDataSource<PasswordScadutaBean, PasswordScadutaBean> {

	private static Logger mLogger = Logger.getLogger(AurigaCambioPasswordDataSource.class);

	@Override
	public PasswordScadutaBean call(PasswordScadutaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();

		// inizializzo INPUT
		DmpkLoginCambiopasswordBean input = new DmpkLoginCambiopasswordBean();
		input.setCodidconnectiontokenin(token);
		input.setOldpasswordin(bean.getOldPassword());
		input.setNewpasswordin(bean.getNewPassword());
		input.setConfnewpasswordin(bean.getConfermaPassword());

		// eseguo il servizio
		DmpkLoginCambiopassword lDmpkLoginCambiopassword = new DmpkLoginCambiopassword();
		StoreResultBean<DmpkLoginCambiopasswordBean> output = lDmpkLoginCambiopassword.execute(getLocale(), loginBean, input);
		PasswordScadutaBean toReturn = new PasswordScadutaBean();
		toReturn.setChangeOk(false);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			toReturn.setChangeOk(true);
		}
		loginBean.setPasswordScaduta(false);
		AurigaUserUtil.setLoginInfo(getSession(), loginBean);
		return toReturn;
	}

	public ResetPasswordBean resetPasswordLogin(UtenteBean pUtenteBean, AurigaLoginBean ploginBean, HttpSession session) throws Exception {
		
		ResetPasswordBean toReturn = new ResetPasswordBean();
		AurigaLoginBean loginBean = new AurigaLoginBean();
		
		// Inserisco la lingua di default
		loginBean.setLinguaApplicazione(getLocale().getLanguage());
		loginBean.setSchema(ploginBean.getSchema());
		loginBean.setToken(ploginBean.getToken());

		// inizializzo INPUT
		DmpkLoginResetpasswordloginBean input = new DmpkLoginResetpasswordloginBean();
		input.setUsernamein(pUtenteBean.getUsername().toUpperCase());

		// eseguo il servizio
		DmpkLoginResetpasswordlogin lDmpkLoginResetpasswordlogin = new DmpkLoginResetpasswordlogin();
		StoreResultBean<DmpkLoginResetpasswordloginBean> output = lDmpkLoginResetpasswordlogin.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				toReturn.setChangeOk(false);
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			String newPassword = output.getResultBean().getNewpasswordout();
			toReturn.setNewPassword(newPassword);
			
			// MITTENTE
			String mittenteEmail = output.getResultBean().getEmailmittenteout();
			if(StringUtils.isNotBlank(mittenteEmail)) {
				String emailDestinatario = output.getResultBean().getEmailutenteout();
				if (emailDestinatario != null && !emailDestinatario.equalsIgnoreCase("")) {
					try {
					
						// OGGETTO
						String oggettoEmail = output.getResultBean().getOggettomailout();
	
						// BODY
						String bodyEmail = output.getResultBean().getBodymailout();
						
						// *******************************************************
						// Invio la mail
						// *******************************************************
						InvioMailBean lInvioMailBean = new InvioMailBean();
						lInvioMailBean.setMittente(mittenteEmail);
						lInvioMailBean.setDestinatari(emailDestinatario);
						lInvioMailBean.setBodyHtml(bodyEmail);
						lInvioMailBean.setOggetto(oggettoEmail);
						List<String> errorMessages = new ArrayList<String>();
						errorMessages = inviaEmail(lInvioMailBean);
						if (errorMessages.size() == 0) {
							toReturn.setChangeOk(true);
						} else {
							toReturn.setChangeOk(false);
							String listErrorMessages = "";
							for (int i = 0; i < errorMessages.size(); i++) {
								if (errorMessages.get(i) != null)
									listErrorMessages = listErrorMessages + errorMessages.get(i) + "\n";
							}
							toReturn.setErrorMessages(listErrorMessages);
						}
					} catch (Exception e) {
						throw new Exception(e.getMessage());
					}
				} else {
					toReturn.setChangeOk(false);
					toReturn.setErrorMessages("L'utente non ha una mail associata.");
				}
			} else {
				toReturn.setChangeOk(true);
			}
			
		}
		return toReturn;
	}
	
	public ResetPasswordBean resetPasswordLoginNoAuth(UtenteBean pUtenteBean, String schema, HttpSession session) throws Exception {
		
		ResetPasswordBean toReturn = new ResetPasswordBean();
		
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(schema);

		// inizializzo INPUT
		DmpkLoginResetpasswordnologinBean input = new DmpkLoginResetpasswordnologinBean();
		input.setUsernamein(pUtenteBean.getUsername().toUpperCase());

		// eseguo il servizio
		DmpkLoginResetpasswordnologin lDmpkLoginResetpasswordnologin = new DmpkLoginResetpasswordnologin();
		StoreResultBean<DmpkLoginResetpasswordnologinBean> output = lDmpkLoginResetpasswordnologin.execute(getLocale(), lSchemaBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				toReturn.setChangeOk(false);
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			String newPassword = output.getResultBean().getNewpasswordout();
			toReturn.setNewPassword(newPassword);			

			String emailDestinatario = output.getResultBean().getEmailutenteout();
			if (emailDestinatario != null && !emailDestinatario.equalsIgnoreCase("")) {
				try {
					
					// MITTENTE
					String mittenteEmail = output.getResultBean().getEmailmittenteout();
					
					// OGGETTO
					String oggettoEmail = output.getResultBean().getOggettomailout();
					  
					// BODY
					String bodyEmail = output.getResultBean().getBodymailout();
					
					// *******************************************************
					// Invio la mail
					// *******************************************************
					InvioMailBean lInvioMailBean = new InvioMailBean();
					lInvioMailBean.setMittente(mittenteEmail);
					lInvioMailBean.setDestinatari(emailDestinatario);
					lInvioMailBean.setBodyHtml(bodyEmail);
					lInvioMailBean.setOggetto(oggettoEmail);
					List<String> errorMessages = new ArrayList<String>();
					errorMessages = inviaEmail(lInvioMailBean);
					if (errorMessages.size() == 0) {
						toReturn.setChangeOk(true);
					} else {
						toReturn.setChangeOk(false);
						String listErrorMessages = "";
						for (int i = 0; i < errorMessages.size(); i++) {
							if (errorMessages.get(i) != null)
								listErrorMessages = listErrorMessages + errorMessages.get(i) + "\n";
						}
						toReturn.setErrorMessages(listErrorMessages);
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			} else {
				toReturn.setChangeOk(false);
				toReturn.setErrorMessages("L'utente non ha una mail associata.");
			}
			
		}
		return toReturn;
	}

	public ResetPasswordBean creaPasswordLogin(UtenteBean pUtenteBean, AurigaLoginBean ploginBean, HttpSession session) throws Exception {

		String urlAurigaWeb = null;
		
		ResetPasswordBean toReturn = new ResetPasswordBean();

		AurigaLoginBean loginBean = new AurigaLoginBean();
		// Inserisco la lingua di default
		loginBean.setLinguaApplicazione(getLocale().getLanguage());
		loginBean.setSchema(ploginBean.getSchema());
		loginBean.setToken(ploginBean.getToken());

		// inizializzo INPUT
		DmpkLoginResetpasswordloginBean input = new DmpkLoginResetpasswordloginBean();
		input.setUsernamein(pUtenteBean.getUsername().toUpperCase());

		// eseguo il servizio
		DmpkLoginResetpasswordlogin lDmpkLoginResetpasswordlogin = new DmpkLoginResetpasswordlogin();

		StoreResultBean<DmpkLoginResetpasswordloginBean> output = lDmpkLoginResetpasswordlogin.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				toReturn.setChangeOk(false);
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			String username = pUtenteBean.getUsername().toUpperCase();
			String emailDestinatario = output.getResultBean().getEmailutenteout();
			String newPassword = output.getResultBean().getNewpasswordout();
			if (ParametriDBUtil.getParametriDB(session) != null) {
				urlAurigaWeb = ParametriDBUtil.getParametroDB(session, "URL_AURIGAWEB");
			}
			if (emailDestinatario != null && !emailDestinatario.equalsIgnoreCase("")) {
				try {			
					
					List<String> errorMessages = new ArrayList<String>();
					String mittenteEmail = null;
					String oggettoEmail  = null;
					String bodyEmail     = null;
					String[] attributes1 = new String[3];
					
					// *******************************************************
					// Invio la mail con l'utenza
					// *******************************************************
					
					// MITTENTE
					if(isAttivoClienteCORC(session)) {
						mittenteEmail = "protoauriga@hermesrc.it";
					} else {
						mittenteEmail = MessageUtil.getValue(getLocale().getLanguage(), session, "crea_utenza_mittente_email_value");		
					}
					
					// OGGETTO
					oggettoEmail = MessageUtil.getValue(getLocale().getLanguage(), session, "crea_utenza_oggetto_email_value");
					
					// BODY
					attributes1[0] = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(new Date());
					attributes1[1] = username;
					attributes1[2] = urlAurigaWeb;
					bodyEmail = MessageUtil.getValue(getLocale().getLanguage(), session, "crea_utenza_body_email_value", attributes1);
					
					// Invio la mail con l'utenza
					InvioMailBean lInvioMail1Bean = new InvioMailBean();
					lInvioMail1Bean.setMittente(mittenteEmail);
					lInvioMail1Bean.setBodyHtml(bodyEmail);
					lInvioMail1Bean.setOggetto(oggettoEmail);
					lInvioMail1Bean.setDestinatari(emailDestinatario);
					
					errorMessages = inviaEmail(lInvioMail1Bean);
					
					if (errorMessages.size() == 0) {
												
						// *******************************************************
						// Invio la mail con la password
						// *******************************************************
						// MITTENTE
						if(isAttivoClienteCORC(session)) {
							mittenteEmail = "protoauriga@hermesrc.it";		
						} else {
							mittenteEmail = MessageUtil.getValue(getLocale().getLanguage(), session, "crea_password_mittente_email_value");	
						}
						
						// OGGETTO
						oggettoEmail = MessageUtil.getValue(getLocale().getLanguage(), session, "crea_password_oggetto_email_value");
						
						// BODY
						attributes1[0] = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(new Date());
						attributes1[1] = newPassword;
						attributes1[2] = urlAurigaWeb;
						bodyEmail = MessageUtil.getValue(getLocale().getLanguage(), session, "crea_password_body_email_value", attributes1);
						
						// Invio la mail con la password
						InvioMailBean lInvioMail2Bean = new InvioMailBean();
						lInvioMail2Bean.setMittente(mittenteEmail);
						lInvioMail2Bean.setBodyHtml(bodyEmail);
						lInvioMail2Bean.setOggetto(oggettoEmail);
						lInvioMail2Bean.setDestinatari(emailDestinatario);
						
						errorMessages = inviaEmail(lInvioMail2Bean);
						
						if (errorMessages.size() == 0) {
							toReturn.setChangeOk(true);	
						}
						else {
							toReturn.setChangeOk(false);
							String listErrorMessages = "";
							for (int i = 0; i < errorMessages.size(); i++) {
								if (errorMessages.get(i) != null)
									listErrorMessages = listErrorMessages + errorMessages.get(i) + "\n";
							}
							toReturn.setErrorMessages(listErrorMessages);
						}
					} else {
						toReturn.setChangeOk(false);
						String listErrorMessages = "";
						for (int i = 0; i < errorMessages.size(); i++) {
							if (errorMessages.get(i) != null)
								listErrorMessages = listErrorMessages + errorMessages.get(i) + "\n";
						}
						toReturn.setErrorMessages(listErrorMessages);
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			} else {
				toReturn.setChangeOk(false);
				toReturn.setErrorMessages("L'utente non ha una mail associata.");
			}
		}
		return toReturn;
	}

	// Invia la mail all'utente 
	public List<String> inviaEmail(InvioMailBean pInvioMailBean) throws Exception {

		// Inizializza i parametri di input
		AurigaDummyMailToSendBean lDummyMailToSendBean = new AurigaDummyMailToSendBean();

		// from
		lDummyMailToSendBean.setFrom(pInvioMailBean.getMittente());

		// to
		List<String> destinatari = new ArrayList<String>();
		String[] lStringDestinatari = pInvioMailBean.getDestinatari().split(";");
		destinatari = Arrays.asList(lStringDestinatari);
		lDummyMailToSendBean.setAddressTo(destinatari);
		// oggetto
		lDummyMailToSendBean.setSubject(pInvioMailBean.getOggetto());
		// testo
		lDummyMailToSendBean.setBody(pInvioMailBean.getBodyHtml());
		lDummyMailToSendBean.setHtml(true);

		// Invia la mail
		List<String> errorMessages = new ArrayList<String>();
		try {
			AurigaResultSendMail lResultSendMail = new SimpleSendMailService().sendfromconfigured(Locale.ITALY, lDummyMailToSendBean, "helpdesk");
			if (!lResultSendMail.isInviata()) {
				errorMessages = lResultSendMail.getErrori();
			}
		} catch (IllegalAccessException e) {
			mLogger.error("Errore nel recupero dell'output: " + e.getMessage());
			throw new StoreException(e.getMessage());
		} catch (InvocationTargetException e) {
			mLogger.error("Errore nel recupero dell'output: " + e.getMessage());
			throw new StoreException(e.getMessage());
		}
		return errorMessages;
	}
	
	public boolean isAttivoClienteCORC(HttpSession session) {
		String value = ParametriDBUtil.getParametroDB(session,"CLIENTE");
		return value != null && !"".equals(value) && "CORC".equals(value);
	}	
}