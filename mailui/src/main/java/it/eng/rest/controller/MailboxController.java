package it.eng.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.core.business.HibernateUtil;
import it.eng.testrest.MailboxQueryResultBean;
import it.eng.testrest.MailboxRestBean;
import it.eng.testrest.RestResult;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.bean.MailUiConfigurator;
import it.eng.utility.email.reader.config.MailBoxConfigKey;

@RestController
@RequestMapping("/service/mailbox")
public class MailboxController {


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadAll", method = RequestMethod.GET)
	public @ResponseBody MailboxQueryResultBean getAllMailbox() throws Exception {
		SubjectInitializer.init(null);
		MailboxQueryResultBean lMailboxQueryResultBean = new MailboxQueryResultBean();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Criteria criteria = session.createCriteria(Mailbox.class, "mbox").createAlias("mailboxAccount",
					"MailboxAccount");

			List<Mailbox> lList = criteria.addOrder(Order.asc("name")).list();

			List<MailboxRestBean> lListResult = new ArrayList<>();
			lListResult.add(new MailboxRestBean());
			for (Mailbox lMailbox : lList) {
				MailboxRestBean lMailboxRestBean = new MailboxRestBean();
				if (StringUtils.isBlank(lMailbox.getName())) {
					lMailbox.setName(lMailbox.getIdMailbox());
				}
				BeanUtilsBean2.getInstance().copyProperties(lMailboxRestBean, lMailbox);
				lMailboxRestBean.setIdAccount(lMailbox.getMailboxAccount().getIdAccount());
				lMailboxRestBean.setAccount(lMailbox.getMailboxAccount().getAccount());
				// Visualizzo anche lo stato nel nome
				lMailboxRestBean.setName(lMailbox.getName() + " - " + lMailbox.getStatus());
				lListResult.add(lMailboxRestBean);
			}
			lMailboxQueryResultBean = new MailboxQueryResultBean(lListResult);
			lMailboxQueryResultBean.setMessage("Load avvenuta con successo");
			lMailboxQueryResultBean.setResult(RestResult.OK);
		} catch (Exception e) {
			lMailboxQueryResultBean.setResult(RestResult.ERROR);
			lMailboxQueryResultBean.setMessage(e.getMessage());
			lMailboxQueryResultBean.setException(e);
		} finally {
			HibernateUtil.release(session);
		}
		return lMailboxQueryResultBean;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadAllMailBoxForConnectId", method = RequestMethod.GET)
	public @ResponseBody MailboxQueryResultBean getAllMailBoxForConnectId() throws Exception {
		SubjectInitializer.init(null);
		MailboxQueryResultBean lMailboxQueryResultBean = new MailboxQueryResultBean();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Criteria criteria = session.createCriteria(Mailbox.class, "mbox").createAlias("mailboxAccount",
					"MailboxAccount");

			// la mailbox deve essere associata al server
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxConfig.class, "config")
					.add(Restrictions.eq("config.id.configKey", MailBoxConfigKey.MAILBOX_MAILCONNECT_ID.keyname()))
					.add(Restrictions.eq("config.configValue", MailUiConfigurator.getMailConnectId()))
					.add(Property.forName("config.id.idMailbox").eqProperty("mbox.idMailbox"));

			criteria.add(
					Subqueries.exists(detachedCriteria.setProjection(Projections.property("config.id.configKey"))));

			List<Mailbox> lList = criteria.addOrder(Order.asc("name")).list();

			List<MailboxRestBean> lListResult = new ArrayList<>();
			for (Mailbox lMailbox : lList) {
				if (StringUtils.isBlank(lMailbox.getName())) {
					lMailbox.setName(lMailbox.getIdMailbox());
				}
				MailboxRestBean lMailboxRestBean = new MailboxRestBean();
				BeanUtilsBean2.getInstance().copyProperties(lMailboxRestBean, lMailbox);
				lMailboxRestBean.setIdAccount(lMailbox.getMailboxAccount().getIdAccount());
				lMailboxRestBean.setAccount(lMailbox.getMailboxAccount().getAccount());
				// Visualizzo anche lo stato nel nome
				lMailboxRestBean.setName(lMailbox.getName() + " - " + lMailbox.getStatus());
				lListResult.add(lMailboxRestBean);
			}
			lMailboxQueryResultBean = new MailboxQueryResultBean(lListResult);
			lMailboxQueryResultBean.setMessage("Load avvenuta con successo");
			lMailboxQueryResultBean.setResult(RestResult.OK);
		} catch (Exception e) {
			lMailboxQueryResultBean.setResult(RestResult.ERROR);
			lMailboxQueryResultBean.setMessage(e.getMessage());
			lMailboxQueryResultBean.setException(e);
		} finally {
			HibernateUtil.release(session);
		}
		return lMailboxQueryResultBean;
	}

}
