/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface LavadoMotivoFacadeLocal {

    void create(LavadoMotivo lavadoMotivo);

    void edit(LavadoMotivo lavadoMotivo);

    void remove(LavadoMotivo lavadoMotivo);

    LavadoMotivo find(Object id);

    List<LavadoMotivo> findAll();

    List<LavadoMotivo> findRange(int[] range);

    int count();
    
    List<LavadoMotivo> findAllEstadoReg();
    
}
