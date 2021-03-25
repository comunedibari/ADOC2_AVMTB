package it.eng.utility.email.operation.impl.notificationoperation;

import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccount;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.client.AurigaMailService;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.service.serialization.SerializerRepository;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.ListUtil;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.operation.impl.archiveoperation.MessageArchiveBean;
import it.eng.utility.email.operation.impl.archiveoperation.utils.AccountUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.ExceptionUtils;
import org.hibernate.Hibernate;
import org.python.jline.internal.Log;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Invoca la stored di invio email di notifica dopo che la mail � stata scaricata da mailconnect ed assegnata ad una UO.
 * L'invio della mail � gestito dalla stored PTPK_WEB_MAIL.POPOLAINVIAMAILAUTO_PEC_CONN
 * @author leone
 *
 */
@MessageOperation(
		description="Effettua l'invio della mail di notifica alla uo assegnataria",
		name="AssignmentNotificationOperation"
)
public class AssignmentNotificationOperation extends AbstractMessageOperation<AssignmentNotificationBean> 
{
	private static final String className = AssignmentNotificationOperation.class.getName();
	private static Logger logger = Logger.getLogger( className );
	
	@ConfigOperation(title = "Account mittente che deve configurato in auriga", name = "assignmentnotificationoperation.accountmittente", description = "Indica l'account mittente da cui mandare la notifica")
	private String accountmittente = "";
	
	@ConfigOperation(title = "Oggetto della mail di notifica", name = "assignmentnotificationoperation.oggettonotifica", description = "Oggetto dell'invio della notifica")
	private String oggettonotifica = "[AURIGA] Notifica automatica di assegnazione e-mail in arrivo";
	
	public AssignmentNotificationOperation() 
	{
		
	}
	
