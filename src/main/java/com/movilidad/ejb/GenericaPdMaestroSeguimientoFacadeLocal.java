/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPdMaestroSeguimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPdMaestroSeguimientoFacadeLocal {

    void create(GenericaPdMaestroSeguimiento genericaPdMaestroSeguimiento);

    void edit(GenericaPdMaestroSeguimiento genericaPdMaestroSeguimiento);

    void remove(GenericaPdMaestroSeguimiento genericaPdMaestroSeguimiento);

    GenericaPdMaestroSeguimiento find(Object id);

    List<GenericaPdMaestroSeguimiento> findAll();
    
    List<GenericaPdMaestroSeguimiento> findByIdProceso(Integer idProceso);

    List<GenericaPdMaestroSeguimiento> findRange(int[] range);

    int count();
    
}
