/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccVehFalla;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccVehFallaFacadeLocal {

    void create(AccVehFalla accVehFalla);

    void edit(AccVehFalla accVehFalla);

    void remove(AccVehFalla accVehFalla);

    AccVehFalla find(Object id);

    List<AccVehFalla> findAll();

    List<AccVehFalla> findRange(int[] range);

    int count();

    List<AccVehFalla> estadoReg();

}
