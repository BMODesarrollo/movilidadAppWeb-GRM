/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccLugar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccLugarFacadeLocal {

    void create(AccLugar accLugar);

    void edit(AccLugar accLugar);

    void remove(AccLugar accLugar);

    AccLugar find(Object id);

    List<AccLugar> findAll();

    List<AccLugar> findRange(int[] range);

    int count();
    
    List<AccLugar> estadoReg();
    
}
