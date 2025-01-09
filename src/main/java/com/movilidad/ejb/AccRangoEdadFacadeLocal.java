/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccRangoEdad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccRangoEdadFacadeLocal {

    void create(AccRangoEdad accRangoEdad);

    void edit(AccRangoEdad accRangoEdad);

    void remove(AccRangoEdad accRangoEdad);

    AccRangoEdad find(Object id);

    List<AccRangoEdad> findAll();

    List<AccRangoEdad> findRange(int[] range);

    int count();

    List<AccRangoEdad> estadoReg();
}
