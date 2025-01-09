/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaSector;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaSectorFacadeLocal {

    void create(AccViaSector accViaSector);

    void edit(AccViaSector accViaSector);

    void remove(AccViaSector accViaSector);

    AccViaSector find(Object id);

    List<AccViaSector> findAll();

    List<AccViaSector> findRange(int[] range);

    int count();

    List<AccViaSector> estadoReg();

}
