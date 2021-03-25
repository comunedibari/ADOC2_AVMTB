package it.eng.auriga.database.store.dmo_estremi_indirizzo.store;

import it.eng.auriga.database.store.dmo_estremi_indirizzo.bean.DmoEstremiIndirizzoGetnomestatoBean;
import it.eng.auriga.database.store.dmo_estremi_indirizzo.store.impl.GetnomestatoImpl;
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
@Service(name="DmoEstremiIndirizzoGetnomestato")
public class Getnomestato {
		
	private DmoEstremiIndirizzoGetnomestatoBean bean;
		  
	public void setBean(DmoEstremiIndirizzoGetnomestatoBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmoEstremiIndirizzoGetnomestatoBean> execute(SchemaBean lSchemaBean, DmoEstremiIndirizzoGetnomestatoBean pBean) throws Exception{
	   final GetnomestatoImpl lGetnomestato = new GetnomestatoImpl();
	   setBean(pBean);
	   lGetnomestato.setBean(bean);
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
					lGetnomestato.execute(paramConnection);
				}
			});
			StoreResultBean<DmoEstremiIndirizzoGetnomestatoBean> result = new StoreResultBean<DmoEstremiIndirizzoGetnomestatoBean>();
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