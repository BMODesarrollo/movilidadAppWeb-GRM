package com.movilidad.jsf;

import com.movilidad.ejb.SegGestionaHabilitacionFacadeLocal;
import com.movilidad.model.SegGestionaHabilitacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Jesús Jiménez
 */
@Named(value = "segGestorHabilitaBean")
@ViewScoped
public class SegGestionaHabilitacionBean implements Serializable {

    @EJB
    private SegGestionaHabilitacionFacadeLocal segGestionaHabilitacionEjb;

    private SegGestionaHabilitacion segGestionaHabilitacion;
    private SegGestionaHabilitacion selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<SegGestionaHabilitacion> lstSegGestoresHabilitacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegGestoresHabilitacion = segGestionaHabilitacionEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        segGestionaHabilitacion = new SegGestionaHabilitacion();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segGestionaHabilitacion = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarDatos()) {
            return;
        }
        segGestionaHabilitacion.setNombre(nombre);
        segGestionaHabilitacion.setDescripcion(descripcion);
        segGestionaHabilitacion.setUsername(user.getUsername());
        if (isEditing) {
            segGestionaHabilitacion.setModificado(MovilidadUtil.fechaCompletaHoy());
            segGestionaHabilitacionEjb.edit(segGestionaHabilitacion);
            MovilidadUtil.hideModal("wv_gestores_habilitacion");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segGestionaHabilitacion.setEstadoReg(0);
            segGestionaHabilitacion.setCreado(MovilidadUtil.fechaCompletaHoy());
            segGestionaHabilitacionEjb.create(segGestionaHabilitacion);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (segGestionaHabilitacionEjb.findByNombre(nombre.trim(), selected.getIdSegGestionaHabilitacion()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (segGestionaHabilitacionEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegGestionaHabilitacion getSegGestionaHabilitacion() {
        return segGestionaHabilitacion;
    }

    public void setSegGestionaHabilitacion(SegGestionaHabilitacion segGestionaHabilitacion) {
        this.segGestionaHabilitacion = segGestionaHabilitacion;
    }

    public SegGestionaHabilitacion getSelected() {
        return selected;
    }

    public void setSelected(SegGestionaHabilitacion selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<SegGestionaHabilitacion> getLstSegGestoresHabilitacion() {
        return lstSegGestoresHabilitacion;
    }

    public void setLstSegGestoresHabilitacion(List<SegGestionaHabilitacion> lstSegGestoresHabilitacion) {
        this.lstSegGestoresHabilitacion = lstSegGestoresHabilitacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
