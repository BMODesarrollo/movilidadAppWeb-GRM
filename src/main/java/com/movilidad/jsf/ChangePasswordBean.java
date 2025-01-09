/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.service.UsersService;

import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author alexander
 */
@ManagedBean(name = "changePasswordBean")
@ViewScoped
public class ChangePasswordBean implements Serializable {

    private String oldPassword;
    private String newpassword;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    private boolean flagGestionContrasena = false;

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    /**
     * Creates a new instance of ChangePasswordBean
     */
    public ChangePasswordBean() {
    }

    public void changePassword(ActionEvent e) {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        UserExtended principal = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users findUser = getUsersService().findUser(principal.getUsername());
        boolean valid = false;
        if (findUser.getPassword().length() < 20) {
            valid = this.getUsersService().isValid(principal.getUsername(), oldPassword);
        } else {
            valid = compareAuth(findUser.getPassword(), oldPassword);
        }

        if (valid) {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            Users usuario = usersService.findUser(principal.getUsername());
            usuario.setPassword(bcpe.encode(newpassword));
            usersService.updateUser(usuario);

            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cambio exitoso"));
            PrimeFaces.current().executeScript("PF('changePasswrodWV').hide()");
            oldPassword = "";
            newpassword = "";
        } else {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Contraseña anterior no coincide"));
        }
    }

    private boolean compareAuth(String contrasenia_encrip, String contrasenia_aut) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe.matches(contrasenia_aut, contrasenia_encrip);
    }

    public boolean isFlagGestionContrasena() {
        return flagGestionContrasena;
    }

    public void setFlagGestionContrasena(boolean flagGestionContrasena) {
        this.flagGestionContrasena = flagGestionContrasena;
    }

}
