package it.eng.auriga.database.store.dmpk_core.store.impl;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreLoadaclfolderBean;
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

public class LoadaclfolderImpl  {
		
	private DmpkCoreLoadaclfolderBean bean;
		  
	public void setBean(DmpkCoreLoadaclfolderBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_CORE.LOADACLFOLDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.CLOB);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idfolderin",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgereditarietapossibileout",5,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgereditarietaconsentitaout",6,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfolderereditadaout",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desfolderereditadaout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfolderpossibileereddaout",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desfolderpossibileereddaout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpropagazioneconsentitaout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"aclxmlout",12,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",13,Types.INTEGER); 
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