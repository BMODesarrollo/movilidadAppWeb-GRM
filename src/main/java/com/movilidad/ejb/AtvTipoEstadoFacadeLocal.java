/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvTipoEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvTipoEstadoFacadeLocal {

    void create(AtvTipoEstado atvTipoEstado);

    void edit(AtvTipoEstado atvTipoEstado);

    void remove(AtvTipoEstado atvTipoEstado);

    AtvTipoEstado find(Object id);

    List<AtvTipoEstado> findAll();

    List<AtvTipoEstado> findRange(int[] range);

    int count();
    
    List<AtvTipoEstado> findByEstadoReg();
    
}
