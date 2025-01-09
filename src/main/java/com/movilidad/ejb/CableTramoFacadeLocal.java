/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableTramo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableTramoFacadeLocal {

    void create(CableTramo cableTramo);

    void edit(CableTramo cableTramo);

    void remove(CableTramo cableTramo);

    CableTramo find(Object id);

    CableTramo findByCodigo(String codigo, Integer idRegistro);

    CableTramo findByNombre(String nombre, Integer idRegistro);

    List<CableTramo> findAll();

    List<CableTramo> findAllByEstadoReg();

    List<CableTramo> findRange(int[] range);

    int count();

}
