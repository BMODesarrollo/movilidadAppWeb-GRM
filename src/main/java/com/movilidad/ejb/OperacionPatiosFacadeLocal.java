/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionPatios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface OperacionPatiosFacadeLocal {

    void create(OperacionPatios operacionPatios);

    void edit(OperacionPatios operacionPatios);

    void remove(OperacionPatios operacionPatios);

    OperacionPatios find(Object id);

    List<OperacionPatios> findAll();

    List<OperacionPatios> findAllActivos();

    List<OperacionPatios> findAllActivosLatLong();

    List<OperacionPatios> findRange(int[] range);

    int count();

    OperacionPatios findByCodigo(String value, int id);

}
