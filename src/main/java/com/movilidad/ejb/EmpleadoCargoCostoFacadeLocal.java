/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoCargoCosto;
import java.util.List;
import javax.ejb.Local;
import java.util.Date;

/**
 *
 * @author HP
 */
@Local
public interface EmpleadoCargoCostoFacadeLocal {

    void create(EmpleadoCargoCosto empleadoCargoCosto);

    void edit(EmpleadoCargoCosto empleadoCargoCosto);

    void remove(EmpleadoCargoCosto empleadoCargoCosto);

    EmpleadoCargoCosto find(Object id);

    EmpleadoCargoCosto findByFechaYTipoCargo(Date fecha, int idTipoCargo);

    List<EmpleadoCargoCosto> findAll();

    List<EmpleadoCargoCosto> findRange(int[] range);

    int count();

    List<EmpleadoCargoCosto> findForDateAndCargo(Date d, Integer idCargo);

    List<EmpleadoCargoCosto> findMaxDateHasta(Integer idCargo);

    List<EmpleadoCargoCosto> findAllEstadoReg();

    List<EmpleadoCargoCosto> findAllByAreaEstadoReg(Integer idArea);

    List<EmpleadoCargoCosto> findAllRangoFechaEstadoReg(Date ini, Date fin);

}
