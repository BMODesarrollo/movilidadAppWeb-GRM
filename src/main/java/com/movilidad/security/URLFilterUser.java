///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.movilidad.security;
//
//import com.movilidad.model.Opcion;
//import com.movilidad.model.UserRoles;
//import com.movilidad.model.Users;
//import com.movilidad.service.impl.RolesServiceImpl;
//import com.movilidad.service.impl.UsersServiceImpl;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.Collection;
//import javax.servlet.DispatcherType;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
///**
// *
// * @author alexanderosorio
// */
//@WebFilter(filterName = "URLFilterUser", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
//public class URLFilterUser implements Filter {
//
//    private static final boolean debug = true;
//
//    // The filter configuration object we are associated with.  If
//    // this value is null, this filter instance is not currently
//    // configured. 
//    private FilterConfig filterConfig = null;
//
//    public URLFilterUser() {
//    }
//
//    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
//            throws IOException, ServletException {
//        if (debug) {
//            log("URLFilterUser:DoBeforeProcessing");
//        }
//
//        // Write code here to process the request and/or response before
//        // the rest of the filter chain is invoked.
//        // For example, a logging filter might log items on the request object,
//        // such as the parameters.
//	/*
//         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
//         String name = (String)en.nextElement();
//         String values[] = request.getParameterValues(name);
//         int n = values.length;
//         StringBuffer buf = new StringBuffer();
//         buf.append(name);
//         buf.append("=");
//         for(int i=0; i < n; i++) {
//         buf.append(values[i]);
//         if (i < n-1)
//         buf.append(",");
//         }
//         log(buf.toString());
//         }
//         */
//    }
//
//    private void doAfterProcessing(ServletRequest request, ServletResponse response)
//            throws IOException, ServletException {
//        if (debug) {
//            log("URLFilterUser:DoAfterProcessing");
//        }
//
//	// Write code here to process the request and/or response after
//        // the rest of the filter chain is invoked.
//        // For example, a logging filter might log the attributes on the
//        // request object after the request has been processed. 
//	/*
//         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
//         String name = (String)en.nextElement();
//         Object value = request.getAttribute(name);
//         log("attribute: " + name + "=" + value.toString());
//
//         }
//         */
//        // For example, a filter might append something to the response.
//	/*
//         PrintWriter respOut = new PrintWriter(response.getWriter());
//         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
//         */
//    }
//
//    /**
//     *
//     * @param request The servlet request we are processing
//     * @param response The servlet response we are creating
//     * @param chain The filter chain we are processing
//     *
//     * @exception IOException if an input/output error occurs
//     * @exception ServletException if a servlet error occurs
//     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//            FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpSession session = req.getSession();
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
//        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
//        UsersServiceImpl uimpl = wc.getBean("usersService", UsersServiceImpl.class);
//        RolesServiceImpl roleServiceImpl = wc.getBean("rolesService", RolesServiceImpl.class);
//        boolean flag = false;
//        if (sci != null) {
//
//            UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
//            Users userLogin = uimpl.findUser(cud.getUsername());
//
//            Collection<? extends GrantedAuthority> authorities = cud.getAuthorities();
//            Object[] roles = authorities.toArray();
//            SimpleGrantedAuthority rol = (SimpleGrantedAuthority) roles[0];
//            UserRoles role = roleServiceImpl.getRole(rol.getAuthority(), userLogin);
//            Collection<Opcion> opciones = roleServiceImpl.getOpciones(role, userLogin);
//            Collection<Opcion> opcionesAll = roleServiceImpl.getOpciones();
//            String requestURI = req.getRequestURI();
//            boolean eval = false;
//
//            for (Opcion opc : opcionesAll) {
//                String urlCompletada = req.getServletContext().getContextPath() + opc.getRecurso();
//                if (requestURI.contains(urlCompletada)) {
//                    eval = true;
//                    break;
//                }
//            }
//
//            if (eval) {
//                for (Opcion op : opciones) {
//                    if (requestURI.contains(op.getRecurso())) {
//                        flag = true;
//                        break;
//                    }
//
//                }
//
//                if (flag) {
//                    chain.doFilter(request, response);
//                } else {
//                  res.sendRedirect(req.getContextPath()+"/access-denied.jsf");
//                }
//            }
//
//        }
//
//        chain.doFilter(request, response);
//
//    }
//
//    /**
//     * Return a String representation of this object.
//     */
//    @Override
//    public String toString() {
//        if (filterConfig == null) {
//            return ("URLFilterUser()");
//        }
//        StringBuffer sb = new StringBuffer("URLFilterUser(");
//        sb.append(filterConfig);
//        sb.append(")");
//        return (sb.toString());
//    }
//
//    private void sendProcessingError(Throwable t, ServletResponse response) {
//        String stackTrace = getStackTrace(t);
//
//        if (stackTrace != null && !stackTrace.equals("")) {
//            try {
//                response.setContentType("text/html");
//                PrintStream ps = new PrintStream(response.getOutputStream());
//                PrintWriter pw = new PrintWriter(ps);
//                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
//
//                // PENDING! Localize this for next official release
//                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
//                pw.print(stackTrace);
//                pw.print("</pre></body>\n</html>"); //NOI18N
//                pw.close();
//                ps.close();
//                response.getOutputStream().close();
//            } catch (Exception ex) {
//            }
//        } else {
//            try {
//                PrintStream ps = new PrintStream(response.getOutputStream());
//                t.printStackTrace(ps);
//                ps.close();
//                response.getOutputStream().close();
//            } catch (Exception ex) {
//            }
//        }
//    }
//
//    public static String getStackTrace(Throwable t) {
//        String stackTrace = null;
//        try {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            t.printStackTrace(pw);
//            pw.close();
//            sw.close();
//            stackTrace = sw.getBuffer().toString();
//        } catch (Exception ex) {
//        }
//        return stackTrace;
//    }
//
//    public void log(String msg) {
//        filterConfig.getServletContext().log(msg);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("Info");
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("Destroy");
//    }
//
//}
