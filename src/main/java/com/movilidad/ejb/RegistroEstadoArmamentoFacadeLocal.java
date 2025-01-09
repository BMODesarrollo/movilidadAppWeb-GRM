/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.RegistroEstadoArmamento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface RegistroEstadoArmamentoFacadeLocal {

    void create(RegistroEstadoArmamento registroEstadoArmamento);

    void edit(RegistroEstadoArmamento registroEstadoArmamento);

    void remove(RegistroEstadoArmamento registroEstadoArmamento);

    RegistroEstadoArmamento find(Object id);

    List<RegistroEstadoArmamento> findAll();
    
    List<RegistroEstadoArmamento> findAllByEstadoReg();

    List<RegistroEstadoArmamento> findRange(int[] range);

    int count();
    
}
