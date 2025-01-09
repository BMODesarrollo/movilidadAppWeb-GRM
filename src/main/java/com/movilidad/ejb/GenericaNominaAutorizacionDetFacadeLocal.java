/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacionDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaNominaAutorizacionDetFacadeLocal {

    void create(GenericaNominaAutorizacionDet genericaNominaAutorizacionDet);

    void edit(GenericaNominaAutorizacionDet genericaNominaAutorizacionDet);

    void remove(GenericaNominaAutorizacionDet genericaNominaAutorizacionDet);

    GenericaNominaAutorizacionDet find(Object id);

    List<GenericaNominaAutorizacionDet> findAll();
    
    List<GenericaNominaAutorizacionDet> findByIdNominaAutorizacion(Integer idNominaAutorizacion);

    List<GenericaNominaAutorizacionDet> findRange(int[] range);
    
    List<GenericaNominaAutorizacionDet> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError);

    int count();
    
    Long obtenerCantidadErrores(Integer idNominaAutorizacion);
    
}
