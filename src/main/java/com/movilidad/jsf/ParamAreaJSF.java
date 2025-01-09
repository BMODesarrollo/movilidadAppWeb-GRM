/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.model.ParamArea;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@Named(value = "paramAreaJSF")
@ViewScoped
public class ParamAreaJSF implements Serializable {

    @EJB
    private ParamAreaFacadeLocal paramAreaFacadeLocal;

    private ParamArea paramArea;
    private List<ParamArea> listParamArea;

    private boolean b_btnJornada;
    private boolean b_btnNovedad;
    private boolean b_btnMaster;
    private boolean b_btnJFlexible;

    public ParamAreaJSF() {
    }

    @PostConstruct
    public void init() {
        paramArea = null;
        listParamArea = new ArrayList<>();
        b_btnJornada = false;
        b_btnNovedad = false;
        b_btnMaster = false;
        b_btnJFlexible = false;
    }

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void guardar() {
        try {
            if (paramArea != null) {
                cargarObjetos();
                paramArea.setUsername(user.getUsername());
                paramArea.setCreado(new Date());
                paramArea.setModificado(new Date());
                paramArea.setEstadoReg(0);
                MovilidadUtil.addSuccessMessage("Parametrización de área realizada con éxito");
                paramAreaFacadeLocal.create(paramArea);
                reset();
                paramArea = new ParamArea();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editar() {
        try {
            if (paramArea != null) {
                cargarObjetos();
                paramArea.setModificado(new Date());
                paramAreaFacadeLocal.edit(paramArea);
                MovilidadUtil.addSuccessMessage("Parametrización de área actualizada con éxito");
                reset();
                PrimeFaces.current().executeScript("PF('areaDlg').hide()");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void opcionProgramaMaster() {
        if (!b_btnNovedad) {
            b_btnMaster = false;
        }
    }

    void cargarObjetos() {
        paramArea.setControlJornada(b_btnJornada ? 1 : 0);
        paramArea.setNovedad(b_btnNovedad ? 1 : 0);
        paramArea.setProgramaMaster(b_btnMaster ? 1 : 0);
        paramArea.setJornadaFlexible(b_btnJFlexible ? 1 : 0);
    }

    public void reset() {
        paramArea = null;
        b_btnJornada = false;
        b_btnNovedad = false;
        b_btnMaster = false;
        b_btnJFlexible = false;
    }

    public void prepareGuardar() {
        paramArea = new ParamArea();
    }

    public void onParamAreaEvent(ParamArea event) {
        paramArea = event;
        b_btnJornada = paramArea.getControlJornada() != null ? paramArea.getControlJornada().equals(1) : false;
        b_btnMaster = paramArea.getProgramaMaster() != null ? paramArea.getProgramaMaster().equals(1) : false;
        b_btnJFlexible = paramArea.getJornadaFlexible()!= null ? paramArea.getJornadaFlexible().equals(1) : false;
        b_btnNovedad = paramArea.getNovedad() != null ? paramArea.getNovedad().equals(1) : false;
    }

    public void eliminarEvent(ParamArea event) {
        event.setEstadoReg(1);
        event.setModificado(new Date());
        paramAreaFacadeLocal.edit(event);
        MovilidadUtil.addSuccessMessage("Parametrización de área eliminada con éxito");
    }

    public ParamArea getParamArea() {
        return paramArea;
    }

    public void setParamArea(ParamArea paramArea) {
        this.paramArea = paramArea;
    }

    public List<ParamArea> getListParamArea() {
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();
        return listParamArea;
    }

    public void setListParamArea(List<ParamArea> listParamArea) {
        this.listParamArea = listParamArea;
    }

    public boolean isB_btnJornada() {
        return b_btnJornada;
    }

    public void setB_btnJornada(boolean b_btnJornada) {
        this.b_btnJornada = b_btnJornada;
    }

    public boolean isB_btnNovedad() {
        return b_btnNovedad;
    }

    public void setB_btnNovedad(boolean b_btnNovedad) {
        this.b_btnNovedad = b_btnNovedad;
    }

    public boolean isB_btnMaster() {
        return b_btnMaster;
    }

    public void setB_btnMaster(boolean b_btnMaster) {
        this.b_btnMaster = b_btnMaster;
    }

    public boolean isB_btnJFlexible() {
        return b_btnJFlexible;
    }

    public void setB_btnJFlexible(boolean b_btnJFlexible) {
        this.b_btnJFlexible = b_btnJFlexible;
    }

}
