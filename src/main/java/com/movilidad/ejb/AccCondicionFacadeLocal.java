/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondicion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondicionFacadeLocal {

    void create(AccCondicion accCondicion);

    void edit(AccCondicion accCondicion);

    void remove(AccCondicion accCondicion);

    AccCondicion find(Object id);

    List<AccCondicion> findAll();

    List<AccCondicion> findRange(int[] range);

    int count();
    
    List<AccCondicion> estadoReg();
    
}
