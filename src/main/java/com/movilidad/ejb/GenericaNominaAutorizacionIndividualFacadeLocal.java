/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacionIndividual;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaNominaAutorizacionIndividualFacadeLocal {

    void create(GenericaNominaAutorizacionIndividual genericaNominaAutorizacionIndividual);

    void edit(GenericaNominaAutorizacionIndividual genericaNominaAutorizacionIndividual);

    void remove(GenericaNominaAutorizacionIndividual genericaNominaAutorizacionIndividual);

    GenericaNominaAutorizacionIndividual find(Object id);
    
    GenericaNominaAutorizacionIndividual verificarRegistro(Date desde, Date hasta, int idParamArea,int idEmpleado);

    List<GenericaNominaAutorizacionIndividual> findAll();

    List<GenericaNominaAutorizacionIndividual> findAllByRangoFechasAndAreaAndEmpleado(Date desde, Date hasta, int idParamArea, int idEmpleado);

    List<GenericaNominaAutorizacionIndividual> findRange(int[] range);

    int count();

}
