/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterAgentes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterAgentesFacadeLocal {

    void create(AccInformeMasterAgentes accInformeMasterAgentes);

    void edit(AccInformeMasterAgentes accInformeMasterAgentes);

    void remove(AccInformeMasterAgentes accInformeMasterAgentes);

    AccInformeMasterAgentes find(Object id);

    List<AccInformeMasterAgentes> findAll();

    List<AccInformeMasterAgentes> findRange(int[] range);

    int count();
    
}
