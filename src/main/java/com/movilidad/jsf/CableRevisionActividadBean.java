package com.movilidad.jsf;

import com.movilidad.ejb.CableRevisionActividadFacadeLocal;
import com.movilidad.model.CableRevisionActividad;
import com.movilidad.model.NovedadTipoCab;
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
@Named(value = "cableRevisionActividadBean")
@ViewScoped
public class CableRevisionActividadBean implements Serializable {

    @EJB
    private CableRevisionActividadFacadeLocal cableRevisionActividadEjb;

    private CableRevisionActividad cableRevisionActividad;
    private CableRevisionActividad selected;
    private String nombre;

    private boolean isEditing;
    private boolean b_notifica;

    private List<CableRevisionActividad> lstCableRevisionActividad;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableRevisionActividad = cableRevisionActividadEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        cableRevisionActividad = new CableRevisionActividad();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        nombre = selected.getNombre();
        cableRevisionActividad = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    public void cargarEmails() {
        if (b_notifica) {
            cableRevisionActividad.setEmails("");
        }
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                cableRevisionActividad.setNotifica(b_notifica ? 1 : 0);
                cableRevisionActividad.setNombre(nombre);
                cableRevisionActividad.setUsername(user.getUsername());
                cableRevisionActividad.setModificado(new Date());
                cableRevisionActividadEjb.edit(cableRevisionActividad);

                PrimeFaces.current().executeScript("PF('wlvCableRevisionActividad').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                cableRevisionActividad.setNotifica(b_notifica ? 1 : 0);
                cableRevisionActividad.setNombre(nombre);
                cableRevisionActividad.setUsername(user.getUsername());
                cableRevisionActividad.setEstadoReg(0);
                cableRevisionActividad.setCreado(new Date());
                cableRevisionActividadEjb.create(cableRevisionActividad);

                lstCableRevisionActividad.add(cableRevisionActividad);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (cableRevisionActividad.getLimiteInferior().compareTo(cableRevisionActividad.getLimiteSuperior()) > 0) {
            return "El límite inferior NO debe ser mayor al superior";
        }

        if (isEditing) {
            if (cableRevisionActividadEjb.findByNombre(nombre.trim(), selected.getIdCableRevisionActividad()) != null) {
                return "YA existe un registro con el nombre ingresado";
            }
        } else {
            if (!lstCableRevisionActividad.isEmpty()) {
                if (cableRevisionActividadEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre ingresado";
                }
            }
        }
        return null;
    }

    public CableRevisionActividad getCableRevisionActividad() {
        return cableRevisionActividad;
    }

    public void setCableRevisionActividad(CableRevisionActividad cableRevisionActividad) {
        this.cableRevisionActividad = cableRevisionActividad;
    }

    public CableRevisionActividad getSelected() {
        return selected;
    }

    public void setSelected(CableRevisionActividad selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<CableRevisionActividad> getLstCableRevisionActividad() {
        return lstCableRevisionActividad;
    }

    public void setLstCableRevisionActividad(List<CableRevisionActividad> lstCableRevisionActividad) {
        this.lstCableRevisionActividad = lstCableRevisionActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isB_notifica() {
        return b_notifica;
    }

    public void setB_notifica(boolean b_notifica) {
        this.b_notifica = b_notifica;
    }

}
