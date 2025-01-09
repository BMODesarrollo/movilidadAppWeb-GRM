/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacionIndividual;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NominaAutorizacionIndividualFacadeLocal {

    void create(NominaAutorizacionIndividual nominaAutorizacionIndividual);

    void edit(NominaAutorizacionIndividual nominaAutorizacionIndividual);

    void remove(NominaAutorizacionIndividual nominaAutorizacionIndividual);

    NominaAutorizacionIndividual find(Object id);

    NominaAutorizacionIndividual findAllByRangoFechasAndUFAndOperador(Date desde, Date hasta, int idGopUnidadFuncional,int idEmpleado);

    List<NominaAutorizacionIndividual> findAll();

    List<NominaAutorizacionIndividual> findAllByRangoFechasAndUF(Date desde, Date hasta, int idGopUnidadFuncional,int idEmpleado);

    List<NominaAutorizacionIndividual> findRange(int[] range);

    int count();

}
