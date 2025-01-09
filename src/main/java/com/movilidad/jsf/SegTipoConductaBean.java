package com.movilidad.jsf;

import com.movilidad.ejb.SegTipoConductaFacadeLocal;
import com.movilidad.model.SegTipoConducta;
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
@Named(value = "segTipoConductaBean")
@ViewScoped
public class SegTipoConductaBean implements Serializable {

    @EJB
    private SegTipoConductaFacadeLocal segTipoConductaEjb;

    private SegTipoConducta segTipoConducta;
    private SegTipoConducta selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<SegTipoConducta> lstSegTipoConductas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegTipoConductas = segTipoConductaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        segTipoConducta = new SegTipoConducta();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segTipoConducta = selected;
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
        segTipoConducta.setNombre(nombre);
        segTipoConducta.setDescripcion(descripcion);
        segTipoConducta.setUsername(user.getUsername());
        if (isEditing) {
            segTipoConducta.setModificado(MovilidadUtil.fechaCompletaHoy());
            segTipoConductaEjb.edit(segTipoConducta);
            MovilidadUtil.hideModal("wv_tipo_conducta");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segTipoConducta.setEstadoReg(0);
            segTipoConducta.setCreado(MovilidadUtil.fechaCompletaHoy());
            segTipoConductaEjb.create(segTipoConducta);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (segTipoConductaEjb.findByNombre(nombre.trim(), selected.getIdSegTipoConducta()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (segTipoConductaEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegTipoConducta getSegTipoConducta() {
        return segTipoConducta;
    }

    public void setSegTipoConducta(SegTipoConducta segTipoConducta) {
        this.segTipoConducta = segTipoConducta;
    }

    public SegTipoConducta getSelected() {
        return selected;
    }

    public void setSelected(SegTipoConducta selected) {
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

    public List<SegTipoConducta> getLstSegTipoConductas() {
        return lstSegTipoConductas;
    }

    public void setLstSegTipoConductas(List<SegTipoConducta> lstSegTipoConductas) {
        this.lstSegTipoConductas = lstSegTipoConductas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
