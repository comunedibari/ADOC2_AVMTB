package it.eng.auriga.database.store.dmo_componente_gruppo.store;

import it.eng.auriga.database.store.dmo_componente_gruppo.bean.DmoComponenteGruppoTestvaliditaBean;
import it.eng.auriga.database.store.dmo_componente_gruppo.store.impl.TestvaliditaImpl;
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
@Service(name="DmoComponenteGruppoTestvalidita")
public class Testvalidita {
		
	private DmoComponenteGruppoTestvaliditaBean bean;
		  
	public void setBean(DmoComponenteGruppoTestvaliditaBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmoComponenteGruppoTestvaliditaBean> execute(SchemaBean lSchemaBean, DmoComponenteGruppoTestvaliditaBean pBean) throws Exception{
	   final TestvaliditaImpl lTestvalidita = new TestvaliditaImpl();
	   setBean(pBean);
	   lTestvalidita.setBean(bean);
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
					lTestvalidita.execute(paramConnection);
				}
			});
			StoreResultBean<DmoComponenteGruppoTestvaliditaBean> result = new StoreResultBean<DmoComponenteGruppoTestvaliditaBean>();
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