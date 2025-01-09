/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaJornadaMotivoFacadeLocal {

    void create(GenericaJornadaMotivo genericaJornadaMotivo);

    void edit(GenericaJornadaMotivo genericaJornadaMotivo);

    void remove(GenericaJornadaMotivo genericaJornadaMotivo);

    GenericaJornadaMotivo find(Object id);

    List<GenericaJornadaMotivo> findAll();

    List<GenericaJornadaMotivo> findByArea(int idArea);

    List<GenericaJornadaMotivo> findRange(int[] range);

    int count();
    
    GenericaJornadaMotivo findByName(String name, int idArea);

}
