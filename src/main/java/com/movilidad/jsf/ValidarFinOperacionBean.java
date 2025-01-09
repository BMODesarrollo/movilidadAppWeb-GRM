/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopParamTiempoCierreFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.model.GopParamTiempoCierre;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "validarFinOperacionBean")
@ViewScoped
public class ValidarFinOperacionBean implements Serializable {

    @EJB
    private PrgTcResumenFacadeLocal prgTcResumenEJB;

    @EJB
    private GopParamTiempoCierreFacadeLocal gopParamTiempoCierreEJB;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    private boolean isTecControl;

    /**
     * Creates a new instance of ValidarFinOperacionBean
     */
    public ValidarFinOperacionBean() {
    }

    /**
     * Carga la variable "flagConciliado" con un valor boolean para controlar
     * que se pueda hacer gestion sobre los servicios para una fecha
     * seleccionada en el panel principal. la fecha es seleccionada desde le
     * panel principal
     *
     * @param fecha
     * @return
     */
    public boolean validarDiaBloqueado(Date fecha, int idGopUnidadFuncional) {
        PrgTcResumen prgTcResumen = getPrgTcResumenSingleton(fecha, idGopUnidadFuncional);
        /**
         * Valida si hay programación montada para la fecha
         */
        if (prgTcResumen == null) {
            MovilidadUtil.addErrorMessage("No existe resumen de servicios programados");
            MovilidadUtil.updateComponent("msgs");
            return true;
        }
        if (isTecControl) {
            /**
             * Valida si la fecha ya está conciliada
             */
            if (prgTcResumen.getConciliado() != null && (prgTcResumen.getConciliado() == 2 || prgTcResumen.getConciliado() == 1)) {
                MovilidadUtil.addErrorMessage("Fecha Conciliada");
                MovilidadUtil.updateComponent("msgs");
                return true;
            }
            /**
             * Valida de la fecha está desbloqueda.
             */
            if (prgTcResumen.getConciliado() != null && prgTcResumen.getConciliado() == 0) {
                return false;
            }
            PrgTc findUltimoServicioProgramado = prgTcEJB.findUltimoServicioProgramado(fecha, idGopUnidadFuncional);
            /**
             * Valida si hay un servicio ultimo de la operación
             */
            if (findUltimoServicioProgramado == null) {
                MovilidadUtil.addErrorMessage("No existe ultimo servicio");
                MovilidadUtil.updateComponent("msgs");
                return false;
            }
            /**
             * Hora del momento en la cual el usuario realiza alguna acción
             * referente a la gestión de servicios
             */
            String horaDiaHoy = MovilidadUtil.horaActual();

            /**
             * Valida si la fecha seleccionada por el usuario en el panel
             * princial, es anterior a la fecha actual del sistema.
             */
            if (MovilidadUtil.fechaHoy().after(fecha)) {
                horaDiaHoy = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("24:00:00")
                        + MovilidadUtil.toSecs(horaDiaHoy));
            }
            /**
             * Valida si hay parametrización de de tiempo de
             * GopParamTiempoCierre.
             *
             * Se utiliza para tiempo de holgura en el cual se finaliza el
             * ultimo servicio de la operación y el tecnico pueda realizar
             * gestión de servicios.
             */
            if (getGopParamTiempoCierre() != null) {

                horaDiaHoy = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaDiaHoy)
                        - MovilidadUtil.toSecs(getGopParamTiempoCierre().getTiempo()));
            }
            /**
             * Se valida la acción que se quiere realizar por el usuario está
             * dentro del rango de opeación.
             */
            if (MovilidadUtil.toSecs(findUltimoServicioProgramado.getTimeDestiny())
                    < MovilidadUtil.toSecs(horaDiaHoy)) {
                MovilidadUtil.addErrorMessage("Operación Cerrada para la fecha");
                MovilidadUtil.updateComponent("msgs");
                return true;
            }
        }

        return false;
    }

    public PrgTcResumen getPrgTcResumenSingleton(Date fecha, int idGopUnidadFuncional) {
        PrgTcResumen prgTcResumenResult = ObjetoSigleton.getMapPrgTcResumen().get(Util.dateFormat(fecha) + "_" + idGopUnidadFuncional);
        if (prgTcResumenResult == null) {
            prgTcResumenResult = prgTcResumenEJB.findByFecha(fecha, idGopUnidadFuncional);
            ObjetoSigleton.getMapPrgTcResumen().put(Util.dateFormat(fecha) + "_" + idGopUnidadFuncional, prgTcResumenResult);
        }
        return prgTcResumenResult;
    }

    public GopParamTiempoCierre getGopParamTiempoCierre() {
        if (ObjetoSigleton.getInstanceGopParamTiempoCierre() == null) {
            GopParamTiempoCierre paramTiempoCierre = gopParamTiempoCierreEJB.findParam();
            ObjetoSigleton.setGopParamTiempoCierre(paramTiempoCierre);
        }
        return ObjetoSigleton.getInstanceGopParamTiempoCierre();
    }

    public boolean isIsTecControl() {
        return isTecControl;
    }

    public void setIsTecControl(boolean isTecControl) {
        this.isTecControl = isTecControl;
    }

}
