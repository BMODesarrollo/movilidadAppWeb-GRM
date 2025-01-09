/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccDetClase;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccDetClaseFacadeLocal {

    void create(AccDetClase accDetClase);

    void edit(AccDetClase accDetClase);

    void remove(AccDetClase accDetClase);

    AccDetClase find(Object id);

    List<AccDetClase> findAll();

    List<AccDetClase> findRange(int[] range);

    int count();
    
    List<AccDetClase> estadoReg();
    
}
