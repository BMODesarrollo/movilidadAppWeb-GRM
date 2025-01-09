/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SncCorrecion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface SncCorrecionFacadeLocal {

    void create(SncCorrecion sncCorrecion);

    void edit(SncCorrecion sncCorrecion);

    void remove(SncCorrecion sncCorrecion);

    SncCorrecion find(Object id);

    List<SncCorrecion> findAll();

    List<SncCorrecion> findRange(int[] range);

    int count();

    public List<SncCorrecion> findallEst();
    
}
