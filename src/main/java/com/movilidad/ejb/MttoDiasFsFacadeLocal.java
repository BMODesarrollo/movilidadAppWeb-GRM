/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoDiasFs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface MttoDiasFsFacadeLocal {

    void create(MttoDiasFs mttoDiasFs);

    void edit(MttoDiasFs mttoDiasFs);

    void remove(MttoDiasFs mttoDiasFs);

    MttoDiasFs find(Object id);

    List<MttoDiasFs> findAll();

    List<MttoDiasFs> findRange(int[] range);

    int count();
    
    List<MttoDiasFs> findAllEstadoReg();
}
