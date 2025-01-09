/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadInfraestrucFacadeLocal {

    void create(AccNovedadInfraestruc accNovedadInfraestruc);

    void edit(AccNovedadInfraestruc accNovedadInfraestruc);

    void remove(AccNovedadInfraestruc accNovedadInfraestruc);

    AccNovedadInfraestruc find(Object id);

    List<AccNovedadInfraestruc> findAll();
    
    List<AccNovedadInfraestruc> findAllByEstadoReg();

    List<AccNovedadInfraestruc> findRange(int[] range);

    int count();
    
}
