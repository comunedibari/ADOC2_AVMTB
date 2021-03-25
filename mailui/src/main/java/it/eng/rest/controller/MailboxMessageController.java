package it.eng.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.core.business.HibernateUtil;
import it.eng.testrest.DeleteMessageRestBean;
import it.eng.testrest.MailBoxMessageSearchBean;
import it.eng.testrest.MailboxMessageQueryResultBean;
import it.eng.testrest.MailboxMessageRestBean;
import it.eng.testrest.MailboxRestBean;
import it.eng.testrest.RestResult;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.reader.MessageStatus;
import it.eng.utility.email.util.synchro.SynchroMail;

@RestController
@RequestMapping("/service/mailboxmessage")
public class MailboxMessageController {

	private static Logger mLogger = LogManager.getLogger(MailboxMessageController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadAll", method = RequestMethod.GET)
	public @ResponseBody MailboxMessageQueryResultBean getAllMessage(MailBoxMessageSearchBean pMailBoxMessageSearchBean) throws Exception {
		SubjectInitializer.init(null);
		MailboxMessageQueryResultBean lMailboxMessageQueryResultBean = new MailboxMessageQueryResultBean();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			mLogger.debug("Start getAllMessage.");
			mLogger.info("Cancellazione da interfaccia della mailbox " + pMailBoxMessageSearchBean.getIdMailbox());

			DetachedCriteria lDetachedCriteria = DetachedCriteria.forClass(TEmailMgo.class).setProjection(Projections.property("messageId"));
			Criteria lCriteria = session.createCriteria(MailboxMessage.class);
			if (StringUtils.isNotEmpty(pMailBoxMessageSearchBean.getIdstatus())) {
				lCriteria.add(Restrictions.eq("status", pMailBoxMessageSearchBean.getIdstatus()));
			} else {
				lCriteria.add(Restrictions.not(Restrictions.eq("status", MessageStatus.MESSAGE_OPERATION_FINISH.status())));
			}
			if (StringUtils.isNotEmpty(pMailBoxMessageSearchBean.getIdMailbox())) {
				lCriteria.add(Restrictions.eq("mailbox.idMailbox", pMailBoxMessageSearchBean.getIdMailbox()));
			}
			List<MailboxMessage> lList = lCriteria.add(Subqueries.propertyNotIn("id.messageId", lDetachedCriteria)).list();
			List<MailboxMessageRestBean> lListResult = new ArrayList<>();
			for (MailboxMessage lMailboxMessage : lList) {
				MailboxMessageRestBean lMailboxMessageRestBean = new MailboxMessageRestBean();
				BeanUtilsBean2.getInstance().copyProperties(lMailboxMessageRestBean, lMailboxMessage);
				lMailboxMessageRestBean.setMessageId(lMailboxMessage.getId().getMessageId());
				lMailboxMessageRestBean.setIdMailbox(lMailboxMessage.getId().getIdMailbox());
				lListResult.add(lMailboxMessageRestBean);
			}
			lMailboxMessageQueryResultBean = new MailboxMessageQueryResultBean(lListResult);
			lMailboxMessageQueryResultBean.setMessage("Load avvenuta con successo");
			lMailboxMessageQueryResultBean.setResult(RestResult.OK);
		} catch (Exception e) {
			lMailboxMessageQueryResultBean.setResult(RestResult.ERROR);
			lMailboxMessageQueryResultBean.setMessage(e.getMessage());
			lMailboxMessageQueryResultBean.setException(e);
		} finally {
			HibernateUtil.release(session);
		}
		return lMailboxMessageQueryResultBean;
	}

	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
	public @ResponseBody DeleteMessageRestBean deleteMessages(@RequestBody MailboxRestBean pMailboxRestBean,
			@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "forceDelete", required = false) Boolean forceDelete,
			@RequestParam(value = "forceDeleteWithDelay", required = false) Boolean forceDeleteWithDelay,
			@RequestParam(value = "enableBackup", required = false) Boolean enableBackup) throws Exception {
		if (forceDelete == null) {
			forceDelete = false;
		}
		if (forceDeleteWithDelay == null) {
			forceDeleteWithDelay = false;
		}
		String folderBackup = null;
		if (enableBackup == null) {
			enableBackup = false;
		}
		if (enableBackup) {
			folderBackup = "archiviateAuriga";
		}
		SubjectInitializer.init(null);
		mLogger.debug("Elimino messaggi per " + pMailboxRestBean.getIdMailbox());
		DeleteMessageRestBean lDeleteMessageRestBean = new DeleteMessageRestBean();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			SynchroMail lSynchroMail = new SynchroMail();
			// di default nessun limite al numero di cancellazioni
			lSynchroMail.synchro(pMailboxRestBean.getIdMailbox(), forceDelete, forceDeleteWithDelay, limit, enableBackup, folderBackup); // cartella di backup provvisoria
			BeanUtilsBean2.getInstance().copyProperties(lDeleteMessageRestBean, lSynchroMail);
			String message = "";
			if (lDeleteMessageRestBean.getListIdMessageDeletedFromFolder() != null && !lDeleteMessageRestBean.getListIdMessageDeletedFromFolder().isEmpty()) {
				message = "Eliminati " + lDeleteMessageRestBean.getListIdMessageDeletedFromFolder().size() + " messaggi";
			} else {
				message = "Nessun messaggio eliminato";
			}

			if (lDeleteMessageRestBean.getListIdMessageBackuped() != null && !lDeleteMessageRestBean.getListIdMessageBackuped().isEmpty()) {
				message += System.lineSeparator() + "Copiati " + lDeleteMessageRestBean.getListIdMessageBackuped().size() + " messaggi nella folder di backup";
			} else {
				message += System.lineSeparator() + "Nessun messaggio copiato nella folder di backup";
			}
			lDeleteMessageRestBean.setMessage(message);
			lDeleteMessageRestBean.setResult(RestResult.OK);
		} catch (Exception e) {
			lDeleteMessageRestBean.setResult(RestResult.ERROR);
			lDeleteMessageRestBean.setMessage(e.getMessage());
			lDeleteMessageRestBean.setException(e);
		} finally {
			HibernateUtil.release(session);
		}
		return lDeleteMessageRestBean;
	}
}
