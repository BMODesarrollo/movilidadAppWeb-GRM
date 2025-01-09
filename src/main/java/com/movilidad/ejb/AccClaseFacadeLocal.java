/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccClase;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccClaseFacadeLocal {

    void create(AccClase accClase);

    void edit(AccClase accClase);

    void remove(AccClase accClase);

    AccClase find(Object id);

    List<AccClase> findAll();

    List<AccClase> findRange(int[] range);

    int count();

    List<AccClase> estadoReg();

}
