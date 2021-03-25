package it.eng.auriga.database.store.dmpk_mail.store;

import it.eng.auriga.database.store.dmpk_mail.bean.DmpkMailAttach_textBean;
import it.eng.auriga.database.store.dmpk_mail.store.impl.Attach_textImpl;
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
@Service(name="DmpkMailAttach_text")
public class Attach_text {
		
	private DmpkMailAttach_textBean bean;
		  
	public void setBean(DmpkMailAttach_textBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkMailAttach_textBean> execute(SchemaBean lSchemaBean, DmpkMailAttach_textBean pBean) throws Exception{
	   final Attach_textImpl lAttach_text = new Attach_textImpl();
	   setBean(pBean);
	   lAttach_text.setBean(bean);
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
					lAttach_text.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkMailAttach_textBean> result = new StoreResultBean<DmpkMailAttach_textBean>();
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