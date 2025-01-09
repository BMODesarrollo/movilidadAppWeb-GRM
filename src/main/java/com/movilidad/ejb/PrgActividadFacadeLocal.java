/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgActividad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgActividadFacadeLocal {

    void create(PrgActividad prgActividad);

    void edit(PrgActividad prgActividad);

    void remove(PrgActividad prgActividad);

    PrgActividad find(Object id);

    List<PrgActividad> findAll();

    List<PrgActividad> findRange(int[] range);

    int count();
    
}
