/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmLiquidacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmLiquidacionFacadeLocal {

    void create(GenericaPmLiquidacion genericaPmLiquidacion);

    void edit(GenericaPmLiquidacion genericaPmLiquidacion);

    void remove(GenericaPmLiquidacion genericaPmLiquidacion);

    GenericaPmLiquidacion find(Object id);

    List<GenericaPmLiquidacion> findAll();

    List<GenericaPmLiquidacion> findRange(int[] range);

    int count();

    void generarReporte(Date desde, Date hasta, int idArea, String userGenera);

    int eliminarDatos(Date desde, Date hasta, String userBorra, int idArea);

    List<GenericaPmLiquidacion> findAllByFechaMes(Date fecha, int idARea);

}
