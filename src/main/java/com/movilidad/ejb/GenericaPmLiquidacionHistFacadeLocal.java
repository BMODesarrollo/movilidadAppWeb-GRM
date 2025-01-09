/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmLiquidacionHist;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmLiquidacionHistFacadeLocal {

    void create(GenericaPmLiquidacionHist genericaPmLiquidacionHist);

    void edit(GenericaPmLiquidacionHist genericaPmLiquidacionHist);

    void remove(GenericaPmLiquidacionHist genericaPmLiquidacionHist);

    GenericaPmLiquidacionHist find(Object id);

    List<GenericaPmLiquidacionHist> findAll();

    List<GenericaPmLiquidacionHist> findRange(int[] range);

    int count();

    List<GenericaPmLiquidacionHist> findAllByFechaMes(Date desde, Date hasta, int idArea);

    int eliminarDatos(Date desde, Date hasta, String userBorra, int idArea);

}
