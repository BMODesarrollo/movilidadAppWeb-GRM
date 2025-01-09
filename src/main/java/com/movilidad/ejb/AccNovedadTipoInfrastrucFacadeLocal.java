/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadTipoInfrastruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadTipoInfrastrucFacadeLocal {

    void create(AccNovedadTipoInfrastruc accNovedadTipoInfrastruc);

    void edit(AccNovedadTipoInfrastruc accNovedadTipoInfrastruc);

    void remove(AccNovedadTipoInfrastruc accNovedadTipoInfrastruc);

    AccNovedadTipoInfrastruc find(Object id);
    
    AccNovedadTipoInfrastruc findByNombre(String nombre, Integer idRegistro);

    List<AccNovedadTipoInfrastruc> findAll();
    
    List<AccNovedadTipoInfrastruc> findAllByEstadoReg();

    List<AccNovedadTipoInfrastruc> findRange(int[] range);

    int count();
    
}
