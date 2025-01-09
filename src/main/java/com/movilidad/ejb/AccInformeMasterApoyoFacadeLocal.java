/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterApoyo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterApoyoFacadeLocal {

    void create(AccInformeMasterApoyo accInformeMasterApoyo);

    void edit(AccInformeMasterApoyo accInformeMasterApoyo);

    void remove(AccInformeMasterApoyo accInformeMasterApoyo);

    AccInformeMasterApoyo find(Object id);

    List<AccInformeMasterApoyo> findAll();

    List<AccInformeMasterApoyo> findRange(int[] range);

    int count();
    
}
