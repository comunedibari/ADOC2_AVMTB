package it.eng.rest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageId;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperationId;
import it.eng.aurigamailbusiness.database.mail.MailboxOperation;
import it.eng.core.business.HibernateUtil;
import it.eng.testrest.RetrieveAndInsertResultBean;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.ProcessOperationFlow;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.util.xml.XmlUtil;

@RestController
@RequestMapping("/service/retrievemailboxmessage")
public class RetrieveAndInsertMessages {

	@RequestMapping(value = "/retrieve/{idMailbox}", method = RequestMethod.GET)
	@ResponseBody
	public RetrieveAndInsertResultBean retrieve(@PathVariable("idMailbox") String idMailbox) {
		RetrieveAndInsertResultBean lRetrieveAndInsertResultBean = new RetrieveAndInsertResultBean();
		SubjectInitializer.init(null);
		try {
			List<String[]> lListMessaggi = recuperaMessaggi(idMailbox);
			List<String> mailInserite = new ArrayList<>();
			List<String> mailNonInserite = new ArrayList<>();
			for (String[] lStrings : lListMessaggi) {
				boolean result = inserisciMessaggio(lStrings, idMailbox);
				if (result) {
					mailInserite.add(lStrings[0]);
				} else {
					mailNonInserite.add(lStrings[0]);
				}
			}
			lRetrieveAndInsertResultBean.setMailInserite(mailInserite);
			lRetrieveAndInsertResultBean.setMailNonInserite(mailNonInserite);
			return lRetrieveAndInsertResultBean;
		} catch (Exception e) {
			lRetrieveAndInsertResultBean.setError(e.getMessage());
			return lRetrieveAndInsertResultBean;
		}
	}

	private boolean inserisciMessaggio(String[] lStrings, String idMailbox) throws Exception {
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateUtil.begin();
			Mailbox lMailbox = (Mailbox) hibernateSession.get(Mailbox.class, idMailbox);
			ProcessOperationFlow flow = (ProcessOperationFlow) XmlUtil.xmlToObject(lMailbox.getProcessFlow());
			String lStrOperationId = flow.getStart().getOperationid();
			MailboxOperation lMailboxOperation = (MailboxOperation) hibernateSession.createCriteria(MailboxOperation.class)
					.add(Restrictions.eq("operationName", "StartOperation")).add(Restrictions.eq("idMailboxOperation", lStrOperationId)).list().get(0);
			if (FactoryMailBusiness.getInstance().isMimeMessageElaborate(lStrings[0], idMailbox)) {
				throw new Exception("Mail già nel sistema");
			}
			Transaction lTransaction = hibernateSession.beginTransaction();
			MailboxMessage lMailboxMessage = new MailboxMessage();
			MailboxMessageId lMailboxMessageId = new MailboxMessageId();
			lMailboxMessageId.setIdMailbox(idMailbox);
			lMailboxMessageId.setMessageId(lStrings[0]);
			lMailboxMessage.setId(lMailboxMessageId);
			if (hibernateSession.createCriteria(MailboxMessage.class).add(Restrictions.idEq(lMailboxMessageId)).uniqueResult() != null) {
				throw new Exception("Mail già nel sistema");
			}
			lMailboxMessage.setDateDischarged(new Date());
			lMailboxMessage.setStatus(MessageStatus.MESSAGE_FORCE_OPERATION.status());

			lMailboxMessage.setUrlMime(lStrings[1]);

			MailboxMessageOperationId lMailboxMessageOperationId = new MailboxMessageOperationId();
			lMailboxMessageOperationId.setIdMailbox(idMailbox);
			lMailboxMessageOperationId.setIdMailboxoperation(lMailboxOperation.getIdMailboxOperation());
			lMailboxMessageOperationId.setMessageId(lMailboxMessageId.getMessageId());
			MailboxMessageOperation lMailboxMessageOperation = new MailboxMessageOperation();
			lMailboxMessageOperation.setMailboxMessage(lMailboxMessage);
			lMailboxMessageOperation.setOperationTryNum((short) 1);
			lMailboxMessageOperation.setOperationStatus("error");
			lMailboxMessageOperation.setOperationValue("operation_value");
			lMailboxMessageOperation.setDateOperation(new Date());
			lMailboxMessageOperation.setId(lMailboxMessageOperationId);
			lMailboxMessageOperation.setMailboxOperation(lMailboxOperation);
			hibernateSession.save(lMailboxMessage);
			hibernateSession.save(lMailboxMessageOperation);
			hibernateSession.flush();
			lTransaction.commit();
			return true;
		} catch (Throwable e) {
			return false;
		} finally {
			HibernateUtil.release(hibernateSession);
		}

	}

	@SuppressWarnings("unchecked")
	private List<String[]> recuperaMessaggi(String pStrIdMailbox) throws Exception {
		Session hibernateSession = HibernateUtil.begin();
		List<String[]> lListResult = new ArrayList<>();
		try {
			List<Object[]> lListQuery = hibernateSession.createSQLQuery("select MESSAGE_ID, URL_MIME from MAILBOX_MESSAGE_2 WHERE ID_MAILBOX = ?")
					.setString(0, pStrIdMailbox).list();
			for (Object[] lObjects : lListQuery) {
				String[] lStrings = new String[2];
				lStrings[0] = (String) lObjects[0];
				lStrings[1] = (String) lObjects[1];
				lListResult.add(lStrings);
			}
		} finally {
			HibernateUtil.release(hibernateSession);
		}
		return lListResult;
	}
}
