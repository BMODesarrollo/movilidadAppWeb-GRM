/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterLesionado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterLesionadoFacadeLocal {

    void create(AccInformeMasterLesionado accInformeMasterLesionado);

    void edit(AccInformeMasterLesionado accInformeMasterLesionado);

    void remove(AccInformeMasterLesionado accInformeMasterLesionado);

    AccInformeMasterLesionado find(Object id);

    List<AccInformeMasterLesionado> findAll();

    List<AccInformeMasterLesionado> findRange(int[] range);

    int count();
    
}
