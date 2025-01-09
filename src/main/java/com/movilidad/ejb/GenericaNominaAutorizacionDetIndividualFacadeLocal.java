/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacionDetIndividual;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaNominaAutorizacionDetIndividualFacadeLocal {

    void create(GenericaNominaAutorizacionDetIndividual genericaNominaAutorizacionDetIndividual);

    void edit(GenericaNominaAutorizacionDetIndividual genericaNominaAutorizacionDetIndividual);

    void remove(GenericaNominaAutorizacionDetIndividual genericaNominaAutorizacionDetIndividual);

    GenericaNominaAutorizacionDetIndividual find(Object id);

    List<GenericaNominaAutorizacionDetIndividual> findAll();

    List<GenericaNominaAutorizacionDetIndividual> findRange(int[] range);

    int count();
    
    List<GenericaNominaAutorizacionDetIndividual> findByIdNominaAutorizacion(Integer idNominaAutorizacion);
    
    List<GenericaNominaAutorizacionDetIndividual> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError);
    
    Long obtenerCantidadErrores(Integer idNominaAutorizacion);
    
}
