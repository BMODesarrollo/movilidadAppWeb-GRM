/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegReportePilona;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegReportePilonaFacadeLocal {

    void create(SegReportePilona segReportePilona);

    void edit(SegReportePilona segReportePilona);

    void remove(SegReportePilona segReportePilona);

    SegReportePilona find(Object id);

    List<SegReportePilona> findAll();

    List<SegReportePilona> findRange(int[] range);

    int count();
    
    List<SegReportePilona> findRanfoFechaEstadoReg(Date desde, Date hasta);
}
