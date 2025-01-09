/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegRegistroArmamento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SegRegistroArmamentoFacadeLocal {

    void create(SegRegistroArmamento segRegistroArmamento);

    void edit(SegRegistroArmamento segRegistroArmamento);

    void remove(SegRegistroArmamento segRegistroArmamento);

    SegRegistroArmamento find(Object id);
    
    SegRegistroArmamento findBySerial(String serial);

    List<SegRegistroArmamento> findAll();

    List<SegRegistroArmamento> findByEstadoReg();

    List<SegRegistroArmamento> findRange(int[] range);

    int count();
    
}
