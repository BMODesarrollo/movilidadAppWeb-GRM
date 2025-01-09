/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacion;
import com.movilidad.model.NominaAutorizacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaNominaAutorizacionFacadeLocal {

    void create(GenericaNominaAutorizacion genericaNominaAutorizacion);

    void edit(GenericaNominaAutorizacion genericaNominaAutorizacion);

    void remove(GenericaNominaAutorizacion genericaNominaAutorizacion);

    GenericaNominaAutorizacion find(Object id);

    List<GenericaNominaAutorizacion> findAll();
    
    List<GenericaNominaAutorizacion> findAllByRangoFechasAndArea(Date desde, Date hasta, int idParamArea);

    List<GenericaNominaAutorizacion> findRange(int[] range);

    int count();
    
}
