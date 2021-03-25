package it.eng.auriga.database.store.dmpk_def_security.store.impl;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityCambiopasswordBean;
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

public class CambiopasswordImpl  {
		
	private DmpkDefSecurityCambiopasswordBean bean;
		  
	public void setBean(DmpkDefSecurityCambiopasswordBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.CAMBIOPASSWORD(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserin",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernamein",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserin",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnoverifyoldpwdin",7,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"oldpasswordin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"newpasswordin",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"confnewpasswordin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",12,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",15,Types.VARCHAR); 
						
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