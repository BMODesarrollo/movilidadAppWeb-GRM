/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoParamArea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AseoParamAreaFacadeLocal {

    void create(AseoParamArea aseoParamArea);

    void edit(AseoParamArea aseoParamArea);

    void remove(AseoParamArea aseoParamArea);

    AseoParamArea find(Object id);

    AseoParamArea findByCodigo(String codigo);
    
    AseoParamArea findByCodigo(String codigo, Integer idRegistro);

    AseoParamArea findByNombre(String nombre, Integer idRegistro);

    List<AseoParamArea> findAllByEstadoReg();

    List<AseoParamArea> findAll();

    List<AseoParamArea> findRange(int[] range);

    int count();

    AseoParamArea findByHashString(String hashString);
}