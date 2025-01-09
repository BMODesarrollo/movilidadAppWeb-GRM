/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadSeguimientoDocs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Alberto
 */
@Local
public interface NovedadSeguimientoDocsFacadeLocal {

    void create(NovedadSeguimientoDocs novedadSeguimientoDocs);

    void edit(NovedadSeguimientoDocs novedadSeguimientoDocs);

    void remove(NovedadSeguimientoDocs novedadSeguimientoDocs);

    NovedadSeguimientoDocs find(Object id);

    List<NovedadSeguimientoDocs> findByIdSeguimiento(Integer idSeguimiento);

    List<NovedadSeguimientoDocs> findRange(int[] range);

    int count();
    
}
