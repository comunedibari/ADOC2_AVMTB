package it.eng.auriga.database.store.dmpk_def_security.store.impl;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovaprivsuoggdefcontestoperBean;
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

public class TrovaprivsuoggdefcontestoperImpl  {
		
	private DmpkDefSecurityTrovaprivsuoggdefcontestoperBean bean;
		  
	public void setBean(DmpkDefSecurityTrovaprivsuoggdefcontestoperBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.TROVAPRIVSUOGGDEFCONTESTOPER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.CLOB);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpobjprivtoin",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjprivtoin",6,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desobjprivtoin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliuoprivtoin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuoappscrivaniatoin",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserscrivaniatoin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpobjprivonin",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsoloprivilegidirettiin",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpprivilegioin",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"rigatagnamein",27,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",23,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",26,Types.VARCHAR); 
						
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