/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.util.StringUtils;

/**
 *
 * @author alexander
 */
public class JsfRedirectStrategy implements InvalidSessionStrategy {

    //private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FACES_REQUEST_HEADER = "faces-request";

    private String invalidSessionUrl;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {        

        boolean ajaxRedirect = "partial/ajax".equals(request.getHeader(FACES_REQUEST_HEADER));
        if(ajaxRedirect) {
            String contextPath = request.getContextPath();
            String redirectUrl = contextPath + invalidSessionUrl;
            //logger.debug("Session expired due to ajax request, redirecting to '{}'", redirectUrl);
         
            String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);
            //logger.debug("Ajax partial response to redirect: {}", ajaxRedirectXml);

            response.setContentType("text/xml");
            response.getWriter().write(ajaxRedirectXml);
        } else {
            String contextPath = request.getContextPath();
            String redirectUrl = contextPath + invalidSessionUrl;
           
            String requestURI = getRequestUrl(request);
            System.out.println("Sesion ha expirado pase por aqui");
            //logger.debug("Session expired due to non-ajax request, starting a new session and redirect to requested url '{}'", requestURI);
            request.getSession(true);
            response.sendRedirect(requestURI);
        }

    }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();

        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            requestURL.append("?").append(queryString);
        }
        return requestURL.toString();
    }

    private String createAjaxRedirectXml(String redirectUrl) {
        return new StringBuilder()
                        .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                        .append("<partial-response><redirect url=\"")
                        .append(redirectUrl)
                        .append("\"></redirect></partial-response>")
                        .toString();
    }

    public void setInvalidSessionUrl(String invalidSessionUrl) {
        this.invalidSessionUrl = invalidSessionUrl;
    }

    
}