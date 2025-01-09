/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaGeometrica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaGeometricaFacadeLocal {

    void create(AccViaGeometrica accViaGeometrica);

    void edit(AccViaGeometrica accViaGeometrica);

    void remove(AccViaGeometrica accViaGeometrica);

    AccViaGeometrica find(Object id);

    List<AccViaGeometrica> findAll();

    List<AccViaGeometrica> findRange(int[] range);

    int count();

    List<AccViaGeometrica> estadoReg();

}
