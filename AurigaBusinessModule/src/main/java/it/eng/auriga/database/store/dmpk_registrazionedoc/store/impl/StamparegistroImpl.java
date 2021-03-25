package it.eng.auriga.database.store.dmpk_registrazionedoc.store.impl;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocStamparegistroBean;
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

public class StamparegistroImpl  {
		
	private DmpkRegistrazionedocStamparegistroBean bean;
		  
	public void setBean(DmpkRegistrazionedocStamparegistroBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_REGISTRAZIONEDOC.STAMPAREGISTRO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.CLOB);
			call.registerOutParameter(7, Types.CLOB);
			call.registerOutParameter(8, Types.CLOB);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.CLOB);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"parametriregistroin",4,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idjobio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",18,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idjobio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"datiregistrazionixmlout",6,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dativarregistrazionixmlout",7,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"datiudstampaout",8,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"headerregistroout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"headervarregistroout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"footerregistroout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"footervarregistroout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroregistrazioniout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrovarregistrazioniout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldatiregistroxconservout",15,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tiporeportout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",21,Types.VARCHAR); 
						
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