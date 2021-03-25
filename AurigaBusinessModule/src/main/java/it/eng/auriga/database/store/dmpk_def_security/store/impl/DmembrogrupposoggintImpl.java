package it.eng.auriga.database.store.dmpk_def_security.store.impl;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDmembrogrupposoggintBean;
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

public class DmembrogrupposoggintImpl  {
		
	private DmpkDefSecurityDmembrogrupposoggintBean bean;
		  
	public void setBean(DmpkDefSecurityDmembrogrupposoggintBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.DMEMBROGRUPPOSOGGINT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idgruppoio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomegruppoin",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpobjappartenentein",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjappartenentein",7,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desobjappartenentein",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliuoappartenentein",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuoappscrivaniatoin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserscrivaniatoin",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",13,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idgruppoio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",16,Types.VARCHAR); 
						
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