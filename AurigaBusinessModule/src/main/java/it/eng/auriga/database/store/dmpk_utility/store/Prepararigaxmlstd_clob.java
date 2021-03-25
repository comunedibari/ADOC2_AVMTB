package it.eng.auriga.database.store.dmpk_utility.store;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityPrepararigaxmlstd_clobBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Prepararigaxmlstd_clobImpl;
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
@Service(name="DmpkUtilityPrepararigaxmlstd_clob")
public class Prepararigaxmlstd_clob {
		
	private DmpkUtilityPrepararigaxmlstd_clobBean bean;
		  
	public void setBean(DmpkUtilityPrepararigaxmlstd_clobBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkUtilityPrepararigaxmlstd_clobBean> execute(SchemaBean lSchemaBean, DmpkUtilityPrepararigaxmlstd_clobBean pBean) throws Exception{
	   final Prepararigaxmlstd_clobImpl lPrepararigaxmlstd_clob = new Prepararigaxmlstd_clobImpl();
	   setBean(pBean);
	   lPrepararigaxmlstd_clob.setBean(bean);
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
					lPrepararigaxmlstd_clob.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkUtilityPrepararigaxmlstd_clobBean> result = new StoreResultBean<DmpkUtilityPrepararigaxmlstd_clobBean>();
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