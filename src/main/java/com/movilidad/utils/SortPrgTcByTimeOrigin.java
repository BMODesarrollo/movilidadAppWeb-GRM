/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.movilidad.model.PrgTc;
import java.util.Comparator;

/**
 *
 * @author solucionesit
 */
public class SortPrgTcByTimeOrigin implements Comparator<PrgTc> {

    private static SortPrgTcByTimeOrigin INSTANCE;

    @Override
    public int compare(PrgTc a, PrgTc b) {
        return MovilidadUtil.toSecs(a.getTimeOrigin()) - MovilidadUtil.toSecs(b.getTimeDestiny());
    }

    private SortPrgTcByTimeOrigin() {
    }

    public static SortPrgTcByTimeOrigin getSortPrgTcByTimeOrigin() {
        if (INSTANCE == null) {
            INSTANCE = new SortPrgTcByTimeOrigin();
        }
        return INSTANCE;
    }

}
