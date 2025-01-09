/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadPrgTcFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.PrgClasificacionMotivoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResponsableFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadPrgTc;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PrgClasificacionMotivo;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResponsable;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "prgServiciosSinAsignarJSF")
@ViewScoped
public class PrgServiciosSinAsignarJSF implements Serializable {

    @EJB
    private NovedadTipoFacadeLocal novedadTipoFacadeLocal;
    @EJB
    private NovedadTipoDetallesFacadeLocal novDetallesFaceLocal;
    @EJB
    private PrgTcResponsableFacadeLocal prgTcRespEJB;
    @EJB
    private PrgClasificacionMotivoFacadeLocal clasificacionMotivoFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private NovedadPrgTcFacadeLocal NovPrgTcEJB;
    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    //
    private List<NovedadTipo> listNovedadTipo;
    private List<NovedadTipoDetalles> listNovedadTipoDet;
    private List<PrgTcResponsable> lstResponsable;
    private List<PrgClasificacionMotivo> lstClasificacion;
    private List<PrgTc> listPrgTc;

    private Integer idNovedadTipo;
    private Integer idNovedadTipoDetalle;
    private Integer idResponsable;
    private Integer idClasificacion;
    private String observacion;
    private String codVehiculo;
    private PrgTc prgTc;
    private boolean validProcessEliminar;
    private Date fecha;

    private int idGopUF;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of PrgServiciosSinAsignarJSF
     */
    public PrgServiciosSinAsignarJSF() {
    }

    @PostConstruct
    void init() {
        listNovedadTipo = novedadTipoFacadeLocal.findAllEstadoReg();
        lstResponsable = prgTcRespEJB.getPrgResponsables();
        validProcessEliminar = false;
    }

    public void consultarServiciosSinAisgnar() {
        cargarUF();
        if (idGopUF == 0) {
            listPrgTc = null;
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            MovilidadUtil.hideModal("serviciosSinAsignarWV");
            return;
        }
        listPrgTc = prgTcFacadeLocal.findServiciosSinAsignarByFechaAndGopUf(fecha, idGopUF);
//        resetDataNovedad();
        resetPrgTc();
    }

