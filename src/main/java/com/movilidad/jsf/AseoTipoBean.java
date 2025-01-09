package com.movilidad.jsf;

import com.movilidad.ejb.AseoTipoFacadeLocal;
import com.movilidad.model.AseoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "aseoTipoBean")
@ViewScoped
public class AseoTipoBean implements Serializable {

    @EJB
    private AseoTipoFacadeLocal aseoTipoEjb;

    private AseoTipo aseoTipo;
    private AseoTipo selected;
    private String nombre;

    private boolean isEditing;

    private List<AseoTipo> lstAseoTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAseoTipos = aseoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        aseoTipo = new AseoTipo();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        aseoTipo = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                aseoTipo.setNombre(nombre);
                aseoTipo.setEstadoReg(0);
                aseoTipo.setCreado(new Date());
                aseoTipo.setUsername(user.getUsername());
                aseoTipoEjb.edit(aseoTipo);

                PrimeFaces.current().executeScript("PF('wlvTipoAseo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                aseoTipo.setNombre(nombre);
                aseoTipo.setEstadoReg(0);
                aseoTipo.setCreado(new Date());
                aseoTipo.setUsername(user.getUsername());
                aseoTipoEjb.create(aseoTipo);
                lstAseoTipos.add(aseoTipo);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (aseoTipoEjb.findByNombre(nombre.trim(), selected.getIdAseoTipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAseoTipos.isEmpty()) {
                if (aseoTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AseoTipo getAseoTipo() {
        return aseoTipo;
    }

    public void setAseoTipo(AseoTipo aseoTipo) {
        this.aseoTipo = aseoTipo;
    }

    public AseoTipo getSelected() {
        return selected;
    }

    public void setSelected(AseoTipo selected) {
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

    public List<AseoTipo> getLstAseoTipos() {
        return lstAseoTipos;
    }

    public void setLstAseoTipos(List<AseoTipo> lstAseoTipos) {
        this.lstAseoTipos = lstAseoTipos;
    }

}
