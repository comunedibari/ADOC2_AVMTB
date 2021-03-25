package it.eng.auriga.database.store.dmpk_utility.store.impl;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetlistdefprefsBean;
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

public class GetlistdefprefsImpl  {
		
	private DmpkUtilityGetlistdefprefsBean bean;
		  
	public void setBean(DmpkUtilityGetlistdefprefsBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_UTILITY.GETLISTDEFPREFS(?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.CLOB);
			call.registerOutParameter(5, Types.CLOB);
			call.registerOutParameter(6, Types.CLOB);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernamein",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listidin",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idtreenodein",14,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"filterprefout",4,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"filterlayoutprefout",5,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"gridprefout",6,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"autosearchprefout",7,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgfilterprefdefout",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgfilterlayoutprefdefout",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggridprefdefout",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",13,Types.VARCHAR); 
						
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