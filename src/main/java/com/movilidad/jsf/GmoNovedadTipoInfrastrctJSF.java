package com.movilidad.jsf;

import com.movilidad.ejb.GmoNovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.GmoNovedadTipoInfrastruc;
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
@Named(value = "gmoNovTipoInfrastrctJSF")
@ViewScoped
public class GmoNovedadTipoInfrastrctJSF implements Serializable {

    @EJB
    private GmoNovedadTipoInfrastrucFacadeLocal novedadTipoEjb;

    private GmoNovedadTipoInfrastruc novedadTipo;
    private GmoNovedadTipoInfrastruc selected;
    private String nombre;

    private boolean isEditing;

    private List<GmoNovedadTipoInfrastruc> lstNovedadTipoInfrastrcts;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadTipoInfrastrcts = novedadTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        novedadTipo = new GmoNovedadTipoInfrastruc();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        novedadTipo = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            novedadTipo.setNombre(nombre);
            novedadTipo.setEstadoReg(0);
            novedadTipo.setUsername(user.getUsername());
            if (isEditing) {
                novedadTipo.setModificado(MovilidadUtil.fechaCompletaHoy());
                novedadTipoEjb.edit(novedadTipo);
                PrimeFaces.current().executeScript("PF('wlvNovedadTipoInfrastrct').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                novedadTipo.setCreado(MovilidadUtil.fechaCompletaHoy());
                novedadTipoEjb.create(novedadTipo);
                lstNovedadTipoInfrastrcts.add(novedadTipo);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (novedadTipoEjb.findByNombre(nombre.trim(), selected.getIdGmoNovedadTipoInfrastruc()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovedadTipoInfrastrcts.isEmpty()) {
                if (novedadTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public GmoNovedadTipoInfrastruc getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(GmoNovedadTipoInfrastruc novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public GmoNovedadTipoInfrastruc getSelected() {
        return selected;
    }

    public void setSelected(GmoNovedadTipoInfrastruc selected) {
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

    public List<GmoNovedadTipoInfrastruc> getLstNovedadTipoInfrastrcts() {
        return lstNovedadTipoInfrastrcts;
    }

    public void setLstNovedadTipoInfrastrcts(List<GmoNovedadTipoInfrastruc> lstNovedadTipoInfrastrcts) {
        this.lstNovedadTipoInfrastrcts = lstNovedadTipoInfrastrcts;
    }

}
