/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MttoTareaFacadeLocal;
import com.movilidad.model.MttoTarea;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * En este JSFManagedBean se lleva a cabo la persistencia de los parametros
 * denominados tareas de mantenimiento, los cuales son utilizados en el modulo
 * de Asignación,
 *
 * @author solucionesit
 */
@Named(value = "mttoTareaJSFMB")
@ViewScoped
public class MttoTareaJSFManagedBean implements Serializable {

    private MttoTarea obj_mttoTarea;
    private boolean flag;
    private List<MttoTarea> listaMttoTareas;

    @EJB
    private MttoTareaFacadeLocal mttoTareaEJB;

    /**
     * Creates a new instance of MttoTarea
     */
    public MttoTareaJSFManagedBean() {
    }
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void prepareCreate() {
        flag = false;
        obj_mttoTarea = new MttoTarea();
    }

    @Transactional
    public void guardar() {
        if (obj_mttoTarea.getNombreMttoTarea() == null) {
            MovilidadUtil.addErrorMessage("Digite Nombre de la Tarea");
            return;
        }
        if (obj_mttoTarea.getDescripcionMttoTarea() == null) {
            MovilidadUtil.addErrorMessage("Digite Descripción de la Tarea");
            return;
        }
        if (obj_mttoTarea.getNombreMttoTarea().replaceAll("\\s", "").isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite Nombre de Tarea");
            return;
        }
        if (obj_mttoTarea.getDescripcionMttoTarea().replaceAll("\\s", "").isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite Descripción de la Tarea");
            return;
        }
        if (flag) {
            obj_mttoTarea.setUsername(user.getUsername());
            obj_mttoTarea.setModificado(MovilidadUtil.fechaCompletaHoy());
            mttoTareaEJB.edit(obj_mttoTarea);
            MovilidadUtil.addSuccessMessage("Tarea modificada exitosamente");
            PrimeFaces.current().executeScript("PF('wv_tarea').hide();");
        } else {
            obj_mttoTarea.setCreado(MovilidadUtil.fechaCompletaHoy());
            obj_mttoTarea.setEstadoReg(0);
            obj_mttoTarea.setUsername(user.getUsername());
            mttoTareaEJB.create(obj_mttoTarea);
            MovilidadUtil.addSuccessMessage("Tarea guardada exitosamente");
            PrimeFaces.current().executeScript("PF('wv_tarea').hide();");
        }
        consultar();
    }

    public void consultar() {
        listaMttoTareas = mttoTareaEJB.findAll();
    }

    public void prepareaEditar(MttoTarea tarea) {
        flag = true;
        obj_mttoTarea = tarea;
    }

    public MttoTarea getObj_mttoTarea() {
        return obj_mttoTarea;
    }

    public void setObj_mttoTarea(MttoTarea obj_mttoTarea) {
        this.obj_mttoTarea = obj_mttoTarea;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<MttoTarea> getListaMttoTareas() {
        return listaMttoTareas;
    }

    public void setListaMttoTareas(List<MttoTarea> listaMttoTareas) {
        this.listaMttoTareas = listaMttoTareas;
    }

}
