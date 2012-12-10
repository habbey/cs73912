
package com.cs739.app.servlet.master;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.Replicant;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
import com.cs739.app.util.AppConstants.MasterPages;

/**
 * Main master endpoint right now, can handle image uploads.
 * Responsible for initializing state of server, like the number of
 * {@link Replicant} instances that have contacted.
 * @author MDee
 */
public class IndexServlet extends AbstractPlopboxServlet implements ServletContextListener {

    /**
     * 
     */
    private static final long serialVersionUID = 7252872348356932582L;

    private static final Logger log = LoggerFactory
    .getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }
        
        forward(request, response, MasterPages.LOGIN.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("doPost");
        }
        response.sendRedirect("index");
    }

    ServletContext context;
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("Context Created");
        context = contextEvent.getServletContext();
        // set up vars
        context.setAttribute(AppConstants.NUM_REPLICANTS, 0);
        context.setAttribute(AppConstants.REPLICANTS, new ArrayList<Replicant>());
       
    }
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();
        log.info("Context Destroyed");
    }
}
