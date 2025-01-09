/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadTipoDetallesInfrastruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GmoNovedadTipoDetallesInfrastrucFacadeLocal {

    void create(GmoNovedadTipoDetallesInfrastruc gmoNovedadTipoDetallesInfrastruc);

    void edit(GmoNovedadTipoDetallesInfrastruc gmoNovedadTipoDetallesInfrastruc);

    void remove(GmoNovedadTipoDetallesInfrastruc gmoNovedadTipoDetallesInfrastruc);

    GmoNovedadTipoDetallesInfrastruc find(Object id);

    List<GmoNovedadTipoDetallesInfrastruc> findAll();

    List<GmoNovedadTipoDetallesInfrastruc> findRange(int[] range);

    int count();
    
    GmoNovedadTipoDetallesInfrastruc findByNombre(String nombre, Integer idRegistro);

    List<GmoNovedadTipoDetallesInfrastruc> findAllByEstadoReg();
    
}
