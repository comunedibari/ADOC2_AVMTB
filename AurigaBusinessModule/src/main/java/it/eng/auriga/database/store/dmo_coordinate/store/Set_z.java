package it.eng.auriga.database.store.dmo_coordinate.store;

import it.eng.auriga.database.store.dmo_coordinate.bean.DmoCoordinateSet_zBean;
import it.eng.auriga.database.store.dmo_coordinate.store.impl.Set_zImpl;
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
@Service(name="DmoCoordinateSet_z")
public class Set_z {
		
	private DmoCoordinateSet_zBean bean;
		  
	public void setBean(DmoCoordinateSet_zBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmoCoordinateSet_zBean> execute(SchemaBean lSchemaBean, DmoCoordinateSet_zBean pBean) throws Exception{
	   final Set_zImpl lSet_z = new Set_zImpl();
	   setBean(pBean);
	   lSet_z.setBean(bean);
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
					lSet_z.execute(paramConnection);
				}
			});
			StoreResultBean<DmoCoordinateSet_zBean> result = new StoreResultBean<DmoCoordinateSet_zBean>();
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