    public void cargarNovedadTipoDetalles() {
        if (idNovedadTipo == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        idNovedadTipoDetalle = null;
        listNovedadTipoDet = novDetallesFaceLocal.findByTipoNovedad(idNovedadTipo);
    }

    public void cargarNovedadClasificaicon() {
        if (idResponsable == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        idClasificacion = null;
        lstClasificacion = clasificacionMotivoFacadeLocal.findByIdPrgResponsableEstadoReg(idResponsable);
    }

    public void resetNovedadTipoDetalles() {
        if (idNovedadTipo == null) {
            idNovedadTipoDetalle = null;
            listNovedadTipoDet = null;
        }
    }

    public void resetNovedadClasificacion() {
        if (idResponsable == null) {
            idClasificacion = null;
            lstClasificacion = null;
        }
    }

    public void prepareAsignarServicio() {
        validProcessEliminar = false;
        codVehiculo = null;
    }

    public void asignarVehiculo() {
        cargarUF();
        if (idGopUF == 0) {
            listPrgTc = null;
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            MovilidadUtil.hideModal("serviciosSinAsignarWV");
            return;
        }
        if (Util.isStringNullOrEmpty(codVehiculo)) {
            MovilidadUtil.addErrorMessage("Código de vehículo es requerido");
            return;
        }
        Vehiculo vehiculo = vehiculoFacadeLocal.findVehiculoExist(codVehiculo, idGopUF);
        if (validDataAsignar(vehiculo)) {
            MovilidadUtil.addErrorMessage("No se realizó ningun cambio");
            return;
        }
        prgTc.setIdVehiculo(vehiculo);
        prgTc.setUsername(user.getUsername());
        prgTc.setModificado(MovilidadUtil.fechaCompletaHoy());
        prgTcFacadeLocal.edit(prgTc);
        consultarServiciosSinAisgnar();
        validProcessEliminar = false;
        codVehiculo = null;
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void prepareEliminarServicio() {
        validProcessEliminar = true;
    }

    public void elimianarServicio(PrgTc tc) {
        setPrgTc(tc);
        cargarUF();
        if (idGopUF == 0) {
            listPrgTc = null;
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            MovilidadUtil.hideModal("serviciosSinAsignarWV");
            return;
        }
        if (prgTc == null) {
            validProcessEliminar = false;
            MovilidadUtil.addErrorMessage("Debe seleccionar un servicio");
            return;
        }
        if (prgTc.getEstadoOperacion() != null) {
            if (prgTc.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_SERVICIO_5)
                    || prgTc.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_SERVICIO_8)
                    || prgTc.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_SERVICIO_99)) {
                MovilidadUtil.addErrorMessage("Servicio ha sido eliminado");
                listPrgTc = prgTcFacadeLocal.findServiciosSinAsignarByFechaAndGopUf(fecha, idGopUF);
//                validProcessEliminar = false;
                codVehiculo = null;
//                resetDataNovedad();
                return;
            }
        }
        if (validDataNovedad()) {
            MovilidadUtil.addErrorMessage("No se realizó ningun cambio");
            return;
        }
        NovedadTipoDetalles ntd = novDetallesFaceLocal.find(idNovedadTipoDetalle);
        if (ntd == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo detalle de novedad");
            return;
        }
        Date d = MovilidadUtil.fechaCompletaHoy();
        PrgTcResponsable prgTcResponsable = new PrgTcResponsable(idResponsable);
        prgTc.setEstadoOperacion(ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        prgTc.setUsername(user.getUsername());
        prgTc.setModificado(d);
        prgTc.setObservaciones(observacion);
        prgTc.setIdPrgClasificacionMotivo(idClasificacion != null ? new PrgClasificacionMotivo(idClasificacion) : null);
        prgTc.setIdPrgTcResponsable(prgTcResponsable);
        prgTcFacadeLocal.edit(prgTc);

        // crear novedad
        Novedad nov = new Novedad();
        nov.setCreado(d);
        nov.setModificado(d);
        nov.setFecha(d);
        nov.setEstadoReg(ConstantsUtil.OFF_INT);
        nov.setEstadoNovedad(ConstantsUtil.ON_INT);
        nov.setIdEmpleado(prgTc.getIdEmpleado());
        nov.setIdNovedadTipoDetalle(ntd);
        nov.setIdNovedadTipo(ntd.getIdNovedadTipo());
        nov.setObservaciones(observacion);
        nov.setUsername(user.getUsername());
        nov.setIdGopUnidadFuncional(prgTc.getIdGopUnidadFuncional());
        if (ntd.getAfectaPm() == 1) {
            nov.setPuntosPm(ntd.getPuntosPm());
            nov.setPuntosPmConciliados(ntd.getPuntosPm());
            nov.setLiquidada(0);
            nov.setProcede(1);
        } else {
            nov.setPuntosPm(0);
            nov.setPuntosPmConciliados(0);
            nov.setLiquidada(0);
            nov.setProcede(0);
        }
        novedadFacadeLocal.create(nov);
        // crear novedad de prgTc
        NovedadPrgTc np = new NovedadPrgTc();
        np.setIdNovedad(nov);
        np.setIdPrgTc(prgTc);
        np.setIdEmpleado(prgTc.getIdEmpleado());
        np.setToStop(prgTc.getToStop());
        np.setFromStop(prgTc.getFromStop());
        np.setIdPrgTcResponsable(prgTcResponsable);
        np.setObservaciones(observacion);
        np.setIdGopUnidadFuncional(prgTc.getIdGopUnidadFuncional());
        np.setTimeOrigin(prgTc.getTimeOrigin());
        np.setTimeDestiny(prgTc.getTimeDestiny());
        np.setEstadoOperacion(prgTc.getEstadoOperacion());
        np.setDistancia(prgTcFacadeLocal.findDistandeByFromStopAndToStop(prgTc.getFromStop().getIdPrgStoppoint(),
                prgTc.getToStop().getIdPrgStoppoint()));
        np.setUsername(user.getUsername());
        np.setCreado(d);
        np.setEstadoReg(ConstantsUtil.CODE_ESTADO_REG_ACTIVO);
        NovPrgTcEJB.create(np);
//        resetDataNovedad();
        resetPrgTc();
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
        consultarServiciosSinAisgnar();
    }

    private boolean validDataAsignar(Vehiculo vehiculo) {
        if (prgTc == null) {
            validProcessEliminar = false;
            MovilidadUtil.addErrorMessage("Debe seleccionar un servicio");
            return true;
        }

        if (vehiculo == null) {
            MovilidadUtil.addErrorMessage(ConstantsUtil.NO_EXISTE_VEHICULO);
            return true;
        }
        if (!vehiculo.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado().equals(ConstantsUtil.ON_INT)) {
            vehiculo = null;
            MovilidadUtil.addErrorMessage(ConstantsUtil.INOPERATIVO_VEHICULO);
            return true;
        }
        if (!vehiculo.getIdVehiculoTipo().equals(prgTc.getIdVehiculoTipo())) {
            MovilidadUtil.addErrorMessage("La tipología del vehículo "
                    + vehiculo.getCodigo()
                    + " no corresponde con la del servicio seleccionado");
            return true;
        }
        if (prgTc.getEstadoOperacion() != null) {
            if (prgTc.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_SERVICIO_5)
                    || prgTc.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_SERVICIO_8)
                    || prgTc.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_SERVICIO_99)) {
                MovilidadUtil.addErrorMessage("Servicio ha sido eliminado");
                listPrgTc = prgTcFacadeLocal.findServiciosSinAsignarByFechaAndGopUf(fecha, idGopUF);
                validProcessEliminar = false;
                codVehiculo = null;
                return true;
            }
        }
        /**
         * Se verifica si el vehículo consultado tiene servicio pendientes que
         * se crucen en tiempos de ejecución con los servicio seleccionados en
         * vista
         */
        if (validarVehiculoOcupado(prgTc, vehiculo.getCodigo(), fecha,
                prgTc.getIdGopUnidadFuncional() == null ? 0
                : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
            MovilidadUtil.addErrorMessage("No se realizó la asignación");
            return true;
        }

//        PrgTc prgTcEjecucion = prgTcFacadeLocal.validarServicioEnEjecucionByHoraAndVehiculo(prgTc.getTimeOrigin(),
//                vehiculo.getCodigo(),
//                fecha, prgTc.getIdGopUnidadFuncional() == null ? 0 : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
//        if (prgTcEjecucion != null) {
//            MovilidadUtil.addErrorMessage("Vehículo con servicios pendientes");
//            MovilidadUtil.addErrorMessage("No se realizó la asignación");
//            return true;
//        } else {
//            prgTcEjecucion = prgTcFacadeLocal.validarServicioEnEjecucionByHoraAndVehiculo(prgTc.getTimeOrigin(),
//                    vehiculo.getCodigo(),
//                    fecha, prgTc.getIdGopUnidadFuncional() == null ? 0 : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
//            if (prgTcEjecucion != null) {
//                MovilidadUtil.addErrorMessage("Vehículo con servicios pendientes");
//                MovilidadUtil.addErrorMessage("No se realizó la asignación");
//                return true;
//            }
//        }
        return false;
    }

    private boolean validDataNovedad() {
        if (idNovedadTipo == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return true;
        }
        if (idNovedadTipoDetalle == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo detalle de novedad");
            return true;
        }
        if (idResponsable == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un responsable");
            return true;
        }
        if (idClasificacion == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una clasificación");
            return true;
        }
        return false;
    }

    private void resetDataNovedad() {
        idNovedadTipo = null;
        idNovedadTipoDetalle = null;
        idResponsable = null;
        idClasificacion = null;
        observacion = null;
        resetNovedadTipoDetalles();
    }

    private void resetPrgTc() {
        prgTc = null;
        validProcessEliminar = false;
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    private boolean validarVehiculoOcupado(PrgTc servicioSeleccionado, String codVehiculo, Date fecha, int idUnidaFunc) {
        List<PrgTc> serviciosPendientesPorVehiculo = prgTcFacadeLocal.serviciosPendientesPorVehiculo(codVehiculo, fecha, idUnidaFunc);
        for (PrgTc pend : serviciosPendientesPorVehiculo) {
            if (MovilidadUtil.horaBetweenSinIgual(servicioSeleccionado.getTimeOrigin(), pend.getTimeOrigin(), pend.getTimeDestiny())
                    || MovilidadUtil.horaBetweenSinIgual(servicioSeleccionado.getTimeDestiny(), pend.getTimeOrigin(), pend.getTimeDestiny())) {
                MovilidadUtil.addErrorMessage("Vehículo con servicios pendientes. " + pend.getTimeOrigin() + " " + pend.getTimeDestiny());
                return true;
            }
        }
        return false;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<NovedadTipo> getListNovedadTipo() {
        return listNovedadTipo;
    }

    public void setListNovedadTipo(List<NovedadTipo> listNovedadTipo) {
        this.listNovedadTipo = listNovedadTipo;
    }

    public List<NovedadTipoDetalles> getListNovedadTipoDet() {
        return listNovedadTipoDet;
    }

    public void setListNovedadTipoDet(List<NovedadTipoDetalles> listNovedadTipoDet) {
        this.listNovedadTipoDet = listNovedadTipoDet;
    }

    public List<PrgTcResponsable> getLstResponsable() {
        return lstResponsable;
    }

    public void setLstResponsable(List<PrgTcResponsable> lstResponsable) {
        this.lstResponsable = lstResponsable;
    }

    public List<PrgClasificacionMotivo> getLstClasificacion() {
        return lstClasificacion;
    }

    public void setLstClasificacion(List<PrgClasificacionMotivo> lstClasificacion) {
        this.lstClasificacion = lstClasificacion;
    }

    public List<PrgTc> getListPrgTc() {
        return listPrgTc;
    }

    public void setListPrgTc(List<PrgTc> listPrgTc) {
        this.listPrgTc = listPrgTc;
    }

    public Integer getIdNovedadTipo() {
        return idNovedadTipo;
    }

    public void setIdNovedadTipo(Integer idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public Integer getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(Integer idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Integer getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodVehiculo() {
        return codVehiculo;
    }

    public void setCodVehiculo(String codVehiculo) {
        this.codVehiculo = codVehiculo;
    }

    public boolean isValidProcessEliminar() {
        return validProcessEliminar;
    }

    public void setValidProcessEliminar(boolean validProcessEliminar) {
        this.validProcessEliminar = validProcessEliminar;
    }

}
