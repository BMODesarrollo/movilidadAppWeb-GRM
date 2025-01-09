package com.movilidad.jsf;

import com.movilidad.ejb.SegPilonaFacadeLocal;
import com.movilidad.model.SegPilona;
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
@Named(value = "segPilonaBean")
@ViewScoped
public class SegPilonaBean implements Serializable {

    @EJB
    private SegPilonaFacadeLocal segPilonaEjb;

    private SegPilona segPilona;
    private SegPilona selected;
    private String codigo;
    private String nombre;
    private String c_latitud;
    private String c_longitud;

    private boolean isEditing;

    private List<SegPilona> lstSegPilonas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstSegPilonas = segPilonaEjb.findByEstadoReg();
    }

    public void nuevo() {
        segPilona = new SegPilona();
        selected = null;
        nombre = "";
        codigo = "";
        c_latitud = "";
        c_longitud = "";
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        codigo = selected.getCodigo();
        nombre = selected.getNombre();
        c_longitud = selected.getLongitud();
        c_latitud = selected.getLatitud();

        segPilona = selected;
    }

    @Transactional
    public void guardar() {

        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                segPilona.setLatitud(c_latitud);
                segPilona.setLongitud(c_longitud);
                segPilona.setCodigo(codigo);
                segPilona.setNombre(nombre);
                segPilona.setUsername(user.getUsername());
                segPilona.setModificado(new Date());
                segPilonaEjb.edit(segPilona);
                PrimeFaces.current().executeScript("PF('wvPilonas').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                segPilona.setLatitud(c_latitud);
                segPilona.setLongitud(c_longitud);
                segPilona.setCodigo(codigo);
                segPilona.setNombre(nombre);
                segPilona.setUsername(user.getUsername());
                segPilona.setCreado(new Date());
                segPilonaEjb.create(segPilona);
                lstSegPilonas.add(segPilona);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (isEditing) {
            if (!segPilona.getCodigo().equals(codigo)) {
                if (segPilonaEjb.findByCodigo(codigo.trim()) != null) {
                    return "YA existe un registro con el código a ingresar";
                }
            }
            if (!segPilona.getNombre().equals(nombre)) {
                if (segPilonaEjb.findByNombre(nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }

        } else {

            if (lstSegPilonas != null) {
                if (segPilonaEjb.findByCodigo(codigo.trim()) != null) {
                    return "YA existe un registro con el código a ingresar";
                }

                if (segPilonaEjb.findByNombre(nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }

            }
        }
        return null;
    }

    public SegPilona getSegPilona() {
        return segPilona;
    }

    public void setSegPilona(SegPilona segPilona) {
        this.segPilona = segPilona;
    }

    public SegPilona getSelected() {
        return selected;
    }

    public void setSelected(SegPilona selected) {
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

    public List<SegPilona> getLstSegPilonas() {
        return lstSegPilonas;
    }

    public void setLstSegPilonas(List<SegPilona> lstSegPilonas) {
        this.lstSegPilonas = lstSegPilonas;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public String getC_latitud() {
        return c_latitud;
    }

    public void setC_latitud(String c_latitud) {
        this.c_latitud = c_latitud;
    }

    public String getC_longitud() {
        return c_longitud;
    }

    public void setC_longitud(String c_longitud) {
        this.c_longitud = c_longitud;
    }

}
