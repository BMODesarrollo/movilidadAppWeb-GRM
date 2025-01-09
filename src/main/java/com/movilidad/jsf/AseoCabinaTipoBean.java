package com.movilidad.jsf;

import com.movilidad.ejb.AseoCabinaTipoFacadeLocal;
import com.movilidad.model.AseoCabinaTipo;
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
@Named(value = "aseoCabinaTipoBean")
@ViewScoped
public class AseoCabinaTipoBean implements Serializable {

    @EJB
    private AseoCabinaTipoFacadeLocal aseoTipoEjb;

    private AseoCabinaTipo aseoCabinaTipo;
    private AseoCabinaTipo selected;
    private String nombre;

    private boolean isEditing;

    private List<AseoCabinaTipo> lstAseoCabinaTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAseoCabinaTipos = aseoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        aseoCabinaTipo = new AseoCabinaTipo();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        aseoCabinaTipo = selected;
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

                aseoCabinaTipo.setNombre(nombre);
                aseoCabinaTipo.setEstadoReg(0);
                aseoCabinaTipo.setCreado(new Date());
                aseoCabinaTipo.setUsername(user.getUsername());
                aseoTipoEjb.edit(aseoCabinaTipo);

                PrimeFaces.current().executeScript("PF('wlvCabinaTipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                aseoCabinaTipo.setNombre(nombre);
                aseoCabinaTipo.setEstadoReg(0);
                aseoCabinaTipo.setCreado(new Date());
                aseoCabinaTipo.setUsername(user.getUsername());
                aseoTipoEjb.create(aseoCabinaTipo);
                lstAseoCabinaTipos.add(aseoCabinaTipo);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (aseoTipoEjb.findByNombre(nombre.trim(), selected.getIdAseoCabinaTipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAseoCabinaTipos.isEmpty()) {
                if (aseoTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AseoCabinaTipo getAseoCabinaTipo() {
        return aseoCabinaTipo;
    }

    public void setAseoCabinaTipo(AseoCabinaTipo aseoCabinaTipo) {
        this.aseoCabinaTipo = aseoCabinaTipo;
    }

    public AseoCabinaTipo getSelected() {
        return selected;
    }

    public void setSelected(AseoCabinaTipo selected) {
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

    public List<AseoCabinaTipo> getLstAseoCabinaTipos() {
        return lstAseoCabinaTipos;
    }

    public void setLstAseoCabinaTipos(List<AseoCabinaTipo> lstAseoCabinaTipos) {
        this.lstAseoCabinaTipos = lstAseoCabinaTipos;
    }

}
