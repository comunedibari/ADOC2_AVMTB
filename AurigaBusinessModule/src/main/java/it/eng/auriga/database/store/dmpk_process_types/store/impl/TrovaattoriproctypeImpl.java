package it.eng.auriga.database.store.dmpk_process_types.store.impl;

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovaattoriproctypeBean;
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

public class TrovaattoriproctypeImpl  {
		
	private DmpkProcessTypesTrovaattoriproctypeBean bean;
		  
	public void setBean(DmpkProcessTypesTrovaattoriproctypeBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESS_TYPES.TROVAATTORIPROCTYPE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.CLOB);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocesstypeio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeprocesstypeio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnaturaattorein",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsolovldin",7,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeattorein",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",13,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",14,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",16,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypeio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprocesstypeio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",19,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",25,Types.VARCHAR); 
						
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