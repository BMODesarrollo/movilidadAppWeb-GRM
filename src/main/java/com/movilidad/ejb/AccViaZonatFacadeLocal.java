/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaZonat;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaZonatFacadeLocal {

    void create(AccViaZonat accViaZonat);

    void edit(AccViaZonat accViaZonat);

    void remove(AccViaZonat accViaZonat);

    AccViaZonat find(Object id);

    List<AccViaZonat> findAll();

    List<AccViaZonat> findRange(int[] range);

    int count();
    
    List<AccViaZonat> estadoReg();
    
}
