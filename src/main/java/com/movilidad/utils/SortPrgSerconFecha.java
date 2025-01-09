/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.movilidad.model.PrgSercon;
import java.util.Comparator;

/**
 *
 * @author solucionesit
 */
public class SortPrgSerconFecha implements Comparator<PrgSercon> {

    private static SortPrgSerconFecha INSTANCE;

    @Override
    public int compare(PrgSercon a, PrgSercon b) {
        return b.getFecha().compareTo(a.getFecha());
    }

    private SortPrgSerconFecha() {
    }

    public static SortPrgSerconFecha getSortPrgTcFecha() {
        if (INSTANCE == null) {
            INSTANCE = new SortPrgSerconFecha();
        }
        return INSTANCE;
    }

}
