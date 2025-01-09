/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdResponsables;
import com.movilidad.model.ReporteSemanaActualMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface ReporteSemanaMotivoFacadeLocal {

    void create(ReporteSemanaActualMotivo reporteSemanaActualMotivo);

    void edit(ReporteSemanaActualMotivo reporteSemanaActualMotivo);

    void remove(ReporteSemanaActualMotivo reporteSemanaActualMotivo);

    ReporteSemanaActualMotivo find(Object id);

    List<ReporteSemanaActualMotivo> findAll();

    List<ReporteSemanaActualMotivo> findRange(int[] range);

    int count();

    List<ReporteSemanaActualMotivo> getAllActivo();
    
}
