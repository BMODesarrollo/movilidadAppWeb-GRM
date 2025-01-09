/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterBomberos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterBomberosFacadeLocal {

    void create(AccInformeMasterBomberos accInformeMasterBomberos);

    void edit(AccInformeMasterBomberos accInformeMasterBomberos);

    void remove(AccInformeMasterBomberos accInformeMasterBomberos);

    AccInformeMasterBomberos find(Object id);

    List<AccInformeMasterBomberos> findAll();

    List<AccInformeMasterBomberos> findRange(int[] range);

    int count();
    
}
