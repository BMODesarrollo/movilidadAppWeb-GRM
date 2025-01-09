/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.Generica;
import com.movilidad.model.Novedad;
import com.movilidad.model.ParamAreaUsr;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaFacadeLocal {

    void create(Generica generica);

    void edit(Generica generica);

    void remove(Generica generica);

    Generica find(Object id);

    ParamAreaUsr findByUsername(String username);

    List<Integer> obtenerCargos(int area);

    List<Generica> findAll(Date fecha);

    List<Generica> findAllByArea(Date fecha, int area, int idGopUnidadFuncional);

    List<Generica> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<Generica> findByDateRangeAndArea(Date fechaIni, Date fechaFin, int area, int idGopUnidadFuncional);

    List<Generica> findRange(int[] range);

    List<Empleado> obtenerEmpleadosByCargo(List<Integer> cargos);

    Generica validarNovedadConFechas(int empleado, Date desde, Date hasta);

    Generica verificarNovedadPMSinFechas(Date fecha, int idEmpleado, int idNovedadTipoDetalle);

    List<Generica> findByDateRangeAndIdEmpleado(Date fechaIni, Date fechaFin, int idEmpleado);

    List<Integer> findByDateRangeAndIdEmpleadoSeguim(Date fechaIni, Date fechaFin, int idEmpleado);

    List<Integer> findByDateRangeAndIdEmpleadoDocu(Date fechaIni, Date fechaFin, int idEmpleado);

    int count();

}
