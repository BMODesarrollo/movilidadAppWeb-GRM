/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.OpcionFacadeLocal;
import com.movilidad.ejb.UserAuthorityFacadeLocal;
import com.movilidad.model.Opcion;
import com.movilidad.model.UserAuthority;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "userAuthorityJSF")
@ViewScoped
public class UserAuthorityJSF implements Serializable {

    @EJB
    private UserAuthorityFacadeLocal userAuthorityFacadeLocal;
    @EJB
    private OpcionFacadeLocal opcionFacadeLocal;

    private List<UserAuthority> listUserAuthority;
    private List<Opcion> listOpcion;

    private HashMap<String, UserAuthority> mapAuthority;

    private UserAuthority userAuthority;

    private String cAuthority;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public UserAuthorityJSF() {
    }

    @PostConstruct
    public void init() {
        listUserAuthority = new ArrayList<>();
        userAuthority = null;
        mapAuthority = new HashMap<>();
        cAuthority = null;
    }

    public void guardar() {
        try {
            if (userAuthority != null) {
                cargarMapa();
                if (mapAuthority.containsKey(userAuthority.getAuthority())) {
                    MovilidadUtil.addErrorMessage("Rol se encuentra registrado en el sistema");
                    return;
                }
                userAuthority.setCreado(new Date());
                userAuthority.setUsername(user.getUsername());
                userAuthority.setEstadoReg(0);
                userAuthorityFacadeLocal.create(userAuthority);
                prepareGuardar();
                MovilidadUtil.addSuccessMessage("Rol agregado con éxito");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void prepareGuardar() {
        userAuthority = new UserAuthority();
    }

    public void editar() {
        try {
            if (userAuthority != null) {
                cargarMapa();
                if (cAuthority != null && !cAuthority.equals(userAuthority.getAuthority())) {
                    if (mapAuthority.containsKey(userAuthority.getAuthority())) {
                        MovilidadUtil.addErrorMessage("Rol se encuentra registrado en el sistema");
                        return;
                    }
                }
                userAuthority.setModificado(new Date());
                userAuthorityFacadeLocal.edit(userAuthority);
                reset();
                MovilidadUtil.addSuccessMessage("Rol actaulizado con éxito");
                PrimeFaces.current().executeScript("PF('rolDlg').hide()");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void onPrepareEditar(UserAuthority event) {
        userAuthority = event;
        cAuthority = event.getAuthority();
    }

    public void onEliminar(UserAuthority event) {
        event.setEstadoReg(1);
        event.setModificado(new Date());
        userAuthorityFacadeLocal.edit(event);
        MovilidadUtil.addSuccessMessage("Rol eliminado con éxito");
    }

    public void onOpcion(Opcion event) {
        if (userAuthority != null) {
            userAuthority.setLandPage(event.getRecurso());
            MovilidadUtil.addSuccessMessage("Pagina principal asociada al Rol con éxito");
        }
    }

    public void cargarListOpcion() {
        listOpcion = opcionFacadeLocal.findAll();
    }

    private void cargarMapa() {
        mapAuthority.clear();
        for (UserAuthority usA : userAuthorityFacadeLocal.findAllEstadoReg()) {
            mapAuthority.put(usA.getAuthority(), usA);
        }
    }

    public void reset() {
        userAuthority = null;
        cAuthority = null;
    }

    public List<UserAuthority> getListUserAuthority() {
        listUserAuthority = userAuthorityFacadeLocal.findAllEstadoReg();
        return listUserAuthority;
    }

    public void setListUserAuthority(List<UserAuthority> listUserAuthority) {
        this.listUserAuthority = listUserAuthority;
    }

    public List<Opcion> getListOpcion() {
        return listOpcion;
    }

    public void setListOpcion(List<Opcion> listOpcion) {
        this.listOpcion = listOpcion;
    }

    public UserAuthority getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

}
