/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMaster;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterFacadeLocal {

    void create(AccInformeMaster accInformeMaster);

    void edit(AccInformeMaster accInformeMaster);

    void remove(AccInformeMaster accInformeMaster);

    AccInformeMaster find(Object id);

    List<AccInformeMaster> findAll();

    List<AccInformeMaster> findRange(int[] range);

    int count();
    
}
