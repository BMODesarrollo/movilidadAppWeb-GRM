/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.Empleado;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.Vehiculo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author soluciones-it
 */
@Named(value = "buscadorAccidenteJSF")
@ViewScoped
public class BuscadorAccidenteJSF implements Serializable {

    /**
     * Creates a new instance of BuscadorAccidenteJSF
     */
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetalleFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    //
    private Date inicio;
    private Date fin;
    private Integer idNovedadTipopDetalle;
    private String codVehiculo;
    private Integer codEmpleado;
    private int idGopUF;

    //
    private Accidente accidente;
    private List<Accidente> listAccidente;
    private List<NovedadTipoDetalles> listNovedadTipoDetalles;

    public BuscadorAccidenteJSF() {
    }

    @PostConstruct
    public void init() {
        inicio = new Date();
        fin = new Date();
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        listNovedadTipoDetalles = novedadTipoDetalleFacadeLocal.findTipoAcc();
        buscarAccidente();
    }

    public void buscarAccidente() {
        Integer idEmpleado = null;
        Integer idVehiculo = null;
        cargarUF();
        if (codEmpleado != null) {
            Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(codEmpleado);
            if (empleado != null) {
                idEmpleado = empleado.getIdEmpleado();
            }
        }
        if (!Util.isStringNullOrEmpty(codVehiculo)) {
            Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculo(codVehiculo, idGopUF);
            if (vehiculo != null) {
                idVehiculo = vehiculo.getIdVehiculo();
            }
        }
        if (fin.compareTo(inicio) < 0) {
            MovilidadUtil.addErrorMessage("Fecha fin no puede ser inferior a fecha inicio");
            return;
        }
        listAccidente = accidenteFacadeLocal.findAccidenteAbiertosRecomoto(idVehiculo, idEmpleado, idNovedadTipopDetalle, inicio, fin, idGopUF);
    }

    public void onSetAccidente(Accidente a) {
        accidente = a;
        if (a.getFechaAsistencia() == null) {
            accidente.setFechaAsistencia(new Date());
        }
    }

    public void onSetAccidenteAsistencia(Accidente a) {
        Date d = new Date();
        a.setFechaAsistencia(d);
        accidenteFacadeLocal.edit(a);
        MovilidadUtil.addSuccessMessage("Se confirma asistencia para el accidente "
                + a.getIdNovedadTipoDetalle().getTituloTipoNovedad()
                + " a la fecha: " + Util.dateTimeFormat(d));
    }

    public void limpiar() {
        inicio = new Date();
        fin = new Date();
        codEmpleado = null;
        codVehiculo = null;
        idNovedadTipopDetalle = null;
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Integer getIdNovedadTipopDetalle() {
        return idNovedadTipopDetalle;
    }

    public void setIdNovedadTipopDetalle(Integer idNovedadTipopDetalle) {
        this.idNovedadTipopDetalle = idNovedadTipopDetalle;
    }

    public String getCodVehiculo() {
        return codVehiculo;
    }

    public void setCodVehiculo(String codVehiculo) {
        this.codVehiculo = codVehiculo;
    }

    public Integer getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Integer codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public List<Accidente> getListAccidente() {
        return listAccidente;
    }

    public void setListAccidente(List<Accidente> listAccidente) {
        this.listAccidente = listAccidente;
    }

    public List<NovedadTipoDetalles> getListNovedadTipoDetalles() {
        return listNovedadTipoDetalles;
    }

}
