/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoEmpleador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface EmpleadoEmpleadorFacadeLocal {

    void create(EmpleadoEmpleador empleadoEmpleador);

    void edit(EmpleadoEmpleador empleadoEmpleador);

    void remove(EmpleadoEmpleador empleadoEmpleador);

    EmpleadoEmpleador find(Object id);

    List<EmpleadoEmpleador> findAll();

    List<EmpleadoEmpleador> findRange(int[] range);

    int count();
    
    List<EmpleadoEmpleador> findAllEstadoReg();
    
}
