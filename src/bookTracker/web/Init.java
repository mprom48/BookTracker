package bookTracker.web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import bookTracker.data.DataAccessObject;

public class Init implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext(); 
		try{ 
			contextInitialized2(servletContext); 
		}
		catch (Exception e) { 
			throw new RuntimeException(e); 
		}
	}

	private void contextInitialized2(ServletContext servletContext) throws Exception {
		InitialContext enc = new InitialContext(); 
		Context compContext = (Context) enc.lookup("java:comp/env"); 
		DataSource ds = (DataSource) compContext.lookup("datasource"); 
		DataAccessObject.setDataSource(ds);
		
	}
	
}
