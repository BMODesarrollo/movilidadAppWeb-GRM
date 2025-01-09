/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadInfrastrucSeguimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GmoNovedadInfrastrucSeguimientoFacadeLocal {

    void create(GmoNovedadInfrastrucSeguimiento gmoNovedadInfrastrucSeguimiento);

    void edit(GmoNovedadInfrastrucSeguimiento gmoNovedadInfrastrucSeguimiento);

    void remove(GmoNovedadInfrastrucSeguimiento gmoNovedadInfrastrucSeguimiento);

    GmoNovedadInfrastrucSeguimiento find(Object id);

    List<GmoNovedadInfrastrucSeguimiento> findAll();

    List<GmoNovedadInfrastrucSeguimiento> findRange(int[] range);
    
    List<GmoNovedadInfrastrucSeguimiento> findAllByIdNovedad(Integer idGmoNovedadInfra);

    int count();
    
}
