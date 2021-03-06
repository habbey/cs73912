
package com.cs739.app.servlet.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxFile;
import com.cs739.app.model.Replicant;
import com.cs739.app.service.master.PlopboxFileService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;

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

        log.debug("Checking for cookie...");
        // Check for cookie, if it's set then send it home
        Cookie[] cookies = request.getCookies();
        String username = null, userId = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(AppConstants.USERNAME)) {
                    username = c.getValue();
                } else if (c.getName().equals(AppConstants.USER_ID)) {
                    userId = c.getValue();
                }
            }
        }
        if (username != null && userId != null) {
            // set them in ze cookie
            //request.setAttribute(AppConstants.USERNAME, username);
            //request.setAttribute(AppConstants.USER_ID, userId);
            response.sendRedirect("home");
        } else {
            log.debug("Cookie info is not set -- redirecting to login");
            response.sendRedirect("login");
        }
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
        
        // GAE returns a read-only List, so copy it to a modifiable one
        // TODO: figure out when to persist...?
        List<PlopboxFile> persistedFiles = PlopboxFileService.getAllPlopboxFiles();
        List<PlopboxFile> filesCopy = new ArrayList<PlopboxFile>();
        Collections.copy(persistedFiles, filesCopy);
        context.setAttribute(AppConstants.MASTER_FILES_LIST, filesCopy);

    }
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();
        log.info("Context Destroyed");
    }
}
