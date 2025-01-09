/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopCierreTurnoFacadeLocal;
import com.movilidad.ejb.PrgAsignacionFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.GopCierreTurno;
import com.movilidad.model.PrgAsignacion;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "busToServbusBean")
@ViewScoped
public class AsignarBusToServbusBean implements Serializable {

    /**
     * Creates a new instance of AsignarBusToServbusBean
     */
    public AsignarBusToServbusBean() {
    }

    private PrgTc prgTcServbus;
    private Vehiculo vehiculoExist;
    private String servbusForAsignar;
    private String codigoV = "";
    private Date fechaConsulta;
    private boolean flag_forzar_asignacion = false;
    private PrgTc prgAsignado;
    private PrgTc prgSelected;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @EJB
    private PrgAsignacionFacadeLocal prgAsigEJB;
    @EJB
    private VehiculoFacadeLocal vehEJB;
    @EJB
    private GopCierreTurnoFacadeLocal gopCierreTurnoEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;
    @Inject
    private ValidarFinOperacionBean validarFinOperacionBean;
    @Inject
    private ValidarDocumentacionBean validarDocumentacionBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Método responasble de consultar y cargar el servicio sin vehcíulo que mas
     * tarde servira para actualizar todo los servicios sin Vehículo, con el
     * ServBus que éste contiene. la variable servbusForAsignar es sumistrada
     * desde la vista asignarVehiculoToServbus.
     */
    public void buscarPrgTcSinVehiculo() {
        if (!MovilidadUtil.stringIsEmpty(servbusForAsignar)) {
            /**
             * Consultar objeto PrgTc por medio de servbus sin Vhículo asignado
             */
            prgTcServbus = prgTcEJB.buscarPrgTcSinVehiculo(servbusForAsignar, fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (prgTcServbus == null) {
                MovilidadUtil.addErrorMessage("Servbus digitado no existe.");
                return;
            }
            if (vehiculoExist != null) {
                if (!validarUnidadFuncIgual(vehiculoExist, prgTcServbus)) {
                    MovilidadUtil.addErrorMessage("Vehículo y Servbus no comparten la misma unidad funcional.");
                    prgTcServbus = null;
                    return;
                }
            }
            MovilidadUtil.addSuccessMessage("Servbus listo para recibir asignación");

        }
    }

    /**
     * Método responasble de persistir en BD la asignación por medio de un
     * vehículo y servbus. Vista donde se utiliza: asignarVehiculoToServbus.
     */
    public void guardarAsignacion() {
        if (prgTcServbus == null) {
            MovilidadUtil.addErrorMessage("Consultar Servbus para la asignació");
            return;
        }
        if (vehiculoExist == null) {
            MovilidadUtil.addErrorMessage("Consultar vehículo para la asignación");
            return;
        }
        if ((prgTcServbus.getIdVehiculoTipo().getIdVehiculoTipo() == 1
                && vehiculoExist.getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
            MovilidadUtil.addErrorMessage("Tipología de vehículo incompatible.");
            prgTcServbus = null;
            vehiculoExist = null;
            return;
        }
        int gopUF = vehiculoExist.getIdGopUnidadFuncional().getIdGopUnidadFuncional();

        if (flag_forzar_asignacion) {
            prgTcEJB.updateDesasignarVehiculoServbus(vehiculoExist.getIdVehiculo(), prgAsignado.getServbus(),
                    Util.dateFormat(prgTcServbus.getFecha()),
                    user.getUsername(), gopUF);

        }
        int update = prgTcEJB.asignarBusToServbus(vehiculoExist.getIdVehiculo(),
                prgTcServbus.getServbus(), Util.dateFormat(prgTcServbus.getFecha()),
                user.getUsername(), gopUF);
        /**
         * verificar si se alteraron objetos en el método anterior
         */
        if (update > 0) {

            /**
             * Preparar y guardar nuevo objeto de PrgAsignacion
             */
            PrgAsignacion pa = new PrgAsignacion();
            pa.setCreado(MovilidadUtil.fechaCompletaHoy());
            pa.setFecha(prgTcServbus.getFecha());
            pa.setIdVehiculo(vehiculoExist);
            pa.setServbus(prgTcServbus.getServbus());
            pa.setUsername(user.getUsername());
            pa.setIdGopUnidadFuncional(vehiculoExist.getIdGopUnidadFuncional());
            prgAsigEJB.create(pa);
            MovilidadUtil.addSuccessMessage("Asignación aplicada Para el vehículo " + vehiculoExist.getCodigo());
            vehiculoExist = null;
            prgTcServbus = null;
            servbusForAsignar = "";
            codigoV = "";
        } else {
            MovilidadUtil.addErrorMessage("No se aplicó la asigación para el vehículo " + vehiculoExist.getCodigo());

        }
        MovilidadUtil.hideModal("AsignarDialog");
        prgTcServbus = null;
        vehiculoExist = null;
    }

    /**
     * Método responasble de persistir en BD la asignación por medio de un
     * vehículo y servbus. Vista donde se utiliza:
     * asignarVehiculoToServbusDesasignado.
     */
    public void guardarAsignacionDesasignado() {
        buscarPrgTcSinVehiculo();
        if (prgTcServbus == null) {
            MovilidadUtil.addErrorMessage("Consultar Servbus para la asignación");
            return;
        }
        if (vehiculoExist == null) {
            MovilidadUtil.addErrorMessage("Consultar vehículo para la asignación");
            return;
        }
        if ((prgTcServbus.getIdVehiculoTipo().getIdVehiculoTipo() == 1
                && vehiculoExist.getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
            MovilidadUtil.addErrorMessage("Tipología de vehículo incompatible.");
            prgTcServbus = null;
            vehiculoExist = null;
            return;
        }
        int gopUF = vehiculoExist.getIdGopUnidadFuncional().getIdGopUnidadFuncional();

        int update = prgTcEJB.updateServbusDesasignado(prgSelected.getFecha(), gopUF, prgSelected.getServbus(), vehiculoExist.getIdVehiculo(),
                prgSelected.getTimeOrigin(), user.getUsername());

        if (update > 0) {
            MovilidadUtil.addSuccessMessage("Asignación aplicada para el vehículo " + vehiculoExist.getCodigo());
            vehiculoExist = null;
            prgTcServbus = null;
            servbusForAsignar = "";
            codigoV = "";
            MovilidadUtil.runScript("listarSinBus()");
        } else {
            MovilidadUtil.addErrorMessage("No se aplicó la asigación para el vehículo " + vehiculoExist.getCodigo());

        }
        
        MovilidadUtil.hideModal("AsignarDesasignadoDialog");
        prgTcServbus = null;
        vehiculoExist = null;
        prgSelected = null;
    }

    /**
     * Método responsable de preparar las variables para la asignación de
     * Servbus-Vehículo.
     *
     * Invocado desde la vista prgTc del panel principal.
     *
     * @param fecha
     */
    public void prepareAsignar(Date fecha) {
        flag_forzar_asignacion = false;
        fechaConsulta = fecha;
        boolean flagConciliado = validarFinOperacionBean.validarDiaBloqueado(fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (flagConciliado) {
            MovilidadUtil.addErrorMessage("Día Bloqueado.");
            return;
        }
        if (validarCierreTurno(fecha)) {
            MovilidadUtil.addErrorMessage("Turno Cerrado.");
            return;
        }
        codigoV = "";
        servbusForAsignar = "";
        prgTcServbus = null;
        vehiculoExist = null;
        /**
         * Abrir ventana dialog
         */
        PrimeFaces.current().executeScript("PF('AsignarDialog').show()");
    }

    /**
     * Método responsable de preparar las variables para la asignación de
     * Servbus-Vehículo para tareas que han sido desasignadas de vehículo
     * previamente.
     *
     * Invocado desde la vista prgtcForm del panel principal mediante el clic
     * derecho asignar vehículo.
     *
     * @param fecha
     */
    public void prepareAsignarDesasignado(Date fecha, PrgTc selected) {
        flag_forzar_asignacion = false;
        fechaConsulta = fecha;
        boolean flagConciliado = validarFinOperacionBean.validarDiaBloqueado(fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (flagConciliado) {
            MovilidadUtil.addErrorMessage("Día Bloqueado.");
            return;
        }
        if (validarCierreTurno(fecha)) {
            MovilidadUtil.addErrorMessage("Turno Cerrado.");
            return;
        }
        prgSelected = selected;
        codigoV = "";
        servbusForAsignar = prgSelected.getServbus();
        prgTcServbus = null;
        vehiculoExist = null;
        /**
         * Abrir ventana dialog
         */
        PrimeFaces.current().executeScript("PF('AsignarDesasignadoDialog').show()");
    }

    public boolean validarCierreTurno(Date fecha) {
        //Valida si el cierre de turno inmediatamente anterior es del mismo usuario en sesión
        GopCierreTurno findLastGopCierreTurno = gopCierreTurnoEJB.findLastGopCierreTurno(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        return findLastGopCierreTurno == null ? false : findLastGopCierreTurno.getUserTecControl().equals(user.getUsername());
    }

    /**
     * Permite consultar un vehículo para realizar la asignación en servicio sin
     * Vehículo desde el panel pricipal. codigoV se suministra desde la ventana
     * de asignación, para la asignación de servbus a vehículo.
     */
    public void findVehiculo() {
        flag_forzar_asignacion = false;
        prgAsignado = null;
        if (!MovilidadUtil.stringIsEmpty(codigoV)) {
            vehiculoExist = vehEJB.findVehiculoExist(codigoV, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (vehiculoExist == null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.NO_EXISTE_VEHICULO);
                return;
            }
            if (!vehiculoExist.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado().equals(ConstantsUtil.ON_INT)) {
                vehiculoExist = null;
                MovilidadUtil.addErrorMessage(ConstantsUtil.INOPERATIVO_VEHICULO);
                return;
            }
            if (prgTcServbus != null) {
                if (!validarUnidadFuncIgual(vehiculoExist, prgTcServbus)) {
                    MovilidadUtil.addErrorMessage("Vehículo y Servbus no comparten la misma unidad funcional.");
                    vehiculoExist = null;
                    prgTcServbus = null;
                    return;
                }
            }
            boolean validarDocuVhiculo = validarDocumentacionBean.validarDocuVhiculo(fechaConsulta, vehiculoExist.getIdVehiculo());
            if (validarDocuVhiculo) {
                MovilidadUtil.addErrorMessage("El vehículo tiene documentos vencidos");
                vehiculoExist = null;
                return;
            }
            prgAsignado = prgTcEJB.findVehiculoAsignar(vehiculoExist.getIdVehiculo(), fechaConsulta);
            if (prgAsignado != null) {
                MovilidadUtil.addErrorMessage("Vehículo ya tiene programación asignada");
                if (MovilidadUtil.toSecs(MovilidadUtil.horaActual())
                        > MovilidadUtil.toSecs(prgAsignado.getTimeOrigin())) {
                    vehiculoExist = null;
                    return;
                }
                Long totalServbusVehiculo = prgTcEJB.totalServbusVehiculo(
                        prgAsignado.getFecha(), vehiculoExist.getIdVehiculo(),
                        prgAsignado.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                if (totalServbusVehiculo > 1) {
                    MovilidadUtil.addErrorMessage("No se puede realizar la asignación, el vehículo esta en varios Servbuses");
                    vehiculoExist = null;
                    return;
                }
                MovilidadUtil.addAdvertenciaMessage("Forzar asignación desasigna el vehículo"
                        + " del servbus previamente asignado y lo asigna en el servbus digitado.");
                flag_forzar_asignacion = true;
            } else {
                MovilidadUtil.addSuccessMessage("Vehículo listo para recibir asignación");
            }
        }
    }

    /**
     * Permite consultar un vehículo para realizar la asignación en servicio sin
     * Vehículo desde el panel pricipal. codigoV se suministra desde la ventana
     * de asignación, para la asignación de servbus a vehículo.
     */
    public void findVehiculoDisponible() {
        flag_forzar_asignacion = false;
        prgAsignado = null;
        if (!MovilidadUtil.stringIsEmpty(codigoV)) {
            vehiculoExist = vehEJB.findVehiculoExist(codigoV, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (vehiculoExist == null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.NO_EXISTE_VEHICULO);
                return;
            }
            if (!vehiculoExist.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado().equals(ConstantsUtil.ON_INT)) {
                vehiculoExist = null;
                MovilidadUtil.addErrorMessage(ConstantsUtil.INOPERATIVO_VEHICULO);
                return;
            }
            if (prgTcServbus != null) {
                if (!validarUnidadFuncIgual(vehiculoExist, prgTcServbus)) {
                    MovilidadUtil.addErrorMessage("Vehículo y Servbus no comparten la misma unidad funcional.");
                    vehiculoExist = null;
                    prgTcServbus = null;
                    return;
                }
            }
            boolean validarDocuVhiculo = validarDocumentacionBean.validarDocuVhiculo(fechaConsulta, vehiculoExist.getIdVehiculo());
            if (validarDocuVhiculo) {
                MovilidadUtil.addErrorMessage("El vehículo tiene documentos vencidos");
                vehiculoExist = null;
                return;
            }

            int gopUF = vehiculoExist.getIdGopUnidadFuncional().getIdGopUnidadFuncional();

            Long tareas = prgTcEJB.findVehiculoLibreSinVACAsignar(gopUF,
                    prgSelected.getServbus(), vehiculoExist.getIdVehiculo(), prgSelected.getFecha(), prgSelected.getTimeOrigin());

            if (tareas!=null && tareas > 0) {
                MovilidadUtil.addErrorMessage("Vehículo ya tiene programación asignada que se cruza con los servicios a asignar");
                vehiculoExist = null;
            } else if (tareas!=null && tareas == 0) {
                MovilidadUtil.addSuccessMessage("Vehículo listo para recibir asignación");
            }
        }
    }

    boolean validarUnidadFuncIgual(Vehiculo veh, PrgTc tc) {
        if (veh.getIdGopUnidadFuncional() != null
                && tc.getIdGopUnidadFuncional() != null) {
            return veh.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                    .equals(tc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        }
        return true;
    }

    public String getServbusForAsignar() {
        return servbusForAsignar;
    }

    public void setServbusForAsignar(String servbusForAsignar) {
        this.servbusForAsignar = servbusForAsignar;
    }

    public String getCodigoV() {
        return codigoV;
    }

    public void setCodigoV(String codigoV) {
        this.codigoV = codigoV;
    }

    public boolean isFlag_forzar_asignacion() {
        return flag_forzar_asignacion;
    }

    public void setFlag_forzar_asignacion(boolean flag_forzar_asignacion) {
        this.flag_forzar_asignacion = flag_forzar_asignacion;
    }

    public PrgTc getPrgSelected() {
        return prgSelected;
    }

}
