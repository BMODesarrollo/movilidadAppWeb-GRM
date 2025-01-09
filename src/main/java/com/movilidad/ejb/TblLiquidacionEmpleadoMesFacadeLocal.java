/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblLiquidacionEmpleadoMes;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface TblLiquidacionEmpleadoMesFacadeLocal {

    void create(TblLiquidacionEmpleadoMes tblLiquidacionEmpleadoMes);

    void edit(TblLiquidacionEmpleadoMes tblLiquidacionEmpleadoMes);

    void remove(TblLiquidacionEmpleadoMes tblLiquidacionEmpleadoMes);

    void generarReporte(Date desde, Date hasta, String userGenera);

    TblLiquidacionEmpleadoMes find(Object id);

    List<TblLiquidacionEmpleadoMes> findAll();

    List<TblLiquidacionEmpleadoMes> findAllByFechaMes(Date fecha);

    List<TblLiquidacionEmpleadoMes> findRange(int[] range);

    int count();

    int eliminarDatos(Date desde, Date hasta, String userBorra);

}
