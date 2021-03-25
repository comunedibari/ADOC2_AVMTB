package it.eng.auriga.database.store.dmpk_core.store;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetprofilofolderxcontainerBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.GetprofilofolderxcontainerImpl;
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
@Service(name="DmpkCoreGetprofilofolderxcontainer")
public class Getprofilofolderxcontainer {
		
	private DmpkCoreGetprofilofolderxcontainerBean bean;
		  
	public void setBean(DmpkCoreGetprofilofolderxcontainerBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkCoreGetprofilofolderxcontainerBean> execute(AurigaLoginBean lLoginBean, DmpkCoreGetprofilofolderxcontainerBean pBean) throws Exception{
	   final GetprofilofolderxcontainerImpl lGetprofilofolderxcontainer = new GetprofilofolderxcontainerImpl();
	   setBean(pBean);
	   lGetprofilofolderxcontainer.setBean(bean);
       setCommit();
       setToken(lLoginBean);
       setIdUserLavoro(lLoginBean);
	   SubjectBean subject =  SubjectUtil.subject.get();
	   subject.setIdDominio(lLoginBean.getSchema());
	   subject.setUuidtransaction(lLoginBean.getUuid());
	   SubjectUtil.subject.set(subject);    
	   Session session = null;
		try {
			LoginService lLoginService = new LoginService();
			lLoginService.login(lLoginBean);
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lGetprofilofolderxcontainer.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkCoreGetprofilofolderxcontainerBean> result = new StoreResultBean<DmpkCoreGetprofilofolderxcontainerBean>();
			AnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
			Marktokenusage lMarktokenusage = new Marktokenusage();
			DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
			if (lLoginBean.getToken()!=null)
				lMarktokenusageBean.setCodidconnectiontokenin(lLoginBean.getToken());
			lMarktokenusageBean.setFlgautocommitin(1);
			lMarktokenusage.execute(lLoginBean, lMarktokenusageBean);
			return result;
		}catch(Exception e){
			if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().equals("Chiusura forzata")) throw new Exception("Chiusura forzata");
			else throw e;
		}finally{
			HibernateUtil.release(session);
		}
		
	}
	
	protected void setCommit() {
	}
	protected void setToken(AurigaLoginBean pLoginBean) {
		bean.setCodidconnectiontokenin(pLoginBean.getToken());
	}
	protected void setIdUserLavoro(AurigaLoginBean pLoginBean) {
		if (StringUtils.isNotEmpty(pLoginBean.getIdUserLavoro())){
			BigDecimal lBigDecimal = new BigDecimal(pLoginBean.getIdUserLavoro());
			bean.setIduserlavoroin(lBigDecimal);
		}
		
	} 
}