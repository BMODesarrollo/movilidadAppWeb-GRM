/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConfigFreeway;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ConfigFreewayFacadeLocal {

    void create(ConfigFreeway configFreeway);

    void edit(ConfigFreeway configFreeway);

    void remove(ConfigFreeway configFreeway);

    ConfigFreeway find(Object id);

    List<ConfigFreeway> findAll();

    List<ConfigFreeway> findRange(int[] range);

    int count();

    List<ConfigFreeway> findAllByEstadoReg();

    ConfigFreeway findByCodSolucion(String codSolucion, Integer idConfigFreeway);

    ConfigFreeway findByIdGopUnidadFuncional(Integer idGopUnidadFuncional, Integer idConfigFreeway);

}
