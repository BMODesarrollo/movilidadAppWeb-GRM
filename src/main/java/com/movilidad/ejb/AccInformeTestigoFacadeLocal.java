/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeTestigo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeTestigoFacadeLocal {

    void create(AccInformeTestigo accInformeTestigo);

    void edit(AccInformeTestigo accInformeTestigo);

    void remove(AccInformeTestigo accInformeTestigo);

    AccInformeTestigo find(Object id);

    List<AccInformeTestigo> findAll();

    List<AccInformeTestigo> findRange(int[] range);

    int count();
    
}
