package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.NovedadTipoInfrastruc;
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
@Named(value = "novTipoInfrastrctJSF")
@ViewScoped
public class NovedadTipoInfrastrctJSF implements Serializable {

    @EJB
    private NovedadTipoInfrastrucFacadeLocal novedadTipoEjb;

    private NovedadTipoInfrastruc novedadTipo;
    private NovedadTipoInfrastruc selected;
    private String nombre;

    private boolean isEditing;

    private List<NovedadTipoInfrastruc> lstNovedadTipoInfrastrcts;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadTipoInfrastrcts = novedadTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        novedadTipo = new NovedadTipoInfrastruc();
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
            if (novedadTipoEjb.findByNombre(nombre.trim(), selected.getIdNovedadTipoInfrastruc()) != null) {
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

    public NovedadTipoInfrastruc getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadTipoInfrastruc novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public NovedadTipoInfrastruc getSelected() {
        return selected;
    }

    public void setSelected(NovedadTipoInfrastruc selected) {
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

    public List<NovedadTipoInfrastruc> getLstNovedadTipoInfrastrcts() {
        return lstNovedadTipoInfrastrcts;
    }

    public void setLstNovedadTipoInfrastrcts(List<NovedadTipoInfrastruc> lstNovedadTipoInfrastrcts) {
        this.lstNovedadTipoInfrastrcts = lstNovedadTipoInfrastrcts;
    }

}
