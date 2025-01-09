/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author solucionesit
 */
public class SingletonConfigEmpresa {

    private static Map<String, String> mapConfiMapEmpresa;

    private SingletonConfigEmpresa() {
    }

    public static Map<String, String> getMapConfiMapEmpresa() {
        if (mapConfiMapEmpresa == null) {
            mapConfiMapEmpresa = new HashMap<>();
        }
        return mapConfiMapEmpresa;
    }

    public static void setMapConfiMapEmpresa(Map<String, String> mapConfiMapEmpresa) {
        SingletonConfigEmpresa.mapConfiMapEmpresa = mapConfiMapEmpresa;
    }
}
