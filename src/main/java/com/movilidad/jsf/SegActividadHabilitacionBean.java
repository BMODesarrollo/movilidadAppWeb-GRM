package com.movilidad.jsf;

import com.movilidad.ejb.SegActividadHabilitacionFacadeLocal;
import com.movilidad.model.SegActividadHabilitacion;
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
@Named(value = "segActvdHabilitaBean")
@ViewScoped
public class SegActividadHabilitacionBean implements Serializable {

    @EJB
    private SegActividadHabilitacionFacadeLocal actvdHabilitaEjb;

    private SegActividadHabilitacion segActividadHabilitacion;
    private SegActividadHabilitacion selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<SegActividadHabilitacion> lstSegActividadHabilitaciones;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegActividadHabilitaciones = actvdHabilitaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        segActividadHabilitacion = new SegActividadHabilitacion();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segActividadHabilitacion = selected;
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
        segActividadHabilitacion.setNombre(nombre);
        segActividadHabilitacion.setDescripcion(descripcion);
        segActividadHabilitacion.setUsername(user.getUsername());
        if (isEditing) {
            segActividadHabilitacion.setModificado(MovilidadUtil.fechaCompletaHoy());
            actvdHabilitaEjb.edit(segActividadHabilitacion);
            MovilidadUtil.hideModal("wv_actvd_habilita");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segActividadHabilitacion.setEstadoReg(0);
            segActividadHabilitacion.setCreado(MovilidadUtil.fechaCompletaHoy());
            actvdHabilitaEjb.create(segActividadHabilitacion);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (actvdHabilitaEjb.findByNombre(nombre.trim(), selected.getIdSegActividadHabilitacion()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (actvdHabilitaEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegActividadHabilitacion getSegActividadHabilitacion() {
        return segActividadHabilitacion;
    }

    public void setSegActividadHabilitacion(SegActividadHabilitacion segActividadHabilitacion) {
        this.segActividadHabilitacion = segActividadHabilitacion;
    }

    public SegActividadHabilitacion getSelected() {
        return selected;
    }

    public void setSelected(SegActividadHabilitacion selected) {
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

    public List<SegActividadHabilitacion> getLstSegActividadHabilitaciones() {
        return lstSegActividadHabilitaciones;
    }

    public void setLstSegActividadHabilitaciones(List<SegActividadHabilitacion> lstSegActividadHabilitaciones) {
        this.lstSegActividadHabilitaciones = lstSegActividadHabilitaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
