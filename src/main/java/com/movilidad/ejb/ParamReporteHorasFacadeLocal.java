/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamReporteHoras;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.util.beans.ReporteHorasKactus;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ParamReporteHorasFacadeLocal {

    void create(ParamReporteHoras paramReporteHoras);

    void edit(ParamReporteHoras paramReporteHoras);

    void remove(ParamReporteHoras paramReporteHoras);

    ParamReporteHoras find(Object id);

    List<ParamReporteHoras> findAllActivos(int idGopUnidadFuncional);

    List<ParamReporteHoras> findAll();

    List<ReporteHoras> obtenerDatosReporte(Date fechaInicio, Date fechaFin, int idGopUnidadFuncional);

    List<ReporteHoras> obtenerDatosReporteGenericas(Date fechaInicio, Date fechaFin, Integer idArea, int idGopUnidadFuncional);

    List<ReporteHorasKactus> obtenerDatosReporteKactusGenericas(Date fechaInicio, Date fechaFin, Integer idArea);

    List<ReporteHorasKactus> obtenerDatosReporteKactusGenericasIndividual(Date fechaInicio, Date fechaFin, Integer idArea, int idEmpleado);

    List<ReporteHorasKactus> obtenerDatosReporteKactus(Date fechaInicio, Date fechaFin, int idGopUnidadFuncional);

    List<ReporteHorasKactus> obtenerDatosReporteKactusIndividual(Date fechaInicio, Date fechaFin, int idGopUnidadFuncional, int idEmpleado);

    List<ParamReporteHoras> findRange(int[] range);

    int count();

    List<ReporteHoras> obtenerDatosReporteByFechasAndUf(Date desde, Date hasta, int idGopUnidadFuncional);
    
    List<ReporteHoras> obtenerDatosReporteByFechasAndUfAndIdEmpleado(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado);
    
    List<ReporteHoras> obtenerDatosReporteGenericaByAreaAndEmpleado(Date fechaInicio, Date fechaFin, Integer idArea, int idEmpleado);

}
