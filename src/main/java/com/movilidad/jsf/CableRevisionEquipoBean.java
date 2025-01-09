package com.movilidad.jsf;

import com.movilidad.ejb.CableRevisionEquipoFacadeLocal;
import com.movilidad.model.CableRevisionEquipo;
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
@Named(value = "cableRevisionEquipoBean")
@ViewScoped
public class CableRevisionEquipoBean implements Serializable {

    @EJB
    private CableRevisionEquipoFacadeLocal cableEventoTipoEjb;

    private CableRevisionEquipo cableRevisionEquipo;
    private CableRevisionEquipo selected;
    private String nombre;

    private boolean isEditing;

    private List<CableRevisionEquipo> lstCableRevisionEquipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableRevisionEquipos = cableEventoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        cableRevisionEquipo = new CableRevisionEquipo();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        nombre = selected.getNombre();
        cableRevisionEquipo = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            cableRevisionEquipo.setNombre(nombre);
            if (isEditing) {
                cableRevisionEquipo.setModificado(new Date());
                cableRevisionEquipo.setUsername(user.getUsername());
                cableEventoTipoEjb.edit(cableRevisionEquipo);

                PrimeFaces.current().executeScript("PF('wlvCableRevisionEquipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                cableRevisionEquipo.setEstadoReg(0);
                cableRevisionEquipo.setCreado(new Date());
                cableRevisionEquipo.setUsername(user.getUsername());
                cableEventoTipoEjb.create(cableRevisionEquipo);
                lstCableRevisionEquipos.add(cableRevisionEquipo);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (cableEventoTipoEjb.findByNombre(nombre.trim(), selected.getIdCableRevisionEquipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstCableRevisionEquipos.isEmpty()) {
                if (cableEventoTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public CableRevisionEquipo getCableRevisionEquipo() {
        return cableRevisionEquipo;
    }

    public void setCableRevisionEquipo(CableRevisionEquipo cableEventoTipo) {
        this.cableRevisionEquipo = cableEventoTipo;
    }

    public CableRevisionEquipo getSelected() {
        return selected;
    }

    public void setSelected(CableRevisionEquipo selected) {
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

    public List<CableRevisionEquipo> getLstCableRevisionEquipos() {
        return lstCableRevisionEquipos;
    }

    public void setLstCableRevisionEquipos(List<CableRevisionEquipo> lstCableRevisionEquipos) {
        this.lstCableRevisionEquipos = lstCableRevisionEquipos;
    }

}
