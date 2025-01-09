package com.movilidad.jsf;

import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.CableRevisionActividadFacadeLocal;
import com.movilidad.ejb.CableRevisionEquipoFacadeLocal;
import com.movilidad.ejb.CableRevisionEstacionFacadeLocal;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.CableRevisionActividad;
import com.movilidad.model.CableRevisionEquipo;
import com.movilidad.model.CableRevisionEstacion;
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
@Named(value = "cableRevisionEstacionBean")
@ViewScoped
public class CableRevisionEstacionBean implements Serializable {

    @EJB
    private CableRevisionEstacionFacadeLocal cableRevisionEstacionEjb;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEjb;
    @EJB
    private CableRevisionEquipoFacadeLocal cableRevisionEquipoEjb;
    @EJB
    private CableRevisionActividadFacadeLocal cableRevisionActividadEjb;

    private CableRevisionEstacion cableRevisionEstacion;
    private CableRevisionEstacion selected;

    private boolean isEditing;

    private Integer i_CableEstacion;
    private Integer i_CableRevisionEquipo;
    private Integer i_CableRevisionActividad;

    private List<CableRevisionEstacion> lstCableRevisionEstaciones;
    private List<CableEstacion> lstCableEstaciones;
    private List<CableRevisionEquipo> lstCableRevisionEquipos;
    private List<CableRevisionActividad> lstCableRevisionActividades;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableRevisionEstaciones = cableRevisionEstacionEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        cableRevisionEstacion = new CableRevisionEstacion();
        lstCableEstaciones = cableEstacionEjb.findByEstadoReg();
        lstCableRevisionEquipos = cableRevisionEquipoEjb.findAllByEstadoReg();
        lstCableRevisionActividades = cableRevisionActividadEjb.findAllByEstadoReg();
        i_CableEstacion = null;
        i_CableRevisionEquipo = null;
        i_CableRevisionActividad = null;
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        i_CableEstacion = selected.getIdCableEstacion().getIdCableEstacion();
        i_CableRevisionEquipo = selected.getIdCableRevisionEquipo().getIdCableRevisionEquipo();
        i_CableRevisionActividad = selected.getIdCableRevisionActividad().getIdCableRevisionActividad();
        lstCableEstaciones = cableEstacionEjb.findByEstadoReg();
        lstCableRevisionEquipos = cableRevisionEquipoEjb.findAllByEstadoReg();
        lstCableRevisionActividades = cableRevisionActividadEjb.findAllByEstadoReg();
        cableRevisionEstacion = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {

            cableRevisionEstacion.setIdCableEstacion(cableEstacionEjb.find(i_CableEstacion));
            cableRevisionEstacion.setIdCableRevisionEquipo(cableRevisionEquipoEjb.find(i_CableRevisionEquipo));
            cableRevisionEstacion.setIdCableRevisionActividad(cableRevisionActividadEjb.find(i_CableRevisionActividad));

            if (isEditing) {
                cableRevisionEstacion.setUsername(user.getUsername());
                cableRevisionEstacion.setModificado(new Date());
                cableRevisionEstacionEjb.edit(cableRevisionEstacion);

                PrimeFaces.current().executeScript("PF('wlvCableRevisionEstaciones').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                cableRevisionEstacion.setUsername(user.getUsername());
                cableRevisionEstacion.setEstadoReg(0);
                cableRevisionEstacion.setCreado(new Date());
                cableRevisionEstacionEjb.create(cableRevisionEstacion);

                lstCableRevisionEstaciones.add(cableRevisionEstacion);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (i_CableEstacion == null) {
            return "Debe seleccionar una estación";
        }
        if (i_CableRevisionEquipo == null) {
            return "Debe seleccionar un equipo";
        }
        if (i_CableRevisionActividad == null) {
            return "Debe seleccionar una actividad";
        }

        if (isEditing) {
            if (cableRevisionEstacionEjb.findByRevisionActividadAndEquipo(selected.getIdCableRevisionEstacion(), i_CableRevisionActividad, i_CableRevisionEquipo, i_CableEstacion) != null) {
                return "YA existe un registro CON los parámetros ingresados";
            }
        } else {
            if (lstCableRevisionEstaciones != null) {
                if (cableRevisionEstacionEjb.findByRevisionActividadAndEquipo(0, i_CableRevisionActividad, i_CableRevisionEquipo, i_CableEstacion) != null) {
                    return "YA existe un registro CON los parámetros ingresados";
                }
            }
        }
        return null;
    }

    public CableRevisionEstacion getCableRevisionEstacion() {
        return cableRevisionEstacion;
    }

    public void setCableRevisionEstacion(CableRevisionEstacion cableRevisionEstacion) {
        this.cableRevisionEstacion = cableRevisionEstacion;
    }

    public CableRevisionEstacion getSelected() {
        return selected;
    }

    public void setSelected(CableRevisionEstacion selected) {
        this.selected = selected;
    }

    public List<CableRevisionEstacion> getLstCableRevisionEstaciones() {
        return lstCableRevisionEstaciones;
    }

    public void setLstCableRevisionEstaciones(List<CableRevisionEstacion> lstCableRevisionEstaciones) {
        this.lstCableRevisionEstaciones = lstCableRevisionEstaciones;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public Integer getI_CableEstacion() {
        return i_CableEstacion;
    }

    public void setI_CableEstacion(Integer i_CableEstacion) {
        this.i_CableEstacion = i_CableEstacion;
    }

    public Integer getI_CableRevisionEquipo() {
        return i_CableRevisionEquipo;
    }

    public void setI_CableRevisionEquipo(Integer i_CableRevisionEquipo) {
        this.i_CableRevisionEquipo = i_CableRevisionEquipo;
    }

    public Integer getI_CableRevisionActividad() {
        return i_CableRevisionActividad;
    }

    public void setI_CableRevisionActividad(Integer i_CableRevisionActividad) {
        this.i_CableRevisionActividad = i_CableRevisionActividad;
    }

    public List<CableEstacion> getLstCableEstaciones() {
        return lstCableEstaciones;
    }

    public void setLstCableEstaciones(List<CableEstacion> lstCableEstaciones) {
        this.lstCableEstaciones = lstCableEstaciones;
    }

    public List<CableRevisionEquipo> getLstCableRevisionEquipos() {
        return lstCableRevisionEquipos;
    }

    public void setLstCableRevisionEquipos(List<CableRevisionEquipo> lstCableRevisionEquipos) {
        this.lstCableRevisionEquipos = lstCableRevisionEquipos;
    }

    public List<CableRevisionActividad> getLstCableRevisionActividades() {
        return lstCableRevisionActividades;
    }

    public void setLstCableRevisionActividades(List<CableRevisionActividad> lstCableRevisionActividades) {
        this.lstCableRevisionActividades = lstCableRevisionActividades;
    }

}
