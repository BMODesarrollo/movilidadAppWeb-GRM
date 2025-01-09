/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeVic;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeVicFacadeLocal {

    void create(AccInformeVic accInformeVic);

    void edit(AccInformeVic accInformeVic);

    void remove(AccInformeVic accInformeVic);

    AccInformeVic find(Object id);

    List<AccInformeVic> findAll();

    List<AccInformeVic> findRange(int[] range);

    int count();
    
}
