package it.eng.auriga.database.store.dmpk_collaboration.store.impl;

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationAnnullamentonotificaBean;
import it.eng.storeutil.HibernateStoreUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import oracle.jdbc.OracleTypes;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.springframework.beans.BeanWrapperImpl;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;



import org.apache.commons.lang3.StringUtils;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */

public class AnnullamentonotificaImpl  {
		
	private DmpkCollaborationAnnullamentonotificaBean bean;
		  
	public void setBean(DmpkCollaborationAnnullamentonotificaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_COLLABORATION.ANNULLAMENTONOTIFICA(?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtypeobjnotifiedin",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjnotifiedin",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idnotificain",6,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"motivoannin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",8,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",9,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",10,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",14,Types.VARCHAR); 
						
		}catch(Exception e){
			if (e instanceof SQLException){
				SQLException pSqlException = (SQLException)e;
				if (pSqlException.getErrorCode()==1013 && pSqlException.getSQLState().equals("72000")){
					throw new SQLException("Chiusura forzata");
				}
			} throw new SQLException(e);
		}finally{
		 	try{call.close();} catch(Exception e){}
		}
   }
}