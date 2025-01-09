/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Novedad;
import com.movilidad.security.UserExtended;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "bitacoraCVhJSFMB")
@ViewScoped
public class BitacoraCambioVhJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of BitacoraCambioVhJSFManagedBean
     */
    public BitacoraCambioVhJSFManagedBean() {
    }

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Novedad> listaCambioVehiculos;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    private Date fechaInicio;
    private Date fechaFin;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_rol = validarRol();

    @PostConstruct
    public void init() {
        fechaInicio = new Date();
        fechaFin = new Date();
        this.listaCambioVehiculos = this.novedadEjb.findChangeVehiculo(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

    }

    public List<Novedad> getListaCambioVehiculos() {
        return listaCambioVehiculos;
    }

    /*
     * Obtener novedades por rango de fechas 
     */
    public void getByDateRange() {
        listaCambioVehiculos = new ArrayList<>();
        this.listaCambioVehiculos = novedadEjb.findChangeVehiculo(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public List<Object> grupoPM() {
        List<Object> aux_list = new ArrayList<>();
        for (Novedad d : listaCambioVehiculos) {
            aux_list.add(master(d.getIdEmpleado()));
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                return true;
            }
        }
        return false;
    }

    public String master(Empleado empl) {
        if (empl == null) {
            return "N/A";
        }
        String master = "";
        try {
            master = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo();
        } catch (Exception e) {
            return "N/A";
        }

        return master;
    }

    public int puntoView(Novedad n) {
        if (n == null) {
            return 0;
        }
        if (n.getIdNovedadDano() != null) {
            return n.getIdNovedadDano().getIdVehiculoDanoSeveridad().getPuntosPm();
        }
        if (n.getIdMulta() != null) {
            return n.getPuntosPm();
        }
        if (n.getIdNovedadDano() == null && n.getIdMulta() == null) {
            return n.getIdNovedadTipoDetalle().getPuntosPm();
        }

        return 0;
    }

    public void setListaCambioVehiculos(List<Novedad> listaCambioVehiculos) {
        this.listaCambioVehiculos = listaCambioVehiculos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isB_rol() {
        return b_rol;
    }

    public void setB_rol(boolean b_rol) {
        this.b_rol = b_rol;
    }

}
