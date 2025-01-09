/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondHumana;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondHumanaFacadeLocal {

    void create(AccCondHumana accCondHumana);

    void edit(AccCondHumana accCondHumana);

    void remove(AccCondHumana accCondHumana);

    AccCondHumana find(Object id);

    List<AccCondHumana> findAll();

    List<AccCondHumana> findRange(int[] range);

    int count();
    
    List<AccCondHumana> estadoReg();
    
}
