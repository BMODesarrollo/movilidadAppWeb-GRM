/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegPilona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SegPilonaFacadeLocal {

    void create(SegPilona segPilona);

    void edit(SegPilona segPilona);

    void remove(SegPilona segPilona);

    SegPilona find(Object id);

    SegPilona findByCodigo(String codigo);

    SegPilona findByNombre(String nombre);

    List<SegPilona> findAll();

    List<SegPilona> findByEstadoReg();

    List<SegPilona> findRange(int[] range);

    int count();

}
