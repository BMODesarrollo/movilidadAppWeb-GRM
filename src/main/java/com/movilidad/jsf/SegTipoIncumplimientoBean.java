package com.movilidad.jsf;

import com.movilidad.ejb.SegTipoIncumplimientoFacadeLocal;
import com.movilidad.model.SegTipoIncumplimiento;
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
@Named(value = "segTipoIncumplBean")
@ViewScoped
public class SegTipoIncumplimientoBean implements Serializable {

    @EJB
    private SegTipoIncumplimientoFacadeLocal segTipoIncumplimientoEjb;

    private SegTipoIncumplimiento segTipoIncumplimiento;
    private SegTipoIncumplimiento selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<SegTipoIncumplimiento> lstSegTipoIncumplimientos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegTipoIncumplimientos = segTipoIncumplimientoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        segTipoIncumplimiento = new SegTipoIncumplimiento();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segTipoIncumplimiento = selected;
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
        segTipoIncumplimiento.setNombre(nombre);
        segTipoIncumplimiento.setDescripcion(descripcion);
        segTipoIncumplimiento.setUsername(user.getUsername());
        if (isEditing) {
            segTipoIncumplimiento.setModificado(MovilidadUtil.fechaCompletaHoy());
            segTipoIncumplimientoEjb.edit(segTipoIncumplimiento);
            MovilidadUtil.hideModal("wv_tipo_incumplimiento");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segTipoIncumplimiento.setEstadoReg(0);
            segTipoIncumplimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
            segTipoIncumplimientoEjb.create(segTipoIncumplimiento);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (segTipoIncumplimientoEjb.findByNombre(nombre.trim(), selected.getIdSegTipoIncumplimiento()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (segTipoIncumplimientoEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegTipoIncumplimiento getSegTipoIncumplimiento() {
        return segTipoIncumplimiento;
    }

    public void setSegTipoIncumplimiento(SegTipoIncumplimiento segTipoIncumplimiento) {
        this.segTipoIncumplimiento = segTipoIncumplimiento;
    }

 

    public SegTipoIncumplimiento getSelected() {
        return selected;
    }

    public void setSelected(SegTipoIncumplimiento selected) {
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

    public List<SegTipoIncumplimiento> getLstSegTipoIncumplimientos() {
        return lstSegTipoIncumplimientos;
    }

    public void setLstSegTipoIncumplimientos(List<SegTipoIncumplimiento> lstSegTipoIncumplimientos) {
        this.lstSegTipoIncumplimientos = lstSegTipoIncumplimientos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
