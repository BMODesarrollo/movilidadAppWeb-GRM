/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterTestigo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterTestigoFacadeLocal {

    void create(AccInformeMasterTestigo accInformeMasterTestigo);

    void edit(AccInformeMasterTestigo accInformeMasterTestigo);

    void remove(AccInformeMasterTestigo accInformeMasterTestigo);

    AccInformeMasterTestigo find(Object id);

    List<AccInformeMasterTestigo> findAll();

    List<AccInformeMasterTestigo> findRange(int[] range);

    int count();
    
}