	@Override
	public AssignmentNotificationBean elaborate(MessageInfos message) throws Exception 
	{
		String prefissoLog = className + ".elaborate: ";
		
		logger.debug( prefissoLog + "<INIZIO>" );
		
		AssignmentNotificationBean assignmentNotificationBean = new AssignmentNotificationBean();
		try
		{
			// Per recuperare l'id email accedo al bean risultato della EGrammataMessageOperation che viene eseguita nel processo prima di questa
			List<Object> listaOperationBean = message.getOpResultWithStartName( "MessageArchiveOperation" );
			if ( listaOperationBean != null && !listaOperationBean.isEmpty() && ( (MessageArchiveBean)listaOperationBean.get( 0 ) ).getIsok() )
			{
				String[] emailInfo = getEmailInfo((getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : getMailreceiverPop3().getConfigbean().getMailboxid()), message);
				if ( message.isRicevuta() )
				{
					String resultMessage = "la mail con MessageId=" + message.getHeaderinfo().getMessageid()+ ", si riferisce ad una ricevuta pertanto non eseguo alcuna notificata di assegnazione";
					assignmentNotificationBean.setResultMessage( resultMessage );
					assignmentNotificationBean.setIsok( true );
				}
				else if ( StringUtils.isNotBlank( emailInfo[0] ) )
				{
					try 
					{ 
						List<String> destinatari = FactoryMailBusiness.getInstance().getNotificationEmails((getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : 
							getMailreceiverPop3().getConfigbean().getMailboxid()) , emailInfo[0], accountmittente);
						if (destinatari != null && !destinatari.isEmpty()) {
							// non ok
							if (!sendMail(accountmittente, destinatari, oggettonotifica, getMailBody(emailInfo[0], emailInfo[1], emailInfo[2], emailInfo[3], emailInfo[4])) ) 
							{
								String resultMessage = "MessageId=" + message.getHeaderinfo().getMessageid() + ": notifica a "+ destinatari +" NON inviata correttamente";
								assignmentNotificationBean.setResultMessage( resultMessage );
								assignmentNotificationBean.setIsok( false );
								throw new Exception( resultMessage );
							}
							
							// ok
							String resultMessage = "MessageId=" + message.getHeaderinfo().getMessageid() + ": notifica a "+ destinatari +" inviata correttamente";
							assignmentNotificationBean.setResultMessage( resultMessage );
							assignmentNotificationBean.setIsok( true );
						} else {
							String resultMessage = "MessageId=" + message.getHeaderinfo().getMessageid() + ": non deve essere inviata alcuna notifica";
							assignmentNotificationBean.setResultMessage( resultMessage );
							assignmentNotificationBean.setIsok( true );
						}
						
					} catch (Exception e) {
						String resultMessage = "MessageId=" + message.getHeaderinfo().getMessageid() + " in errore: " + e.getLocalizedMessage();
						assignmentNotificationBean.setResultMessage( resultMessage );
						assignmentNotificationBean.setIsok( false );
						throw new Exception(ExceptionUtils.getFullStackTrace(e));
					}
					
				}
				else
				{
					String resultMessage = "idEmail non valorizzato";
					assignmentNotificationBean.setResultMessage( resultMessage );
					assignmentNotificationBean.setIsok( false );
					throw new Exception( resultMessage );
				}
			}
			else
			{
				String resultMessage = "Errore nel recupero del bean risultato relativo alla MessageArchiveOperation, oppure la MessageArchiveOpreation è terminata con errore";
				assignmentNotificationBean.setResultMessage( resultMessage );
				assignmentNotificationBean.setIsok( false );
				throw new Exception( resultMessage );
			}
		} 
		catch(Exception e) 
		{
	        assignmentNotificationBean.setIsok( false );
			throw e;
		}
		finally
		{
			
		}
		
		logger.debug( prefissoLog + "<FINE>" );
		
		return assignmentNotificationBean;
	}
	/**
	 * 
	 * @param idCasella
	 * @param message
	 * @return casella, messageId, dataInvio, mittente
	 * @throws Exception
	 */
	private String[] getEmailInfo(String idCasella, MessageInfos message) throws Exception {
		String[] result = new String[5];
		String idAccount = AccountUtils.retrieveIdAccount(idCasella);
		
		DaoMailboxAccount daoMailboxAccount = (DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class);
		TFilterFetch<MailboxAccountBean> filterFetchAccount = new TFilterFetch<>();
		MailboxAccountBean beanFiltroAccount = new MailboxAccountBean();
		beanFiltroAccount.setIdAccount(idAccount);
		filterFetchAccount.setFilter(beanFiltroAccount);
		List<MailboxAccountBean> listaAccounts = daoMailboxAccount.search(filterFetchAccount).getData();
		
		if (ListUtil.isNotEmpty(listaAccounts)) {
			result[0] = listaAccounts.get(0).getAccount();
		} else {
			throw new Exception("idCasella["+idCasella+"] non associato ad alcuna casella.");
		}
		
		DaoTEmailMgo daoTEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<>();
		TEmailMgoBean beanFiltro = new TEmailMgoBean();
		beanFiltro.setIdCasella(idAccount);
		beanFiltro.setMessageId(message.getHeaderinfo().getMessageid());
		filterFetch.setFilter(beanFiltro);
		List<TEmailMgoBean> listaEmails = daoTEmail.search(filterFetch).getData();
		
		if (ListUtil.isNotEmpty(listaEmails)) {
			result[1] = listaEmails.get(0).getMessageId();
			Date dataInvio = listaEmails.get(0).getTsInvio();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			result[2] = sdf.format(dataInvio);
			result[3] = listaEmails.get(0).getAccountMittente();
			result[4] = listaEmails.get(0).getOggetto();
		} else 
			throw new Exception("Impossibile recuperare le informazioni della mail.");
		
		return result;
	}
	
	
	/**
	 * costruisce il body della mail, per ora non modificabile
	 * 
	 * @param idEmail
	 * @return
	 */
	private String getMailBody(String casella, String messageId, String dataInvio, String mittente, String oggetto) {
		String body = "Vi è stata assegnata una e-mail in arrivo pervenuta alla casella <br/>"
				+ casella + " <br/>"
				+ "Inviata in data: " + dataInvio + "<br/>"
				+ "Da " + mittente + "<br/>"
				+ "Ad oggetto: " + oggetto + "<br/>"
				+ "Con ID messaggio : " + messageId + "<br/>"
				+ "Tale e-mail è consultabile sia dal Cruscotto gestione e-mail che da Lista di Lavoro --> Posta Elettronica. <br/>"
				+ "La mail sarà trattata dagli operatori abilitati, che provvederanno ad effettuare la registrazione a protocollo. Trattandosi di documento originale informatico, non sarà trasmesso in forma cartacea e potrà essere consultato sulla scrivania di lavoro.";
		return body;		
	}
	
	
	private boolean sendMail(String accountMittente, List<String> destinatariIn, String oggetto, String body) throws Exception {
		EmailSentReferenceBean output = null;
		SenderBean senderBean = new SenderBean();
		senderBean.setIdUtenteModPec("");
		senderBean.setFlgInvioSeparato(true);
		senderBean.setAccount(accountMittente);
		List<String> destinatari = new ArrayList<String>();
		destinatari.addAll(destinatariIn);
		senderBean.setAddressTo(destinatari);
		senderBean.setAddressFrom(accountMittente);
		senderBean.setSubject(oggetto);
		senderBean.setBody(body);
		senderBean.setIsHtml(Boolean.TRUE);
		output = AurigaMailService.getMailSenderService().send(
					new Locale("it", "IT"),
					senderBean);
		if (output.getSentMails() != null &&
				!output.getSentMails().isEmpty()) {
			return output.getSentMails().get(0).getIsSent();
		} else {
			String error = "AssignmentNotificationOperation.sendMail: impossibile inviare la notifica a " + destinatariIn + " per " +  output.getSentMails().get(0).getMotivoEccezione();
			Log.error(error);
			throw new Exception(error);
		}

	}

	public String getAccountmittente() {
		return accountmittente;
	}

	public void setAccountmittente(String accountmittente) {
		this.accountmittente = accountmittente;
	}

	public String getOggettonotifica() {
		return oggettonotifica;
	}

	public void setOggettonotifica(String oggettonotifica) {
		this.oggettonotifica = oggettonotifica;
	}
	
	
}