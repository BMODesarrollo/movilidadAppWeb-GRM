/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadInfrastrucRutaAfectada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GmoNovedadInfrastrucRutaAfectadaFacadeLocal {

    void create(GmoNovedadInfrastrucRutaAfectada gmoNovedadInfrastrucRutaAfectada);

    void edit(GmoNovedadInfrastrucRutaAfectada gmoNovedadInfrastrucRutaAfectada);

    void remove(GmoNovedadInfrastrucRutaAfectada gmoNovedadInfrastrucRutaAfectada);

    GmoNovedadInfrastrucRutaAfectada find(Object id);

    List<GmoNovedadInfrastrucRutaAfectada> findAll();
    
    List<GmoNovedadInfrastrucRutaAfectada> findAllByIdNovedad(Integer idGmoNovedadInfra);

    List<GmoNovedadInfrastrucRutaAfectada> findRange(int[] range);

    int count();
    
}
