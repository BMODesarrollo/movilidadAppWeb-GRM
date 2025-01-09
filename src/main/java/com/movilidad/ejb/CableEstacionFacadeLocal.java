/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEstacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableEstacionFacadeLocal {

    void create(CableEstacion cableEstacion);

    void edit(CableEstacion cableEstacion);

    void remove(CableEstacion cableEstacion);

    CableEstacion find(Object id);

    CableEstacion findByCodigo(Integer idRegistro, String codigo);

    CableEstacion findByNombre(Integer idRegistro, String nombre);

    List<CableEstacion> findAll();

    List<CableEstacion> findByEstadoReg();

    List<CableEstacion> findRange(int[] range);

    int count();
}
