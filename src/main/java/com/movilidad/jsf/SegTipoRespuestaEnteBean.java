package com.movilidad.jsf;

import com.movilidad.ejb.SegTipoRespuestaEnteFacadeLocal;
import com.movilidad.model.SegTipoRespuestaEnte;
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
@Named(value = "segTipoRsptEnteBean")
@ViewScoped
public class SegTipoRespuestaEnteBean implements Serializable {

    @EJB
    private SegTipoRespuestaEnteFacadeLocal tipoRsptEnteEJB;

    private SegTipoRespuestaEnte segTipoRespuestaEnte;
    private SegTipoRespuestaEnte selected;
    private String nombre;
    private String descripcion;
    private boolean reqFecha;

    private boolean isEditing;

    private List<SegTipoRespuestaEnte> lstSegTipoRespuestasEntes;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstSegTipoRespuestasEntes = tipoRsptEnteEJB.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        reqFecha = false;
        segTipoRespuestaEnte = new SegTipoRespuestaEnte();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        segTipoRespuestaEnte = selected;
        reqFecha = selected.getReqFecha() == 1;
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
        segTipoRespuestaEnte.setNombre(nombre);
        segTipoRespuestaEnte.setDescripcion(descripcion);
        segTipoRespuestaEnte.setReqFecha(reqFecha ? 1 : 0);
        segTipoRespuestaEnte.setUsername(user.getUsername());
        if (isEditing) {
            segTipoRespuestaEnte.setModificado(MovilidadUtil.fechaCompletaHoy());
            tipoRsptEnteEJB.edit(segTipoRespuestaEnte);
            MovilidadUtil.hideModal("wv_tipo_rspt_ente");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segTipoRespuestaEnte.setEstadoReg(0);
            segTipoRespuestaEnte.setCreado(MovilidadUtil.fechaCompletaHoy());
            tipoRsptEnteEJB.create(segTipoRespuestaEnte);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (tipoRsptEnteEJB.findByNombre(nombre.trim(), selected.getIdSegTipoRespuestaEnte()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (tipoRsptEnteEJB.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public SegTipoRespuestaEnte getSegTipoRespuestaEnte() {
        return segTipoRespuestaEnte;
    }

    public void setSegTipoRespuestaEnte(SegTipoRespuestaEnte segTipoRespuestaEnte) {
        this.segTipoRespuestaEnte = segTipoRespuestaEnte;
    }

    public SegTipoRespuestaEnte getSelected() {
        return selected;
    }

    public void setSelected(SegTipoRespuestaEnte selected) {
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

    public List<SegTipoRespuestaEnte> getLstSegTipoRespuestasEntes() {
        return lstSegTipoRespuestasEntes;
    }

    public void setLstSegTipoRespuestasEntes(List<SegTipoRespuestaEnte> lstSegTipoRespuestasEntes) {
        this.lstSegTipoRespuestasEntes = lstSegTipoRespuestasEntes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isReqFecha() {
        return reqFecha;
    }

    public void setReqFecha(boolean reqFecha) {
        this.reqFecha = reqFecha;
    }

}
