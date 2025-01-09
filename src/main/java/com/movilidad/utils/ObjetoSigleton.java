/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.movilidad.model.GopAlertaPresentacion;
import com.movilidad.model.GopAlertaPresentacionVehiculo;
import com.movilidad.model.GopAlertaTiempoFueraServicio;
import com.movilidad.model.GopParamTiempoCierre;
import com.movilidad.model.PrgTcResumen;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author solucionesit
 */
public class ObjetoSigleton {

    private static GopAlertaPresentacion gopAlertaPresentacion;
    private static GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo;
    private static GopParamTiempoCierre paramTiempoCierre;
    private static Map<String, PrgTcResumen> mapPrgTcResumen;
    private static GopAlertaTiempoFueraServicio gopAlertaTiempoFueraServicio;

    private ObjetoSigleton() {
    }

    public static GopAlertaPresentacion getInstanceGopAlertaPresentacion() {
        return ObjetoSigleton.gopAlertaPresentacion;
    }

    public static GopParamTiempoCierre getInstanceGopParamTiempoCierre() {
        return ObjetoSigleton.paramTiempoCierre;
    }

    public static void setGopAlertaPresentacion(GopAlertaPresentacion gopAlertaPresentacion) {
        ObjetoSigleton.gopAlertaPresentacion = gopAlertaPresentacion;
    }

    public static void setGopParamTiempoCierre(GopParamTiempoCierre paramTiempoCierre) {
        ObjetoSigleton.paramTiempoCierre = paramTiempoCierre;
    }

    public static GopAlertaPresentacionVehiculo getGopAlertaPresentacionVehiculo() {
        return gopAlertaPresentacionVehiculo;
    }

    public static void setGopAlertaPresentacionVehiculo(GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo) {
        ObjetoSigleton.gopAlertaPresentacionVehiculo = gopAlertaPresentacionVehiculo;
    }

    public static Map<String, PrgTcResumen> getMapPrgTcResumen() {
        if (mapPrgTcResumen == null) {
            mapPrgTcResumen = new HashMap<>();
        }
        return mapPrgTcResumen;
    }

    public static void setMapPrgTcResumen(Map<String, PrgTcResumen> mapPrgTcResumen) {
        ObjetoSigleton.mapPrgTcResumen = mapPrgTcResumen;
    }

    public static GopAlertaTiempoFueraServicio getGopAlertaTiempoFueraServicio() {
        return gopAlertaTiempoFueraServicio;
    }

    public static void setGopAlertaTiempoFueraServicio(GopAlertaTiempoFueraServicio gopAlertaTiempoFueraServicio) {
        ObjetoSigleton.gopAlertaTiempoFueraServicio = gopAlertaTiempoFueraServicio;
    }

}
