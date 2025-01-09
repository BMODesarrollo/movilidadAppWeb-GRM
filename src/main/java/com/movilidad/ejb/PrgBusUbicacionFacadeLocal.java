/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgBusUbicacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PrgBusUbicacionFacadeLocal {

    void create(PrgBusUbicacion prgBusUbicacion);

    void edit(PrgBusUbicacion prgBusUbicacion);

    void remove(PrgBusUbicacion prgBusUbicacion);

    PrgBusUbicacion find(Object id);

    List<PrgBusUbicacion> findAll();

    List<PrgBusUbicacion> findRange(int[] range);

    int count();
    
}
