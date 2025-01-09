/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DispEstadoPendActualFacadeLocal;
import com.movilidad.ejb.VehiculoEstadoHistoricoFacadeLocal;
import com.movilidad.model.DispActividad;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoEstadoHistorico;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "vehiculoEstadoHistoricoSaveBean")
@ViewScoped
public class VehiculoEstadoHistoricoSaveBean implements Serializable {

    private VehiculoEstadoHistorico vehiculoEstadoHistorico;

    @EJB
    private VehiculoEstadoHistoricoFacadeLocal estadoHistoricoEJB;

    @EJB
    private DispEstadoPendActualFacadeLocal dispEstadoPendActualEJB;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of VehiculoEstadoHistoricoSaveBean
     */
    public VehiculoEstadoHistoricoSaveBean() {
    }

    /**
     * Método encargado de persistir el historico de una cambio de estado para
     * vehículos.
     *
     * @param vehiculoTipoEstadoDet
     * @param vehiculo
     * @param dispActividad
     * @param idNovedadTipoDetalles
     * @param idDispEstadoPendActual Sí este valor es null, se utilizará el
     * parametro idNovedadTipoDetalles para consultar el DispEstadoPendActual,
     * de lo contrario se creará una instancia con este valor y e settera al
     * objeto vehiculoEstadoHistorico.
     * @param observacion
     * @param idVehiculoTipoEstado
     */
    void guardarEstadoVehiculoHistorico(VehiculoTipoEstadoDet vehiculoTipoEstadoDet,
            Vehiculo vehiculo, DispActividad dispActividad, Integer idNovedadTipoDetalles,
            Integer idDispEstadoPendActual, String observacion, VehiculoTipoEstado idVehiculoTipoEstado, boolean conDispEstadoPendActual) {
        if (vehiculoEstadoHistorico == null) {
            vehiculoEstadoHistorico = new VehiculoEstadoHistorico();
        } else {
            vehiculoEstadoHistorico.setIdVehiculoEstadoHistorico(null);
        }
        vehiculoEstadoHistorico.setIdVehiculoTipoEstadoDet(vehiculoTipoEstadoDet);
        vehiculoEstadoHistorico.setIdVehiculo(vehiculo);
        vehiculoEstadoHistorico.setIdDispActividad(dispActividad);
        if (conDispEstadoPendActual) {
            vehiculoEstadoHistorico.setIdDispEstadoPendActual(
                    idDispEstadoPendActual == null ? getPrimerEstadoPendienteActual(vehiculoTipoEstadoDet.getIdVehiculoTipoEstadoDet())
                            : new DispEstadoPendActual(idDispEstadoPendActual));
        }
        vehiculoEstadoHistorico.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        vehiculoEstadoHistorico.setUsuarioReporta(user.getUsername());
        vehiculoEstadoHistorico.setCreado(MovilidadUtil.fechaCompletaHoy());
        vehiculoEstadoHistorico.setUsername(user.getUsername());
        vehiculoEstadoHistorico.setEstadoReg(0);
        vehiculoEstadoHistorico.setObservacion(observacion);
        vehiculoEstadoHistorico.setIdVehiculoTipoEstado(idVehiculoTipoEstado);
        estadoHistoricoEJB.create(vehiculoEstadoHistorico);

    }

    DispEstadoPendActual getPrimerEstadoPendienteActual(Integer idVehiculoTipoEstadoDet) {
        List<DispEstadoPendActual> lista = dispEstadoPendActualEJB.findFirtStatusByidVehiculoTipoEstadoDetOrAll(idVehiculoTipoEstadoDet, true);
        return lista.isEmpty() ? null : lista.get(0);
    }

}
