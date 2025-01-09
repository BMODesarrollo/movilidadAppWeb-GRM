/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacionDetIndividual;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NominaAutorizacionDetIndividualFacadeLocal {

    void create(NominaAutorizacionDetIndividual nominaAutorizacionDetIndividual);

    void edit(NominaAutorizacionDetIndividual nominaAutorizacionDetIndividual);

    void remove(NominaAutorizacionDetIndividual nominaAutorizacionDetIndividual);

    NominaAutorizacionDetIndividual find(Object id);

    List<NominaAutorizacionDetIndividual> findAll();

    List<NominaAutorizacionDetIndividual> findRange(int[] range);

    int count();

    List<NominaAutorizacionDetIndividual> findByIdNominaAutorizacion(Integer idNominaAutorizacion);
    
    List<NominaAutorizacionDetIndividual> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError);
    
    Long obtenerCantidadErrores(Integer idNominaAutorizacion);
}
