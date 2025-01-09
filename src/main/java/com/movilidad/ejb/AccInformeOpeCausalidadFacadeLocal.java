/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeOpeCausalidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeOpeCausalidadFacadeLocal {

    void create(AccInformeOpeCausalidad accInformeOpeCausalidad);

    void edit(AccInformeOpeCausalidad accInformeOpeCausalidad);

    void remove(AccInformeOpeCausalidad accInformeOpeCausalidad);

    AccInformeOpeCausalidad find(Object id);

    List<AccInformeOpeCausalidad> findAll();

    List<AccInformeOpeCausalidad> findRange(int[] range);

    int count();
    
}
