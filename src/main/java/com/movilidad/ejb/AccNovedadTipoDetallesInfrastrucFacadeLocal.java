/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadTipoDetallesInfrastruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadTipoDetallesInfrastrucFacadeLocal {

    void create(AccNovedadTipoDetallesInfrastruc accNovedadTipoDetallesInfrastruc);

    void edit(AccNovedadTipoDetallesInfrastruc accNovedadTipoDetallesInfrastruc);

    void remove(AccNovedadTipoDetallesInfrastruc accNovedadTipoDetallesInfrastruc);

    AccNovedadTipoDetallesInfrastruc find(Object id);
    
    AccNovedadTipoDetallesInfrastruc findByNombre(String nombre, Integer idRegistro, Integer idTipoNovedad);

    List<AccNovedadTipoDetallesInfrastruc> findAll();
    
    List<AccNovedadTipoDetallesInfrastruc> findAllByEstadoReg();

    List<AccNovedadTipoDetallesInfrastruc> findRange(int[] range);

    int count();
    
}
