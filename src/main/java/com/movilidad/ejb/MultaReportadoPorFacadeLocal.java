/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaReportadoPor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface MultaReportadoPorFacadeLocal {

    void create(MultaReportadoPor multaReportadoPor);

    void edit(MultaReportadoPor multaReportadoPor);

    void remove(MultaReportadoPor multaReportadoPor);

    MultaReportadoPor find(Object id);

    List<MultaReportadoPor> findAll();

    List<MultaReportadoPor> findRange(int[] range);

    int count();
    
    List<MultaReportadoPor> listarMRP(String nombres);
   
    public List <MultaReportadoPor> findallEst ();
    
}
