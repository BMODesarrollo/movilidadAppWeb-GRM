/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgVehiculoInactivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgVehiculoInactivoFacadeLocal {

    void create(PrgVehiculoInactivo prgVehiculoInactivo);

    void edit(PrgVehiculoInactivo prgVehiculoInactivo);

    void remove(PrgVehiculoInactivo prgVehiculoInactivo);

    PrgVehiculoInactivo find(Object id);

    List<PrgVehiculoInactivo> findAll();

    List<PrgVehiculoInactivo> findRange(int[] range);

    int count();
    
}
