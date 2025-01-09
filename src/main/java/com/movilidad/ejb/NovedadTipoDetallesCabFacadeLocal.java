/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoDetallesCab;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadTipoDetallesCabFacadeLocal {

    void create(NovedadTipoDetallesCab novedadTipoDetallesCab);

    void edit(NovedadTipoDetallesCab novedadTipoDetallesCab);

    void remove(NovedadTipoDetallesCab novedadTipoDetallesCab);

    NovedadTipoDetallesCab find(Object id);
    
    NovedadTipoDetallesCab findByNombre(String nombre, Integer idRegistro);

    List<NovedadTipoDetallesCab> findAll();
    
    List<NovedadTipoDetallesCab> findAllByEstadoReg();

    List<NovedadTipoDetallesCab> findRange(int[] range);

    int count();
    
}
