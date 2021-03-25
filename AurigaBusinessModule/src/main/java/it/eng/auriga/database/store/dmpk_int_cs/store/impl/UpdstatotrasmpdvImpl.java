package it.eng.auriga.database.store.dmpk_int_cs.store.impl;

import it.eng.auriga.database.store.dmpk_int_cs.bean.DmpkIntCsUpdstatotrasmpdvBean;
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

public class UpdstatotrasmpdvImpl  {
		
	private DmpkIntCsUpdstatotrasmpdvBean bean;
		  
	public void setBean(DmpkIntCsUpdstatotrasmpdvBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_INT_CS.UPDSTATOTRASMPDV(?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idpdvin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"statotrasmpdvin",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"errwarncodein",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"errwarnmsgin",5,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ricevutatrasmin",6,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idpdvconsevatorein",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",8,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",9,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"labelpdvin",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsricezionericin",14,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",12,Types.VARCHAR); 
						
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