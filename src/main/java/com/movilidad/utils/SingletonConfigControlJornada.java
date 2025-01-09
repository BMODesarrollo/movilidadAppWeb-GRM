/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.movilidad.model.ConfigControlJornada;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author solucionesit
 */
public class SingletonConfigControlJornada {

    private static Map<String, ConfigControlJornada> mapConfigControlJornada;

    private SingletonConfigControlJornada() {
    }

    public static Map<String, ConfigControlJornada> getMapConfigControlJornada() {
        if (mapConfigControlJornada == null) {
            mapConfigControlJornada = new HashMap<>();
        }
        return mapConfigControlJornada;
    }

    public static void setMapConfigControlJornada(Map<String, ConfigControlJornada> mapConfigControlJornada) {
        SingletonConfigControlJornada.mapConfigControlJornada = mapConfigControlJornada;
    }
}
