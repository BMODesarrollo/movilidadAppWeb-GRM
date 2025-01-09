/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaCondicion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaCondicionFacadeLocal {

    void create(AccViaCondicion accViaCondicion);

    void edit(AccViaCondicion accViaCondicion);

    void remove(AccViaCondicion accViaCondicion);

    AccViaCondicion find(Object id);

    List<AccViaCondicion> findAll();

    List<AccViaCondicion> findRange(int[] range);

    int count();

    List<AccViaCondicion> estadoReg();

}
