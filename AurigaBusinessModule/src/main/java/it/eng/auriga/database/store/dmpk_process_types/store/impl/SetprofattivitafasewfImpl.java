package it.eng.auriga.database.store.dmpk_process_types.store.impl;

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesSetprofattivitafasewfBean;
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

public class SetprofattivitafasewfImpl  {
		
	private DmpkProcessTypesSetprofattivitafasewfBean bean;
		  
	public void setBean(DmpkProcessTypesSetprofattivitafasewfBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESS_TYPES.SETPROFATTIVITAFASEWF(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"citypeflussowfin",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cifasein",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciattivitain",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nroordinevisin",7,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodaclin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlaclin",9,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgassabilin",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"controlliobblvsfasein",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmoddettxesitoin",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmldettxesitoin",13,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmoddesttrasmin",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmldesttrasmin",15,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodattraddeditabiliin",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomiattraddeditabiliin",17,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cifaseoldin",23,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgcallbyguiin",24,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",22,Types.VARCHAR); 
						
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