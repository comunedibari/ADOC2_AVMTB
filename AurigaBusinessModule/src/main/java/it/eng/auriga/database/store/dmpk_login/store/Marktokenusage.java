package it.eng.auriga.database.store.dmpk_login.store;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.impl.MarktokenusageImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.business.HibernateUtil;
import it.eng.storeutil.AnalyzeResult;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.apache.commons.lang3.StringUtils;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.Marktokenusage;
/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
@Service(name="DmpkLoginMarktokenusage")
public class Marktokenusage {
		
	private DmpkLoginMarktokenusageBean bean;
		  
	public void setBean(DmpkLoginMarktokenusageBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkLoginMarktokenusageBean> execute(AurigaLoginBean lLoginBean, DmpkLoginMarktokenusageBean pBean) throws Exception{
	   final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
	   setBean(pBean);
	   lMarktokenusage.setBean(bean);
       setCommit();
	   SubjectBean subject =  SubjectUtil.subject.get();
	   subject.setIdDominio(lLoginBean.getSchema());
	   subject.setUuidtransaction(lLoginBean.getUuid());
	   SubjectUtil.subject.set(subject);    
	   Session session = null;
		try {
			LoginService lLoginService = new LoginService();
			lLoginService.login(lLoginBean);
			session = HibernateUtil.getCurrentSessionThread();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lMarktokenusage.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkLoginMarktokenusageBean> result = new StoreResultBean<DmpkLoginMarktokenusageBean>();
			AnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
			return result;
		}catch(Exception e){
			if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().equals("Chiusura forzata")) throw new Exception("Chiusura forzata");
			else throw e;
		}finally{
//			HibernateUtil.release(session);
		}
		
	}
	
	protected void setCommit() {
	java.lang.Integer lflgautocommitin = bean.getFlgautocommitin();
		if (lflgautocommitin == null){
			bean.setFlgautocommitin(1);
		}
	}
	protected void setToken(AurigaLoginBean pLoginBean) {
		bean.setCodidconnectiontokenin(pLoginBean.getToken());
	}
	protected void setIdUserLavoro(AurigaLoginBean pLoginBean) {
	} 
}