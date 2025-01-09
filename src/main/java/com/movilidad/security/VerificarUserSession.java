/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.security;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Web application lifecycle listener.
 *
 * @author alexander
 */
@WebListener()
public class VerificarUserSession implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Enumeration<String> attributeNames = se.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String string = attributeNames.nextElement();
//            System.out.println(string + "-------------------------------------------");

        }
        ServletContext servletContext = se.getSession().getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);

    }

}
