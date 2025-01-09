/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.MyAppConfirmDepotEntryFacadeLocal;
import com.movilidad.ejb.MyAppNovedadParamFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.MyAppConfirmDepotEntry;
import com.movilidad.model.MyAppNovedadParam;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "entradaPatioVehiculoJSF")
@ViewScoped
public class EntradaPatioVehiculoJSF implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private MyAppConfirmDepotEntryFacadeLocal appConfirmDepotEntryFacadeLocal;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @EJB
    private MyAppNovedadParamFacadeLocal myAppNovedadParamFacadeLocal;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private ClasificacionNovedadBean clasificacionNovedadBean;
    @Inject
    private novedadTipoAndDetalleBean tipoAndDetBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    // Parametros de busqueda
    private String codigoVeh;
    private Date fecha;
    private String hora;

    // respuesta a la busqueda
    private PrgTc prgTc;

    /**
     * Creates a new instance of EntradaPatioVehiculoJSF
     */
    public EntradaPatioVehiculoJSF() {
        fecha = new Date();
    }

    public void consultarEntradaPatio() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        if (hora == null) {
            MovilidadUtil.addErrorMessage("Hora es requerido");
            return;
        }
        if (hora.isEmpty()) {
            MovilidadUtil.addErrorMessage("Hora es requerido");
            return;
        }
        Vehiculo veh = vehiculoFacadeLocal.findByCodigo(codigoVeh);
        if (veh == null) {
            MovilidadUtil.addErrorMessage("Vehículo no disponible");
            return;
        }
        ConfigEmpresa holguraEntrada = configEmpresaFacadeLocal
                .findByLlave(Util.CODE_HORGURA_ENTRADAS_PATIO);
        String horaAux = hora;
        if (holguraEntrada != null) {
            int toSectToHora = MovilidadUtil.toSecs(hora);
            int holgura = MovilidadUtil.toSecs(holguraEntrada.getValor());
            hora = MovilidadUtil.toTimeSec(toSectToHora + holgura);
        }
        prgTc = prgTcFacadeLocal
                .getPrgTcByFechaAndTimeOriginAndStopPointIsDepotEntry(veh.getIdVehiculo(),
                        fecha, hora, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        hora = horaAux;
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Vehículo no cuenta con servicios "
                    + "de entrada a patio para la fecha y hora suministrada");
            return;
        }
        MovilidadUtil.addSuccessMessage("Servicio encontrado");
    }

    public void confirmarEntradaPatio() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Debe existir un servicio de entrada a patio");
            return;
        }
        MyAppConfirmDepotEntry validMyAppcd = appConfirmDepotEntryFacadeLocal.findByIdPrgTc(prgTc.getIdPrgTc());
        if (validMyAppcd != null) {
            MovilidadUtil.addErrorMessage("La entrada a patio fue registrada el "
                    + Util.dateTimeFormat(validMyAppcd.getFechaHora())
                    + ", no se puede confirmar nuevamente");
            return;
        }
        MyAppConfirmDepotEntry macde = new MyAppConfirmDepotEntry();
        macde.setCreado(MovilidadUtil.fechaCompletaHoy());
        macde.setEstadoReg(0);
        macde.setModificado(MovilidadUtil.fechaCompletaHoy());
        macde.setIdTask(prgTc.getIdPrgTc());
        macde.setIdPrgTc(prgTc);
        macde.setVerbo("RIGEL");
        macde.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        macde.setProcesado(0);
        appConfirmDepotEntryFacadeLocal.create(macde);
        MovilidadUtil.addSuccessMessage("Entrada a patio confirmada con exito");
        reset();
        MovilidadUtil.hideModal("confir-entrada-patio-wv");
    }

    public void entradaNoProgramada() {
        MyAppNovedadParam novParam = myAppNovedadParamFacadeLocal.
                findByCodigoProceso(ConstantsUtil.COD_NOVEDAD_CAMBIO_VEH);
        if (novParam == null) {
            MovilidadUtil.addErrorMessage("No se encuentra novedad parametrizada");
            return;
        }
        clasificacionNovedadBean.nuevaNovMtto();
        tipoAndDetBean.setNovedadTipo(novParam.getIdNovedadTipoDetalle().getIdNovedadTipo());
        tipoAndDetBean.setNovedadTipoDet(novParam.getIdNovedadTipoDetalle());
        MovilidadUtil.openModal("nov_mtto_wv");
    }

    public void reset() {
        prgTc = null;
        codigoVeh = null;
        hora = null;
    }

    public String getCodigoVeh() {
        return codigoVeh;
    }

    public void setCodigoVeh(String codigoVeh) {
        codigoVeh = codigoVeh;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        hora = hora;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        prgTc = prgTc;
    }

}
