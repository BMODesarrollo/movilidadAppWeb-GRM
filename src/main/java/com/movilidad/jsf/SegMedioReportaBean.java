package com.movilidad.jsf;

import com.movilidad.ejb.SegMedioReportaFacadeLocal;
import com.movilidad.model.SegMedioReporta;
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
@Named(value = "segMedioReportaBean")
@ViewScoped
public class SegMedioReportaBean implements Serializable {

    @EJB
    private SegMedioReportaFacadeLocal segMedioReportaEjb;

    private SegMedioReporta segMedioReporta;
    private SegMedioReporta selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<SegMedioReporta> lstSegGestoresHabilitacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegGestoresHabilitacion = segMedioReportaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        segMedioReporta = new SegMedioReporta();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segMedioReporta = selected;
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
        segMedioReporta.setNombre(nombre);
        segMedioReporta.setDescripcion(descripcion);
        segMedioReporta.setUsername(user.getUsername());
        if (isEditing) {
            segMedioReporta.setModificado(MovilidadUtil.fechaCompletaHoy());
            segMedioReportaEjb.edit(segMedioReporta);
            MovilidadUtil.hideModal("wv_medio_reporta");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segMedioReporta.setEstadoReg(0);
            segMedioReporta.setCreado(MovilidadUtil.fechaCompletaHoy());
            segMedioReportaEjb.create(segMedioReporta);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (segMedioReportaEjb.findByNombre(nombre.trim(), selected.getIdSegMedioReporta()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (segMedioReportaEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegMedioReporta getSegMedioReporta() {
        return segMedioReporta;
    }

    public void setSegMedioReporta(SegMedioReporta segMedioReporta) {
        this.segMedioReporta = segMedioReporta;
    }

    public SegMedioReporta getSelected() {
        return selected;
    }

    public void setSelected(SegMedioReporta selected) {
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

    public List<SegMedioReporta> getLstSegGestoresHabilitacion() {
        return lstSegGestoresHabilitacion;
    }

    public void setLstSegGestoresHabilitacion(List<SegMedioReporta> lstSegGestoresHabilitacion) {
        this.lstSegGestoresHabilitacion = lstSegGestoresHabilitacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
