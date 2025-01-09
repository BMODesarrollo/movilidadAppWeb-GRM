package com.movilidad.jsf;

import com.movilidad.ejb.ActividadInfraTipoFacadeLocal;
import com.movilidad.model.ActividadInfraTipo;
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
 *
 * @author Carlos Ballestas
 */
@Named(value = "actividadInfraTipoBean")
@ViewScoped
public class ActividadInfraTipoJSF implements Serializable {

    @EJB
    private ActividadInfraTipoFacadeLocal actividadTipoEjb;

    private ActividadInfraTipo mttoTipo;
    private ActividadInfraTipo selected;
    private String nombre;

    private boolean isEditing;

    private List<ActividadInfraTipo> lstActividadInfraTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstActividadInfraTipos = actividadTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        mttoTipo = new ActividadInfraTipo();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        mttoTipo = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            mttoTipo.setNombre(nombre);
            mttoTipo.setUsername(user.getUsername());
            if (isEditing) {
                mttoTipo.setModificado(MovilidadUtil.fechaCompletaHoy());
                actividadTipoEjb.edit(mttoTipo);
                MovilidadUtil.hideModal("wlvActividadInfraTipo");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                mttoTipo.setEstadoReg(0);
                mttoTipo.setCreado(MovilidadUtil.fechaCompletaHoy());
                actividadTipoEjb.create(mttoTipo);
                lstActividadInfraTipos.add(mttoTipo);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (actividadTipoEjb.findByNombre(selected.getIdActividadInfraTipo(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstActividadInfraTipos.isEmpty()) {
                if (actividadTipoEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public ActividadInfraTipo getMttoTipo() {
        return mttoTipo;
    }

    public void setMttoTipo(ActividadInfraTipo mttoTipo) {
        this.mttoTipo = mttoTipo;
    }

    public ActividadInfraTipo getSelected() {
        return selected;
    }

    public void setSelected(ActividadInfraTipo selected) {
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

    public List<ActividadInfraTipo> getLstActividadInfraTipos() {
        return lstActividadInfraTipos;
    }

    public void setLstActividadInfraTipos(List<ActividadInfraTipo> lstActividadInfraTipos) {
        this.lstActividadInfraTipos = lstActividadInfraTipos;
    }

}
