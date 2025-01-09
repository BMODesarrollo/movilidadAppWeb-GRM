package com.movilidad.jsf;

import com.movilidad.ejb.SegTipoSancionFacadeLocal;
import com.movilidad.model.SegTipoSancion;
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
@Named(value = "segTipoSancionBean")
@ViewScoped
public class SegTipoSancionBean implements Serializable {

    @EJB
    private SegTipoSancionFacadeLocal tipoSancionEjb;

    private SegTipoSancion segTipoSancion;
    private SegTipoSancion selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<SegTipoSancion> lstSegTipoSanciones;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegTipoSanciones = tipoSancionEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        segTipoSancion = new SegTipoSancion();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segTipoSancion = selected;
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
        segTipoSancion.setNombre(nombre);
        segTipoSancion.setDescripcion(descripcion);
        segTipoSancion.setUsername(user.getUsername());
        if (isEditing) {
            segTipoSancion.setModificado(MovilidadUtil.fechaCompletaHoy());
            tipoSancionEjb.edit(segTipoSancion);
            MovilidadUtil.hideModal("wv_tipo_sancion");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segTipoSancion.setEstadoReg(0);
            segTipoSancion.setCreado(MovilidadUtil.fechaCompletaHoy());
            tipoSancionEjb.create(segTipoSancion);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (tipoSancionEjb.findByNombre(nombre.trim(), selected.getIdSegTipoSancion()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (tipoSancionEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegTipoSancion getSegTipoSancion() {
        return segTipoSancion;
    }

    public void setSegTipoSancion(SegTipoSancion segTipoSancion) {
        this.segTipoSancion = segTipoSancion;
    }

    public SegTipoSancion getSelected() {
        return selected;
    }

    public void setSelected(SegTipoSancion selected) {
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

    public List<SegTipoSancion> getLstSegTipoSanciones() {
        return lstSegTipoSanciones;
    }

    public void setLstSegTipoSanciones(List<SegTipoSancion> lstSegTipoSanciones) {
        this.lstSegTipoSanciones = lstSegTipoSanciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
