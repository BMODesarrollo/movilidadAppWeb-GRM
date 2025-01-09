/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AseoTipoFacadeLocal {

    void create(AseoTipo aseoTipo);

    void edit(AseoTipo aseoTipo);

    void remove(AseoTipo aseoTipo);

    AseoTipo find(Object id);

    AseoTipo findByNombre(String nombre, Integer idRegistro);

    List<AseoTipo> findAll();

    List<AseoTipo> findAllByEstadoReg();

    List<AseoTipo> findRange(int[] range);

    int count();
}
