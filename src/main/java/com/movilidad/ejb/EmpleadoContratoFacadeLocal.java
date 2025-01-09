/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoContrato;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoContratoFacadeLocal {

    void create(EmpleadoContrato empleadoContrato);

    void edit(EmpleadoContrato empleadoContrato);

    void remove(EmpleadoContrato empleadoContrato);

    EmpleadoContrato find(Object id);

    List<EmpleadoContrato> findAll();

    List<EmpleadoContrato> findRange(int[] range);

    int count();
    
}
