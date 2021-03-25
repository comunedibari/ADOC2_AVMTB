package it.eng.auriga.database.store.dmpk_processes.store;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatiprocxatt_jBean;
import it.eng.auriga.database.store.dmpk_processes.store.impl.Getdatiprocxatt_jImpl;
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
@Service(name="DmpkProcessesGetdatiprocxatt_j")
public class Getdatiprocxatt_j {
		
	private DmpkProcessesGetdatiprocxatt_jBean bean;
		  
	public void setBean(DmpkProcessesGetdatiprocxatt_jBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkProcessesGetdatiprocxatt_jBean> execute(SchemaBean lSchemaBean, DmpkProcessesGetdatiprocxatt_jBean pBean) throws Exception{
	   final Getdatiprocxatt_jImpl lGetdatiprocxatt_j = new Getdatiprocxatt_jImpl();
	   setBean(pBean);
	   lGetdatiprocxatt_j.setBean(bean);
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
					lGetdatiprocxatt_j.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkProcessesGetdatiprocxatt_jBean> result = new StoreResultBean<DmpkProcessesGetdatiprocxatt_jBean>();
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