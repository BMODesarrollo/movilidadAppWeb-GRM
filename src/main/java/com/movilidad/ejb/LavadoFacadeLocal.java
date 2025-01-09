/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.Lavado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoFacadeLocal {

    void create(Lavado lavado);

    void edit(Lavado lavado);

    void remove(Lavado lavado);

    Lavado find(Object id);

    List<Lavado> findAll();

    List<Lavado> findRange(int[] range);

    List<Empleado> findEmployee();
    
    List<String> getNameListEmployee ();
    
    int count();
    
}
