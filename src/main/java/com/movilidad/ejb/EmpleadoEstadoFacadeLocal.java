/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoEstadoFacadeLocal {

    void create(EmpleadoEstado empleadoEstado);

    void edit(EmpleadoEstado empleadoEstado);

    void remove(EmpleadoEstado empleadoEstado);

    EmpleadoEstado find(Object id);

    List<EmpleadoEstado> findAll();

    List<EmpleadoEstado> findAllActivos();

    List<EmpleadoEstado> findRange(int[] range);

    int count();

}
