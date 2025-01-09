/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadIncapacidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadIncapacidadFacadeLocal {

    void create(NovedadIncapacidad novedadIncapacidad);

    void edit(NovedadIncapacidad novedadIncapacidad);

    void remove(NovedadIncapacidad novedadIncapacidad);

    NovedadIncapacidad find(Object id);

    List<NovedadIncapacidad> findAll();

    List<NovedadIncapacidad> findByNovedad(int idNovedad);

    List<NovedadIncapacidad> findRange(int[] range);

    int count();

}
