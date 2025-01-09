/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ReporteSemanaMotivoPrg;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface ReporteSemanaMotivoPrgFacadeLocal {

    void create(ReporteSemanaMotivoPrg r);

    void edit(ReporteSemanaMotivoPrg r);

    void remove(ReporteSemanaMotivoPrg r);

    ReporteSemanaMotivoPrg find(Object id);

    List<ReporteSemanaMotivoPrg> findAll();

    List<ReporteSemanaMotivoPrg> findRange(int[] range);

    int count();

    List<ReporteSemanaMotivoPrg> getActivo();
    
    ReporteSemanaMotivoPrg findByIdPrgTc(Integer idPrgTc);
    
}
