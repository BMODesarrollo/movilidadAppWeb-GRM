/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaHistorial;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface GenericaJornadaHistorialFacadeLocal {

    void create(GenericaJornadaHistorial genericaJornadaHistorial);

    void edit(GenericaJornadaHistorial genericaJornadaHistorial);

    void remove(GenericaJornadaHistorial genericaJornadaHistorial);

    GenericaJornadaHistorial find(Object id);

    List<GenericaJornadaHistorial> findAll();

    List<GenericaJornadaHistorial> findRange(int[] range);

    int count();
    
}
