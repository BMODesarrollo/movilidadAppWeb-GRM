/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadClasificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface NovedadClasificacionFacadeLocal {

    void create(NovedadClasificacion novedadClasificacion);

    void edit(NovedadClasificacion novedadClasificacion);

    void remove(NovedadClasificacion novedadClasificacion);

    NovedadClasificacion find(Object id);

    List<NovedadClasificacion> findAll();

    List<NovedadClasificacion> findRange(int[] range);

    int count();

    List<NovedadClasificacion> findAllEstadoReg();
}
