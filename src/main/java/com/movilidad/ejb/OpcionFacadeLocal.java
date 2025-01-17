/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Opcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface OpcionFacadeLocal {

    void create(Opcion opcion);

    void edit(Opcion opcion);

    void remove(Opcion opcion);

    Opcion find(Object id);

    List<Opcion> findAll();

    List<Opcion> getAllData();

    List<Opcion> findRange(int[] range);

    int count();

    String findRecurso(Integer i);
}
