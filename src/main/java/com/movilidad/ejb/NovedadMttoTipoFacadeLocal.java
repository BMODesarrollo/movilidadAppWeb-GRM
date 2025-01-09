/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadMttoTipoFacadeLocal {

    void create(NovedadMttoTipo novedadMttoTipo);

    void edit(NovedadMttoTipo novedadMttoTipo);

    void remove(NovedadMttoTipo novedadMttoTipo);

    NovedadMttoTipo find(Object id);
    
    NovedadMttoTipo findByNombre(Integer idRegistro,String nombre);

    List<NovedadMttoTipo> findAll();

    List<NovedadMttoTipo> findAllByEstadoReg();

    List<NovedadMttoTipo> findRange(int[] range);

    int count();

}
