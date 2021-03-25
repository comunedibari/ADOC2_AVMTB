package it.eng.auriga.database.store.dmpk_order.store.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_order.bean.DmpkOrderGetcodiciintermediariBean;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.storeutil.HibernateStoreUtil;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */

public class GetcodiciintermediariImpl  {
		
	private DmpkOrderGetcodiciintermediariBean bean;
		  
	public void setBean(DmpkOrderGetcodiciintermediariBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ORDER.GETCODICIINTERMEDIARI(?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"otherparams",3,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"resultout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",6,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",7,Types.VARCHAR); 
						
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