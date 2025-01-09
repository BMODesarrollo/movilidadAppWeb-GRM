/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoNO;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoNOFacadeLocal {

    void create(LavadoNO lavado);

    void edit(LavadoNO lavado);

    void remove(LavadoNO lavado);

    LavadoNO find(Object id);

    List<LavadoNO> findAll();

    List<LavadoNO> findRange(int[] range);
    
    List<LavadoNO> findByDateRange(Date desde, Date hasta);

    int count();
    
}
