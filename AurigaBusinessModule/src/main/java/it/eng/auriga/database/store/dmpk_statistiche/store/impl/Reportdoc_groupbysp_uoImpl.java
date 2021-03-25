package it.eng.auriga.database.store.dmpk_statistiche.store.impl;

import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheReportdoc_groupbysp_uoBean;
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

public class Reportdoc_groupbysp_uoImpl  {
		
	private DmpkStatisticheReportdoc_groupbysp_uoBean bean;
		  
	public void setBean(DmpkStatisticheReportdoc_groupbysp_uoBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_STATISTICHE.REPORTDOC_GROUPBYSP_UO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.CLOB);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tiporeportin",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livellodettreportin",5,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idspaoouoreportonin",6,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"datadain",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dataain",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tipiregistrazioniin",9,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"reporttitleout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"reportcontentsxmlout",11,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecordout",12,Types.INTEGER); 
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