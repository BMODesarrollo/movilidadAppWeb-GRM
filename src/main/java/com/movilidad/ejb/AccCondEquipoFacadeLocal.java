/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondEquipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondEquipoFacadeLocal {

    void create(AccCondEquipo accCondEquipo);

    void edit(AccCondEquipo accCondEquipo);

    void remove(AccCondEquipo accCondEquipo);

    AccCondEquipo find(Object id);

    List<AccCondEquipo> findAll();

    List<AccCondEquipo> findRange(int[] range);

    int count();
    
    List<AccCondEquipo> estadoReg();
    
}
