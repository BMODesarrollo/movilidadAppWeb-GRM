/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaZona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaZonaFacadeLocal {

    void create(AccViaZona accViaZona);

    void edit(AccViaZona accViaZona);

    void remove(AccViaZona accViaZona);

    AccViaZona find(Object id);

    List<AccViaZona> findAll();

    List<AccViaZona> findRange(int[] range);

    int count();

    List<AccViaZona> estadoReg();

}