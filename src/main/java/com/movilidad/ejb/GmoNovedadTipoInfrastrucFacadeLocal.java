/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadTipoInfrastruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GmoNovedadTipoInfrastrucFacadeLocal {

    void create(GmoNovedadTipoInfrastruc gmoNovedadTipoInfrastruc);

    void edit(GmoNovedadTipoInfrastruc gmoNovedadTipoInfrastruc);

    void remove(GmoNovedadTipoInfrastruc gmoNovedadTipoInfrastruc);

    GmoNovedadTipoInfrastruc find(Object id);

    List<GmoNovedadTipoInfrastruc> findAll();

    List<GmoNovedadTipoInfrastruc> findRange(int[] range);

    int count();
    
    GmoNovedadTipoInfrastruc findByNombre(String nombre, Integer idRegistro);

    List<GmoNovedadTipoInfrastruc> findAllByEstadoReg();
    
    
}
