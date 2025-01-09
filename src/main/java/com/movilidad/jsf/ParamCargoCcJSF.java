/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ParamCargoCcFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.model.ParamCargoCc;
import com.movilidad.model.EmpleadoTipoCargo;
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
 * @author cesar
 */
@Named(value = "paramCargoCcJSF")
@ViewScoped
public class ParamCargoCcJSF implements Serializable {

    @EJB
    private ParamCargoCcFacadeLocal paramCargoCcFacadeLocal;
    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoTipoCargoFacadeLocal;

    private ParamCargoCc paramCargoCc;
    private List<ParamCargoCc> listParamCargoCc;
    private List<EmpleadoTipoCargo> listEmpleadoTipoCargo;
    private HashMap<Integer, ParamCargoCc> mapParamCargoCc;

    private Integer idCargo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public ParamCargoCcJSF() {
    }

    @PostConstruct
    public void init() {
        paramCargoCc = null;
        listParamCargoCc = new ArrayList<>();
        listEmpleadoTipoCargo = new ArrayList<>();
        mapParamCargoCc = new HashMap<>();
        idCargo = null;
    }

    public void guardar() {
        if (paramCargoCc != null) {
            if (idCargo == null) {
                MovilidadUtil.addErrorMessage("Cargo es requerido");
                return;
            }
            cargarMap();
            if (mapParamCargoCc.containsKey(idCargo)) {
                MovilidadUtil.addErrorMessage(mapParamCargoCc.get(idCargo).getIdEmpleadoTipoCargo().getNombreCargo()
                        + " tiene relación con: "
                        + mapParamCargoCc.get(idCargo).getCentroCosto()
                        + ", no se puede realizar el proceso");
                return;
            }
            cargarObjetos();
            paramCargoCc.setUsername(user.getUsername());
            paramCargoCc.setCreado(new Date());
            paramCargoCc.setModificado(new Date());
            paramCargoCc.setEstadoReg(0);
            paramCargoCcFacadeLocal.create(paramCargoCc);
            reset();
            prepareGuardar();
            MovilidadUtil.addSuccessMessage("Centro Cargo registrado correctamente");
        }
    }

    public void prepareGuardar() {
        paramCargoCc = new ParamCargoCc();
        listEmpleadoTipoCargo = empleadoTipoCargoFacadeLocal.findAllActivos();
    }

    public void editar() {
        if (paramCargoCc != null) {
            if (idCargo == null) {
                MovilidadUtil.addErrorMessage("Cargo es requerido");
                return;
            }
            cargarMap();
            if (!paramCargoCc.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo().equals(idCargo)) {
                if (mapParamCargoCc.containsKey(idCargo)) {
                    MovilidadUtil.addErrorMessage(mapParamCargoCc.get(idCargo).getIdEmpleadoTipoCargo().getNombreCargo()
                            + " tiene relación con: "
                            + mapParamCargoCc.get(idCargo).getCentroCosto()
                            + ", no se puede realizar el proceso");
                    return;
                }
            }
            cargarObjetos();
            paramCargoCc.setModificado(new Date());
            paramCargoCcFacadeLocal.edit(paramCargoCc);
            reset();
            MovilidadUtil.addSuccessMessage("Centro Cargo actualizado correctamente");
            PrimeFaces.current().executeScript("PF('CcDlg').hide()");
        }
    }

    public void reset() {
        paramCargoCc = null;
        idCargo = null;
    }

    public void onSelectParamCargoCc(ParamCargoCc event) {
        paramCargoCc = event;
        idCargo = event.getIdEmpleadoTipoCargo() != null ? event.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo() : null;
        listEmpleadoTipoCargo = empleadoTipoCargoFacadeLocal.findAllActivos();
    }

    void cargarObjetos() {
        paramCargoCc.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(idCargo));
    }

    void cargarMap() {
        mapParamCargoCc.clear();
        List<ParamCargoCc> findAllEstadoReg = paramCargoCcFacadeLocal.findAllActivos();
        if (findAllEstadoReg != null && !findAllEstadoReg.isEmpty()) {
            for (ParamCargoCc pcc : findAllEstadoReg) {
                mapParamCargoCc.put(pcc.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), pcc);
            }
        }
    }

    public ParamCargoCc getParamCargoCc() {
        return paramCargoCc;
    }

    public void setParamCargoCc(ParamCargoCc paramCargoCc) {
        this.paramCargoCc = paramCargoCc;
    }

    public List<ParamCargoCc> getListParamCargoCc() {
        listParamCargoCc = paramCargoCcFacadeLocal.findAllActivos();
        return listParamCargoCc;
    }

    public void setListParamCargoCc(List<ParamCargoCc> listParamCargoCc) {
        this.listParamCargoCc = listParamCargoCc;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public List<EmpleadoTipoCargo> getListEmpleadoTipoCargo() {
        return listEmpleadoTipoCargo;
    }

    public void setListEmpleadoTipoCargo(List<EmpleadoTipoCargo> listEmpleadoTipoCargo) {
        this.listEmpleadoTipoCargo = listEmpleadoTipoCargo;
    }

}
