package com.movilidad.jsf;

import com.movilidad.ejb.SegMedioComunicacionFacadeLocal;
import com.movilidad.model.SegMedioComunicacion;
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
@Named(value = "segMedioComunicacionBean")
@ViewScoped
public class SegMedioComunicacionBean implements Serializable {

    @EJB
    private SegMedioComunicacionFacadeLocal medioComunicacionEjb;

    private SegMedioComunicacion medioComunicacion;
    private SegMedioComunicacion selected;
    private String codigo;
    private String serial;

    private boolean isEditing;

    private List<SegMedioComunicacion> lstMediosComunicaciones;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstMediosComunicaciones = medioComunicacionEjb.findByEstadoReg();
    }

    public void nuevo() {
        medioComunicacion = new SegMedioComunicacion();
        selected = null;
        serial = "";
        codigo = "";
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        codigo = selected.getCodigo();
        serial = selected.getSerial();
        medioComunicacion = selected;
    }

    public void guardar() {

        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                medioComunicacion.setCodigo(codigo);
                medioComunicacion.setSerial(serial);
                medioComunicacion.setUsername(user.getUsername());
                medioComunicacion.setModificado(new Date());
                medioComunicacionEjb.edit(medioComunicacion);
                PrimeFaces.current().executeScript("PF('wvMedioComunicacion').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                medioComunicacion.setCodigo(codigo);
                medioComunicacion.setSerial(serial);
                medioComunicacion.setUsername(user.getUsername());
                medioComunicacion.setCreado(new Date());
                medioComunicacionEjb.create(medioComunicacion);
                lstMediosComunicaciones.add(medioComunicacion);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (isEditing) {
            if (medioComunicacionEjb.findByCodigo(codigo.trim(), selected.getIdSegMedioComunicacion()) != null) {
                return "YA existe un registro con el código a ingresar";
            }

            if (medioComunicacionEjb.findBySerial(serial.trim(), selected.getIdSegMedioComunicacion()) != null) {
                return "YA existe un registro con el serial a ingresar";
            }

            if (medioComunicacionEjb.findByImei(medioComunicacion.getImei(), selected.getIdSegMedioComunicacion()) != null) {
                return "YA existe un registro con el IMEI a ingresar";
            }

        } else {

            if (lstMediosComunicaciones != null) {
                if (medioComunicacionEjb.findByCodigo(codigo.trim(), 0) != null) {
                    return "YA existe un registro con el código a ingresar";
                }

                if (medioComunicacionEjb.findBySerial(serial.trim(), 0) != null) {
                    return "YA existe un registro con el serial a ingresar";
                }

                if (medioComunicacionEjb.findByImei(medioComunicacion.getImei(), 0) != null) {
                    return "YA existe un registro con el IMEI a ingresar";
                }

            }

        }
        return null;
    }

    public SegMedioComunicacion getSegMedioComunicacion() {
        return medioComunicacion;
    }

    public void setSegMedioComunicacion(SegMedioComunicacion cableEstacion) {
        this.medioComunicacion = cableEstacion;
    }

    public SegMedioComunicacion getSelected() {
        return selected;
    }

    public void setSelected(SegMedioComunicacion selected) {
        this.selected = selected;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public List<SegMedioComunicacion> getLstMediosComunicaciones() {
        return lstMediosComunicaciones;
    }

    public void setLstMediosComunicaciones(List<SegMedioComunicacion> lstMediosComunicaciones) {
        this.lstMediosComunicaciones = lstMediosComunicaciones;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

}
