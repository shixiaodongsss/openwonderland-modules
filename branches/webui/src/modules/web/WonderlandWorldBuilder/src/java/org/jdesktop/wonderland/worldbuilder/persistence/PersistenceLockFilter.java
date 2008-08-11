/**
 * Project Looking Glass
 *
 * $RCSfile$
 *
 * Copyright (c) 2004-2008, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * $State$
 */
package org.jdesktop.wonderland.worldbuilder.persistence;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.locks.Lock;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author jkaplan
 */
public class PersistenceLockFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    // the lock we grabbed
    
    public PersistenceLockFilter() {
    } 

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException
    {

        boolean hasLock = false;
        if (request instanceof HttpServletRequest &&
                ((HttpServletRequest) request).getMethod().equalsIgnoreCase("GET"))
        {
            // get a read lock.  All write operations will be scheduled in a
            // separate task, so there is no need to grab a write lock, even
            // for post operations
            CellPersistence.get().getLock().readLock().lock();
            hasLock = true;
        }
        
	Throwable problem = null;
	try {
	    chain.doFilter(request, response);
        } catch(Throwable t) {
	    //
	    // If an exception is thrown somewhere down the filter chain,
	    // we still want to execute our after processing, and then
	    // rethrow the problem after that.
	    //
	    problem = t;
	    t.printStackTrace();
	} finally {
            // unlock
            if (hasLock) {
                CellPersistence.get().getLock().readLock().unlock();
            }
        }

	//
	// If there was a problem, we want to rethrow it if it is
	// a known type, otherwise log it.
	//
	if (problem != null) {
	    if (problem instanceof ServletException) throw (ServletException)problem;
	    if (problem instanceof IOException) throw (IOException)problem;
	    sendProcessingError(problem, response);
	}
    }

    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
	return (this.filterConfig);
    }


    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {

	this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter 
     *
     */
    public void destroy() { 
    }


    /**
     * Init method for this filter 
     *
     */
    public void init(FilterConfig filterConfig) { 

	this.filterConfig = filterConfig;
	if (filterConfig != null) {
	    if (debug) { 
		log("PersistenceLockFilter:Initializing filter");
	    }
	}
    }

    /**
     * Return a String representation of this object.
     */
    public String toString() {

	if (filterConfig == null) return ("PersistenceLockFilter()");
	StringBuffer sb = new StringBuffer("PersistenceLockFilter(");
	sb.append(filterConfig);
	sb.append(")");
	return (sb.toString());

    }



    private void sendProcessingError(Throwable t, ServletResponse response) {
	
	String stackTrace = getStackTrace(t); 

	if(stackTrace != null && !stackTrace.equals("")) {

	    try {
		    
		response.setContentType("text/html");
		PrintStream ps = new PrintStream(response.getOutputStream());
		PrintWriter pw = new PrintWriter(ps); 
		pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
		    
		// PENDING! Localize this for next official release
		pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n"); 
		pw.print(stackTrace); 
		pw.print("</pre></body>\n</html>"); //NOI18N
		pw.close();
		ps.close();
		response.getOutputStream().close();;
	    }
		
	    catch(Exception ex){ }
	}
	else {
	    try {
		PrintStream ps = new PrintStream(response.getOutputStream());
		t.printStackTrace(ps);
		ps.close();
		response.getOutputStream().close();;
	    }
	    catch(Exception ex){ }
	}
    }

    public static String getStackTrace(Throwable t) {

	String stackTrace = null;
	    
	try {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    t.printStackTrace(pw);
	    pw.close();
	    sw.close();
	    stackTrace = sw.getBuffer().toString();
	}
	catch(Exception ex) {}
	return stackTrace;
    }

    public void log(String msg) {
	filterConfig.getServletContext().log(msg); 
    }

    private static final boolean debug = true;
}