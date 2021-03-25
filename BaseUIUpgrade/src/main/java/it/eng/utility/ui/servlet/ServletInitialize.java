package it.eng.utility.ui.servlet;

import it.eng.spring.servlet.SpringInitServlet;
import it.eng.utility.ui.module.core.server.datasource.SingletonDataSource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;


/**
 * Servlet di init per istanziare il server ADempiere
 * @author michele
 *
 */
public class ServletInitialize extends SpringInitServlet {

	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(ServletInitialize.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);				
								
		SingletonDataSource.getInstance().initialize();
	    logger.info("GUI started successfully");
		
	}
}
