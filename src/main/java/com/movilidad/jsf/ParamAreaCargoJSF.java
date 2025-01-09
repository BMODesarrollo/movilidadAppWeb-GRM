/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "paramAreaCargoJSF")
@ViewScoped
public class ParamAreaCargoJSF implements Serializable {

    @EJB
    private ParamAreaCargoFacadeLocal paramAreaCargoFacadeLocal;
    @EJB
    private ParamAreaFacadeLocal paramAreaFacadeLocal;
    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoTipoCargoFacadeLocal;
    @EJB
    private UsersFacadeLocal usersFacadeLocal;

    private ParamAreaCargo paramAreaCargo;
    private ParamArea paramArea;
    private List<ParamAreaCargo> listParamAreaCargo;
    private List<EmpleadoTipoCargo> listEmpleadoCargo;
    private List<ParamArea> listParamArea;

    private Integer i_idEmpleadoCargo;
    private String c_usernameLogin;

    private HashMap<Integer, ParamArea> mapCargo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public ParamAreaCargoJSF() {
    }

    @PostConstruct
    public void init() {
        paramAreaCargo = null;
        listParamAreaCargo = new ArrayList<>();
        mapCargo = new HashMap<>();
        i_idEmpleadoCargo = null;
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();
        listEmpleadoCargo = empleadoTipoCargoFacadeLocal.findAllActivos();
        Users resp = usersFacadeLocal.findUserForUsername(user.getUsername());
        if (resp != null) {
            if (resp.getParamAreaUsrList() != null && !resp.getParamAreaUsrList().isEmpty()) {
                paramArea = resp.getParamAreaUsrList().get(0).getIdParamArea();
            }
        }
        if (paramArea != null) {
            listParamAreaCargo = paramAreaCargoFacadeLocal.findAllArea(paramArea.getIdParamArea());
        }
        c_usernameLogin = user.getUsername();
    }

    public void guardar() {
        try {
            cargarMap();
            if (paramAreaCargo != null) {
                if (i_idEmpleadoCargo == null) {
                    MovilidadUtil.addErrorMessage("Cargo es requerido");
                    return;
                }
                if (mapCargo.containsKey(i_idEmpleadoCargo)) {
                    MovilidadUtil.addErrorMessage("Cargo seleccionado tiene relación existente con " + mapCargo.get(i_idEmpleadoCargo).getArea());
                    return;
                }
                cargarObjetos();
                paramAreaCargo.setUsername(user.getUsername());
                paramAreaCargo.setCreado(new Date());
                paramAreaCargo.setModificado(new Date());
                paramAreaCargo.setEstadoReg(0);
                paramAreaCargoFacadeLocal.create(paramAreaCargo);
                MovilidadUtil.addSuccessMessage("Relación creada con éxito");
                listParamAreaCargo = paramAreaCargoFacadeLocal.findAllArea(paramArea.getIdParamArea());
                reset();
                paramAreaCargo = new ParamAreaCargo();
                paramAreaCargo.setIdParamArea(paramArea);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editar() {
        try {
            cargarMap();
            if (paramAreaCargo != null) {
                if (i_idEmpleadoCargo == null) {
                    MovilidadUtil.addErrorMessage("Cargo es requerido");
                    return;
                }
                if (!paramAreaCargo.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo().equals(i_idEmpleadoCargo)) {
                    if (mapCargo.containsKey(i_idEmpleadoCargo)) {
                        MovilidadUtil.addErrorMessage("Cargo seleccionado tiene relación existente con " + mapCargo.get(i_idEmpleadoCargo).getArea());
                        return;
                    }
                }
                cargarObjetos();
                paramAreaCargo.setModificado(new Date());
                paramAreaCargoFacadeLocal.edit(paramAreaCargo);
                MovilidadUtil.addSuccessMessage("Relación actualizada con éxito");
                listParamAreaCargo = paramAreaCargoFacadeLocal.findAllArea(paramArea.getIdParamArea());
                reset();
                PrimeFaces.current().executeScript("PF('cargoDlg').hide()");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void onParamAreaCargoEvent(ParamAreaCargo event) {
        paramAreaCargo = event;
        i_idEmpleadoCargo = paramAreaCargo.getIdEmpleadoTipoCargo() != null ? paramAreaCargo.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo() : null;
    }

    public void prepareGuardar() {
        reset();
        paramAreaCargo = new ParamAreaCargo();
        if (paramArea != null) {
            paramAreaCargo.setIdParamArea(paramArea);
            PrimeFaces.current().executeScript("PF('cargoDlg').show()");
        } else {
            eliminarMensages();
            MovilidadUtil.addErrorMessage(user.getUsername() + " no cuenta con Área asociada");
        }
    }

    public void reset() {
        paramAreaCargo = null;
        i_idEmpleadoCargo = null;
    }

    public void eliminarEvent(ParamAreaCargo event) {
        event.setEstadoReg(1);
        event.setModificado(new Date());
        paramAreaCargoFacadeLocal.edit(event);
        MovilidadUtil.addSuccessMessage("Relación eliminada con éxito");
    }

    void cargarObjetos() {
        paramAreaCargo.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_idEmpleadoCargo));
    }

    void cargarMap() {
        List<ParamAreaCargo> findAllEstadoReg = paramAreaCargoFacadeLocal.findAllEstadoReg();
        if (findAllEstadoReg != null) {
            mapCargo.clear();
            for (ParamAreaCargo p : findAllEstadoReg) {
                mapCargo.put(p.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), p.getIdParamArea());
            }
        }
    }

    void eliminarMensages() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> it = context.getMessages();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public ParamAreaCargo getParamAreaCargo() {
        return paramAreaCargo;
    }

    public void setParamAreaCargo(ParamAreaCargo paramAreaCargo) {
        this.paramAreaCargo = paramAreaCargo;
    }

    public List<ParamAreaCargo> getListParamAreaCargo() {
        return listParamAreaCargo;
    }

    public void setListParamAreaCargo(List<ParamAreaCargo> listParamAreaCargo) {
        this.listParamAreaCargo = listParamAreaCargo;
    }

    public Integer getI_idEmpleadoCargo() {
        return i_idEmpleadoCargo;
    }

    public void setI_idEmpleadoCargo(Integer i_idEmpleadoCargo) {
        this.i_idEmpleadoCargo = i_idEmpleadoCargo;
    }

    public List<EmpleadoTipoCargo> getListEmpleadoCargo() {
        return listEmpleadoCargo;
    }

    public void setListEmpleadoCargo(List<EmpleadoTipoCargo> listEmpleadoCargo) {
        this.listEmpleadoCargo = listEmpleadoCargo;
    }

    public List<ParamArea> getListParamArea() {
        return listParamArea;
    }

    public void setListParamArea(List<ParamArea> listParamArea) {
        this.listParamArea = listParamArea;
    }

    public String getC_usernameLogin() {
        return c_usernameLogin;
    }

}
