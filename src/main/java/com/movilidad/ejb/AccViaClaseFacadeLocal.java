/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaClase;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaClaseFacadeLocal {

    void create(AccViaClase accViaClase);

    void edit(AccViaClase accViaClase);

    void remove(AccViaClase accViaClase);

    AccViaClase find(Object id);

    List<AccViaClase> findAll();

    List<AccViaClase> findRange(int[] range);

    int count();
    
     List<AccViaClase> estadoReg();
    
}
