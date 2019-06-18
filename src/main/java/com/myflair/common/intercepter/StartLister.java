package com.myflair.common.intercepter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * Application Lifecycle Listener implementation class StartLister
 *
 */
public class StartLister extends HttpServlet implements ServletContextListener {

    private static WebApplicationContext springContext;

    public StartLister() {
        super();
    }

    public void contextInitialized(ServletContextEvent request) {
    	System.out.print("request.getContextPath()="+request.getServletContext().getContextPath());
    	request.getServletContext().setAttribute("baseContextPath", request.getServletContext().getContextPath()+"/");
        springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    public static ApplicationContext getApplicationContext() {
        return springContext;
    }
}
