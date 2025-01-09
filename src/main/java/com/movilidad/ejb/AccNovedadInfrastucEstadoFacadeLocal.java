/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfrastucEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadInfrastucEstadoFacadeLocal {

    void create(AccNovedadInfrastucEstado accNovedadInfrastucEstado);

    void edit(AccNovedadInfrastucEstado accNovedadInfrastucEstado);

    void remove(AccNovedadInfrastucEstado accNovedadInfrastucEstado);

    AccNovedadInfrastucEstado find(Object id);
    
    AccNovedadInfrastucEstado findByNombre(String nombre, Integer idRegistro);

    List<AccNovedadInfrastucEstado> findAll();
    
    List<AccNovedadInfrastucEstado> findAllByEstadoReg();

    List<AccNovedadInfrastucEstado> findRange(int[] range);

    int count();
    
}
