package com.movilidad.jsf;

import com.movilidad.ejb.DispConciliacionDetFacadeLocal;
import com.movilidad.ejb.DispConciliacionFacadeLocal;
import com.movilidad.ejb.DispConciliacionResumenFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.model.DispConciliacion;
import com.movilidad.model.DispConciliacionDet;
import com.movilidad.model.DispConciliacionResumen;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "rConciliacionMaestroBean")
@ViewScoped
public class ReporteConciliacionMaestroBean implements Serializable {

    @EJB
    private DispConciliacionFacadeLocal conciliacionEjb;
    @EJB
    private DispConciliacionDetFacadeLocal dispConciliacionDetEjb;
    @EJB
    private DispConciliacionResumenFacadeLocal dispConciliacionResumenEjb;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private DispConciliacion conciliacion;
    private Date fecha;
    private Integer idConciliacion;
    private Integer flagConciliado;

    private boolean b_input_causa_estrada;

    private List<DispConciliacion> lstConciliaciones;
    private List<DispConciliacionDet> lstDetalles;
    private List<DispConciliacionResumen> lstResumen;
    private List<VehiculoTipoEstado> lstTipoEstado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void buscarPorConciliado(boolean flagBusqueda) {
        b_input_causa_estrada = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOV_MTTO_INPUT_CAUSA_ENTRADA).equals("1");
        lstConciliaciones = conciliacionEjb.findByFecha(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), flagConciliado);

        if (lstConciliaciones == null || lstConciliaciones.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No se encontraron datos con los parámetros indicados");
            reset();
            return;
        }

        if (flagBusqueda) {
            buscarConciliacionPorId();
        }

    }

    public void buscarConciliacionPorId() {
        if (idConciliacion != null) {
            conciliacion = conciliacionEjb.find(idConciliacion);

            if (conciliacion != null) {
                lstResumen = dispConciliacionResumenEjb.obtenerResumenPorUfAndIdConciliacion(conciliacion.getIdDispConciliacion(),
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), flagConciliado);

                if (lstResumen == null || lstResumen.isEmpty()) {
                    lstDetalles = null;
                    MovilidadUtil.addAdvertenciaMessage("No se encontraron datos con los parámetros indicados");
                    return;
                }

                lstDetalles = dispConciliacionDetEjb.obtenerDetallesByIdDispConciliacion(conciliacion.getIdDispConciliacion(),
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                lstTipoEstado = vehiculoTipoEstadoEjb.findByEstadoReg();
            } else {
                lstDetalles = null;
                lstResumen = null;
                conciliacion = null;
            }
        } else {
            lstDetalles = null;
            lstResumen = null;
            conciliacion = null;
        }
    }

    public void colocarFondoSelectConciliaciones() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String fechaConciliacion = params.get("fecha");
        String id = params.get("id");

        boolean flagAprobado = false, flagNoAprobado = false;

        List<DispConciliacionResumen> resumen = dispConciliacionResumenEjb.findByFechaHora(fechaConciliacion);

        if (resumen != null) {

            for (DispConciliacionResumen dispConciliacionResumen : resumen) {
                if (dispConciliacionResumen.getAprobado() == ConstantsUtil.CON_FLOTA_NO_APROBADO) {
                    flagNoAprobado = true;
                }
                if (dispConciliacionResumen.getAprobado() == ConstantsUtil.CON_FLOTA_APROBADO) {
                    flagAprobado = true;
                }
            }

            if (flagNoAprobado && flagAprobado) {
                MovilidadUtil.runScript("agregarColor('cssPendiente','" + id + "');");
                return;
            }

            if (flagNoAprobado) {
                MovilidadUtil.runScript("agregarColor('cssRed','" + id + "');");
                return;
            }

            if (flagAprobado) {
                MovilidadUtil.runScript("agregarColor('cssGreen','" + id + "');");
            }

        }

    }

    private void reset() {
        lstDetalles = null;
        lstResumen = null;
        conciliacion = null;
        idConciliacion = null;
    }

    public String formatearFecha(Date fecha) {
        return Util.dateTimeFormat(fecha);
    }

    public List<DispConciliacionDet> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<DispConciliacionDet> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public List<DispConciliacionResumen> getLstResumen() {
        return lstResumen;
    }

    public void setLstResumen(List<DispConciliacionResumen> lstResumen) {
        this.lstResumen = lstResumen;
    }

    public DispConciliacion getConciliacion() {
        return conciliacion;
    }

    public void setConciliacion(DispConciliacion conciliacion) {
        this.conciliacion = conciliacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DispConciliacion> getLstConciliaciones() {
        return lstConciliaciones;
    }

    public void setLstConciliaciones(List<DispConciliacion> lstConciliaciones) {
        this.lstConciliaciones = lstConciliaciones;
    }

    public Integer getIdConciliacion() {
        return idConciliacion;
    }

    public void setIdConciliacion(Integer idConciliacion) {
        this.idConciliacion = idConciliacion;
    }

    public List<VehiculoTipoEstado> getLstTipoEstado() {
        return lstTipoEstado;
    }

    public void setLstTipoEstado(List<VehiculoTipoEstado> lstTipoEstado) {
        this.lstTipoEstado = lstTipoEstado;
    }

    public Integer getFlagConciliado() {
        return flagConciliado;
    }

    public void setFlagConciliado(Integer flagConciliado) {
        this.flagConciliado = flagConciliado;
    }

    public boolean isB_input_causa_estrada() {
        return b_input_causa_estrada;
    }

    public void setB_input_causa_estrada(boolean b_input_causa_estrada) {
        this.b_input_causa_estrada = b_input_causa_estrada;
    }

}
