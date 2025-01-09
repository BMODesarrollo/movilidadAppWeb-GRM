package com.movilidad.jsf;

import com.movilidad.ejb.NovedadMttoTipoFacadeLocal;
import com.movilidad.model.NovedadMttoTipo;
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
@Named(value = "novedadMttoTipoBean")
@ViewScoped
public class NovedadMttoTipoJSF implements Serializable {

    @EJB
    private NovedadMttoTipoFacadeLocal novedadTipoEjb;

    private NovedadMttoTipo mttoTipo;
    private NovedadMttoTipo selected;
    private String nombre;

    private boolean isEditing;

    private List<NovedadMttoTipo> lstNovedadMttoTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadMttoTipos = novedadTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        mttoTipo = new NovedadMttoTipo();
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
                novedadTipoEjb.edit(mttoTipo);
                MovilidadUtil.hideModal("wlvNovedadMttoTipo");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                mttoTipo.setEstadoReg(0);
                mttoTipo.setCreado(MovilidadUtil.fechaCompletaHoy());
                novedadTipoEjb.create(mttoTipo);
                lstNovedadMttoTipos.add(mttoTipo);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (novedadTipoEjb.findByNombre(selected.getIdNovedadMttoTipo(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovedadMttoTipos.isEmpty()) {
                if (novedadTipoEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public NovedadMttoTipo getMttoTipo() {
        return mttoTipo;
    }

    public void setMttoTipo(NovedadMttoTipo mttoTipo) {
        this.mttoTipo = mttoTipo;
    }

    public NovedadMttoTipo getSelected() {
        return selected;
    }

    public void setSelected(NovedadMttoTipo selected) {
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

    public List<NovedadMttoTipo> getLstNovedadMttoTipos() {
        return lstNovedadMttoTipos;
    }

    public void setLstNovedadMttoTipos(List<NovedadMttoTipo> lstNovedadMttoTipos) {
        this.lstNovedadMttoTipos = lstNovedadMttoTipos;
    }

}
