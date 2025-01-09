/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegReportePilonaNovedad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegReportePilonaNovedadFacadeLocal {

    void create(SegReportePilonaNovedad segReportePilonaNovedad);

    void edit(SegReportePilonaNovedad segReportePilonaNovedad);

    void remove(SegReportePilonaNovedad segReportePilonaNovedad);

    SegReportePilonaNovedad find(Object id);

    List<SegReportePilonaNovedad> findAll();

    List<SegReportePilonaNovedad> findRange(int[] range);

    int count();
    
}
