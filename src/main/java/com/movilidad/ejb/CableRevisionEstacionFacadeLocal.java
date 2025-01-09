/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionEstacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableRevisionEstacionFacadeLocal {

    void create(CableRevisionEstacion cableRevisionEstacion);

    void edit(CableRevisionEstacion cableRevisionEstacion);

    void remove(CableRevisionEstacion cableRevisionEstacion);

    CableRevisionEstacion find(Object id);

    CableRevisionEstacion findByRevisionActividadAndEquipo(Integer idRegistro, Integer idRevisionActividad, Integer idRevisionEquipo, Integer idCableEstacion);

    List<CableRevisionEstacion> findAll();

    List<CableRevisionEstacion> findAllByEstadoReg();

    List<CableRevisionEstacion> findByEstacion(Integer idEstacion);
    
    List<CableRevisionEstacion> findRange(int[] range);

    int count();

}
