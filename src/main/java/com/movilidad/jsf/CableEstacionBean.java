package com.movilidad.jsf;

import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.model.CableEstacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cableEstacionBean")
@ViewScoped
public class CableEstacionBean implements Serializable {

    @EJB
    private CableEstacionFacadeLocal cableEstacionEjb;

    private CableEstacion cableEstacion;
    private CableEstacion selected;
    private String codigo;
    private String nombre;

    private boolean isEditing;

    private List<CableEstacion> lstEstacionesCable;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstEstacionesCable = cableEstacionEjb.findByEstadoReg();
    }

    public void nuevo() {
        cableEstacion = new CableEstacion();
        selected = null;
        nombre = "";
        codigo = "";
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        codigo = selected.getCodigo();
        nombre = selected.getNombre();
        cableEstacion = selected;
    }

    public void guardar() {

        String validacion = validarDatos();

        if (validacion == null) {
            cableEstacion.setCodigo(codigo);
            cableEstacion.setNombre(nombre);
            cableEstacion.setUsername(user.getUsername());

            if (isEditing) {
                cableEstacion.setModificado(new Date());
                cableEstacionEjb.edit(cableEstacion);
                PrimeFaces.current().executeScript("PF('wvEstaciones').hide();");
                MovilidadUtil.addSuccessMessage("Estación actualizada éxitosamente");
            } else {
                cableEstacion.setCreado(new Date());
                cableEstacionEjb.create(cableEstacion);
                lstEstacionesCable.add(cableEstacion);
                MovilidadUtil.addSuccessMessage("Estación agregada éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (isEditing) {
            if (cableEstacionEjb.findByCodigo(selected.getIdCableEstacion(), codigo.trim()) != null) {
                return "YA existe un registro con el código a ingresar";
            }
            if (cableEstacionEjb.findByNombre(selected.getIdCableEstacion(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }

        } else {

            if (lstEstacionesCable != null) {
                if (cableEstacionEjb.findByCodigo(0, codigo.trim()) != null) {
                    return "YA existe un registro con el código a ingresar";
                }

                if (cableEstacionEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }

            }
        }
        return null;
    }

    public CableEstacion getCableEstacion() {
        return cableEstacion;
    }

    public void setCableEstacion(CableEstacion cableEstacion) {
        this.cableEstacion = cableEstacion;
    }

    public CableEstacion getSelected() {
        return selected;
    }

    public void setSelected(CableEstacion selected) {
        this.selected = selected;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CableEstacion> getLstEstacionesCable() {
        return lstEstacionesCable;
    }

    public void setLstEstacionesCable(List<CableEstacion> lstEstacionesCable) {
        this.lstEstacionesCable = lstEstacionesCable;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

}
