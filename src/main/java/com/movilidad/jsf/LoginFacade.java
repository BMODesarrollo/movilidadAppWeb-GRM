/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.model.Opcion;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.service.OpcionService;
import com.movilidad.service.RolesService;
import com.movilidad.service.UsersService;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class LoginFacade implements Serializable {

    private String fullname;
    private String email;
    private String lastAccess;
    private String userName;
    @ManagedProperty("#{rolesService}")
    private RolesService rolesService;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    @ManagedProperty("#{opcionService}")
    private OpcionService opcionService;

    public RolesService getRolesService() {
        return rolesService;
    }

    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public OpcionService getOpcionService() {
        return opcionService;
    }

    public void setOpcionService(OpcionService opcionService) {
        this.opcionService = opcionService;
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Creates a new instance of LoginFacade
     */
    public LoginFacade() {
        WebApplicationContext wc = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        this.fullname = user.getNombre();
        this.email = user.getEmail();
        this.lastAccess = user.getApellidos();
        this.userName = user.getUsername();

    }

    public void comprobar() {
        WebApplicationContext wc = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean notcontain = false;
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        Users findUser = this.getUsersService().findUser(user.getUsername());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        HttpServletResponse origResonse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        Object[] roles = authorities.toArray();
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) roles[0];
        UserRoles role = this.getRolesService().getRole(rol.getAuthority(), findUser);
        Collection<Opcion> opciones = this.getRolesService().getOpciones(role, findUser);
        if (viewId.contains("index")) {
            return;
        }
        for (Opcion opcione : opciones) {

            if (viewId.contains(opcione.getRecurso())) {
                notcontain = true;
                break;

            }
        }
        if (!notcontain) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(wc.getServletContext().getContextPath() + "/access-denied.jsf");
            } catch (IOException ex) {
                Logger.getLogger(LoginFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
