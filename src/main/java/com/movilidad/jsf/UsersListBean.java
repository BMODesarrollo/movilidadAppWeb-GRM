/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.model.Users;
import com.movilidad.service.UsersService;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author solucionesit
 */
@Named(value = "usersListBean")
@ViewScoped
public class UsersListBean implements Serializable {

    /**
     * Creates a new instance of UsersListBean
     */
    public UsersListBean() {
    }
    private WebApplicationContext wc;

    public List<Users> consultarUsersHabilitadoByUF(int idgopUnidadFunc) {
        wc = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        UsersService us = (UsersService) wc.getBean("usersService");
        return us.findAllHabilidatosByIdGopUnidadFunc(idgopUnidadFunc);
    }
}
