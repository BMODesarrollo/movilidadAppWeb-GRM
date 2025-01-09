/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaIluminacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaIluminacionFacadeLocal {

    void create(AccViaIluminacion accViaIluminacion);

    void edit(AccViaIluminacion accViaIluminacion);

    void remove(AccViaIluminacion accViaIluminacion);

    AccViaIluminacion find(Object id);

    List<AccViaIluminacion> findAll();

    List<AccViaIluminacion> findRange(int[] range);

    int count();

    List<AccViaIluminacion> estadoReg();

}
