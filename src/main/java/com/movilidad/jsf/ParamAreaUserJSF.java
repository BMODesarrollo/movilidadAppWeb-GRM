/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
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
@Named(value = "paramAreaUserJSF")
@ViewScoped
public class ParamAreaUserJSF implements Serializable {

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private UsersFacadeLocal usersFacadeLocal;
    @EJB
    private ParamAreaFacadeLocal paramAreaFacadeLocal;

    private ParamAreaUsr paramAreaUsr;
    private List<ParamAreaUsr> listParamAreaUsr;
    private List<Users> listUsers;
    private List<ParamArea> listParamArea;

    private HashMap<Integer, ParamArea> mapUser;

    private Integer i_idUsers;
    private Integer i_idArea;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public ParamAreaUserJSF() {
    }

    @PostConstruct
    public void init() {
        paramAreaUsr = null;
        listParamAreaUsr = new ArrayList<>();
        mapUser = new HashMap<>();
        i_idUsers = null;
        i_idArea = null;
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();
        listUsers = usersFacadeLocal.findAllUsersActivos();
    }

    public void guardar() {
        try {
            cargarMap();
            if (paramAreaUsr != null) {
                if (i_idUsers == null) {
                    MovilidadUtil.addErrorMessage("Usuario es requerido");
                    return;
                }
                if (i_idArea == null) {
                    MovilidadUtil.addErrorMessage("Área es requerido");
                    return;
                }
                if (mapUser.containsKey(i_idUsers)) {
                    MovilidadUtil.addErrorMessage("Usuario seleccionado tiene relación existente con " + mapUser.get(i_idUsers).getArea());
                    return;
                }
                cargarObjetos();
                paramAreaUsr.setUsername(user.getUsername());
                paramAreaUsr.setCreado(new Date());
                paramAreaUsr.setModificado(new Date());
                paramAreaUsr.setEstadoReg(0);
                MovilidadUtil.addSuccessMessage("Relación creada con éxito");
                paramAreaUsrFacadeLocal.create(paramAreaUsr);
                reset();
                paramAreaUsr = new ParamAreaUsr();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editar() {
        try {
            cargarMap();
            if (paramAreaUsr != null) {
                if (i_idUsers == null) {
                    MovilidadUtil.addErrorMessage("Usuario es requerido");
                    return;
                }
                if (i_idArea == null) {
                    MovilidadUtil.addErrorMessage("Área es requerido");
                    return;
                }
                if (!paramAreaUsr.getIdParamUsr().getUserId().equals(i_idUsers)) {
                    if (mapUser.containsKey(i_idUsers)) {
                        MovilidadUtil.addErrorMessage("Usuario seleccionado tiene relación existente con " + mapUser.get(i_idUsers).getArea());
                        return;
                    }
                }
                cargarObjetos();
                paramAreaUsr.setModificado(new Date());
                paramAreaUsrFacadeLocal.edit(paramAreaUsr);
                MovilidadUtil.addSuccessMessage("Relación actualizada con éxito");
                reset();
                PrimeFaces.current().executeScript("PF('usuDlg').hide()");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void prepareGuardar() {
        reset();
        paramAreaUsr = new ParamAreaUsr();
    }

    public void onSelectEdit(ParamAreaUsr event) {
        paramAreaUsr = event;
        i_idArea = paramAreaUsr.getIdParamArea() != null ? paramAreaUsr.getIdParamArea().getIdParamArea() : null;
        i_idUsers = paramAreaUsr.getIdParamUsr() != null ? paramAreaUsr.getIdParamUsr().getUserId() : null;
    }

    public void reset() {
        paramAreaUsr = null;
        i_idUsers = null;
        i_idArea = null;
    }

    void cargarObjetos() {
        paramAreaUsr.setIdParamArea(new ParamArea(i_idArea));
        paramAreaUsr.setIdParamUsr(new Users(i_idUsers));
    }

    void cargarMap() {
        listParamAreaUsr = paramAreaUsrFacadeLocal.findAllEstadoReg();
        if (listParamAreaUsr != null) {
            mapUser.clear();
            for (ParamAreaUsr p : listParamAreaUsr) {
                mapUser.put(p.getIdParamUsr().getUserId(), p.getIdParamArea());
            }
        }
    }

    public void eliminarEvent(ParamAreaUsr event) {
        event.setEstadoReg(1);
        event.setModificado(new Date());
        paramAreaUsrFacadeLocal.edit(event);
        MovilidadUtil.addSuccessMessage("Relación eliminada con éxito");
    }

    public ParamAreaUsr getParamAreaUsr() {
        return paramAreaUsr;
    }

    public void setParamAreaUsr(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
    }

    public List<ParamAreaUsr> getListParamAreaUsr() {
        listParamAreaUsr = paramAreaUsrFacadeLocal.findAllEstadoReg();
        return listParamAreaUsr;
    }

    public void setListParamAreaUsr(List<ParamAreaUsr> listParamAreaUsr) {
        this.listParamAreaUsr = listParamAreaUsr;
    }

    public Integer getI_idUsers() {
        return i_idUsers;
    }

    public void setI_idUsers(Integer i_idUsers) {
        this.i_idUsers = i_idUsers;
    }

    public Integer getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(Integer i_idArea) {
        this.i_idArea = i_idArea;
    }

    public List<Users> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<Users> listUsers) {
        this.listUsers = listUsers;
    }

    public List<ParamArea> getListParamArea() {
        return listParamArea;
    }

    public void setListParamArea(List<ParamArea> listParamArea) {
        this.listParamArea = listParamArea;
    }

}
