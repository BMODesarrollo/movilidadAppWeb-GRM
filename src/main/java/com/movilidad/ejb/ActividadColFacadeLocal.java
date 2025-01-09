/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.ActividadCol;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar Beltrán
 */
@Local
public interface ActividadColFacadeLocal {

    void create(ActividadCol actividad);

    void edit(ActividadCol actividad);

    void remove(ActividadCol actividad);

    ActividadCol find(Object id);

    int count();
    
    List<ActividadCol> findAll();

    List<ActividadCol> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg);
    
    List<ActividadCol> findAllByFechaRange(Date desde, Date hasta);

    List<ActividadCol> findByFecha(int idNovedad);
    
    List<ActividadCol> findByEmpleado(int idEmpleado);
    
    List<ActividadCol> findAllByDateRangeAndArea(Date desde, Date hasta, int idInfraccionesParamArea);
    
    ActividadCol findActivity(ActividadCol actividad);
    
    List<ActividadCol> findActivityEmp(Date fechaIni, Date fechaFin, Integer idEmpleado);
    
    ActividadCol findActivity(Date fechaIni, Date fechaFin, String horaIni , String horaFin, String descripcion);
    
    List<ActividadCol> findAllByGropUnidadFun(int idGopUnidadFuncional);
    
}