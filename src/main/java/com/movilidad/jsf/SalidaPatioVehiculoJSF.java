/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.MyAppConfirmDepotExitFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.MyAppConfirmDepotExit;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
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
@Named(value = "salidaPatioVehiculoJSF")
@ViewScoped
public class SalidaPatioVehiculoJSF implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private MyAppConfirmDepotExitFacadeLocal appConfirmDepotExitFacadeLocal;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Parametros de busqueda
    private String codigoVeh;
    private Date fecha;
    private String hora;
    // fecha hora para confirmar salida a patio

    // respuesta a la busqueda
    private PrgTc prgTc;

    /**
     * Creates a new instance of SalidaPatioVehiculoJSF
     */
    public SalidaPatioVehiculoJSF() {
        fecha = new Date();
    }

    public void consultarSalidaPatio() {
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
        ConfigEmpresa holguraSalida = configEmpresaFacadeLocal
                .findByLlave(Util.CODE_HORGURA_SALIDAS_PATIO);
        String horaAux = hora;
        if (holguraSalida != null) {
            int toSectToHora = MovilidadUtil.toSecs(hora);
            int holgura = MovilidadUtil.toSecs(holguraSalida.getValor());
            hora = MovilidadUtil.toTimeSec(toSectToHora - holgura);
        }
        prgTc = prgTcFacadeLocal
                .getPrgTcByFechaAndTimeOriginAndStopPointIsDepotExit(veh.getIdVehiculo(), fecha, hora,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        hora = horaAux;
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Vehículo no cuenta con servicios "
                    + "de salida de patio para la fecha y hora suministrada");
            return;
        }
        MovilidadUtil.addSuccessMessage("Servicio encontrado");
    }

    public void confirmarSalidaPatio() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Debe existir un servicio de salida a patio");
            return;
        }
        MyAppConfirmDepotExit validMyAppcd = appConfirmDepotExitFacadeLocal.findByIdPrgTc(prgTc.getIdPrgTc());
        if (validMyAppcd != null) {
            MovilidadUtil.addErrorMessage("La salida de patio fue registrada el "
                    + Util.dateTimeFormat(validMyAppcd.getFechaHora())
                    + ", no se puede confirmar nuevamente");
            return;
        }
        MyAppConfirmDepotExit macde = new MyAppConfirmDepotExit();
        macde.setCreado(MovilidadUtil.fechaCompletaHoy());
        macde.setEstadoReg(0);
        macde.setModificado(MovilidadUtil.fechaCompletaHoy());
        macde.setIdTask(prgTc.getIdPrgTc());
        macde.setIdPrgTc(prgTc);
        macde.setVerbo("RIGEL");
        macde.setUsername(user.getUsername());
        macde.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        macde.setProcesado(0);
        appConfirmDepotExitFacadeLocal.create(macde);
        MovilidadUtil.addSuccessMessage("Salida a patio confirmada con exito");
        reset();
        MovilidadUtil.hideModal("confir-salida-patio-wv");
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
        this.codigoVeh = codigoVeh;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

}
