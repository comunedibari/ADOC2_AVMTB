package it.eng.auriga.database.store.dmpk_policy.store.impl;

import it.eng.auriga.database.store.dmpk_policy.bean.DmpkPolicyGetuserprofilemaindataBean;
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

public class GetuserprofilemaindataImpl  {
		
	private DmpkPolicyGetuserprofilemaindataBean bean;
		  
	public void setBean(DmpkPolicyGetuserprofilemaindataBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_POLICY.GETUSERPROFILEMAINDATA(?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpdominioin",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddominioin",2,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserin",3,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvisibindipaclout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvisibindipuserabilout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"maxlivriservatezzaout",6,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggestindipaclout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggestindipuserabilout",8,Types.VARCHAR); 
						
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