package it.eng.auriga.database.store.dmpk_utility.store;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityTo_date_datetimexmltagBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.To_date_datetimexmltagImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.business.HibernateUtil;
import it.eng.storeutil.AnalyzeResult;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import it.eng.auriga.database.store.bean.SchemaBean;
/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
@Service(name="DmpkUtilityTo_date_datetimexmltag")
public class To_date_datetimexmltag {
		
	private DmpkUtilityTo_date_datetimexmltagBean bean;
		  
	public void setBean(DmpkUtilityTo_date_datetimexmltagBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkUtilityTo_date_datetimexmltagBean> execute(SchemaBean lSchemaBean, DmpkUtilityTo_date_datetimexmltagBean pBean) throws Exception{
	   final To_date_datetimexmltagImpl lTo_date_datetimexmltag = new To_date_datetimexmltagImpl();
	   setBean(pBean);
	   lTo_date_datetimexmltag.setBean(bean);
       setCommit();
	   SubjectBean subject =  SubjectUtil.subject.get();
	   subject.setIdDominio(lSchemaBean.getSchema());	
	   SubjectUtil.subject.set(subject);    
	   Session session = null;
		try {
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lTo_date_datetimexmltag.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkUtilityTo_date_datetimexmltagBean> result = new StoreResultBean<DmpkUtilityTo_date_datetimexmltagBean>();
			AnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
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
}