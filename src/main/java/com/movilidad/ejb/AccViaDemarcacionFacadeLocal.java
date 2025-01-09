/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaDemarcacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaDemarcacionFacadeLocal {

    void create(AccViaDemarcacion accViaDemarcacion);

    void edit(AccViaDemarcacion accViaDemarcacion);

    void remove(AccViaDemarcacion accViaDemarcacion);

    AccViaDemarcacion find(Object id);

    List<AccViaDemarcacion> findAll();

    List<AccViaDemarcacion> findRange(int[] range);

    int count();

    List<AccViaDemarcacion> estadoReg();

}
