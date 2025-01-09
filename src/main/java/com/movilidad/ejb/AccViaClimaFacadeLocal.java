/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaClima;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaClimaFacadeLocal {

    void create(AccViaClima accViaClima);

    void edit(AccViaClima accViaClima);

    void remove(AccViaClima accViaClima);

    AccViaClima find(Object id);

    List<AccViaClima> findAll();

    List<AccViaClima> findRange(int[] range);

    int count();

    List<AccViaClima> estadoReg();

}
