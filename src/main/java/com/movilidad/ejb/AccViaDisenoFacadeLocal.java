/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaDiseno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaDisenoFacadeLocal {

    void create(AccViaDiseno accViaDiseno);

    void edit(AccViaDiseno accViaDiseno);

    void remove(AccViaDiseno accViaDiseno);

    AccViaDiseno find(Object id);

    List<AccViaDiseno> findAll();

    List<AccViaDiseno> findRange(int[] range);

    int count();

    List<AccViaDiseno> estadoReg();

}
