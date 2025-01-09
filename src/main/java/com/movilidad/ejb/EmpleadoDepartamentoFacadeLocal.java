/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoDepartamento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoDepartamentoFacadeLocal {

    void create(EmpleadoDepartamento empleadoDepartamento);

    void edit(EmpleadoDepartamento empleadoDepartamento);

    void remove(EmpleadoDepartamento empleadoDepartamento);

    EmpleadoDepartamento find(Object id);

    List<EmpleadoDepartamento> findAll();

    List<EmpleadoDepartamento> findRange(int[] range);

    int count();
    
}
