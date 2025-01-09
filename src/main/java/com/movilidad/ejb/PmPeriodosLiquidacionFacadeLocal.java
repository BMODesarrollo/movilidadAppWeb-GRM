/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmNovedadIncluir;
import com.movilidad.model.PmPeriodosLiquidacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface PmPeriodosLiquidacionFacadeLocal {

    void create(PmPeriodosLiquidacion PmPeriodosLiquidacion);

    void edit(PmPeriodosLiquidacion PmPeriodosLiquidacion);

    void remove(PmPeriodosLiquidacion PmPeriodosLiquidacion);

    PmPeriodosLiquidacion find(Object id);

    List<PmPeriodosLiquidacion> findAll();

    List<PmPeriodosLiquidacion> findRange(int[] range);

    int count();

    List<PmPeriodosLiquidacion> getAllActivo();
    
    PmPeriodosLiquidacion findProximoPeriodo(int id);
//
//    PmNovedadIncluir getByIdNovedadTipoDet(int idDet, int idPmNovedadIncluir);

}
