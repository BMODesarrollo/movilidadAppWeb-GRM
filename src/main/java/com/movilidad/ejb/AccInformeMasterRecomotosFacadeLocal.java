/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterRecomotos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterRecomotosFacadeLocal {

    void create(AccInformeMasterRecomotos accInformeMasterRecomotos);

    void edit(AccInformeMasterRecomotos accInformeMasterRecomotos);

    void remove(AccInformeMasterRecomotos accInformeMasterRecomotos);

    AccInformeMasterRecomotos find(Object id);

    List<AccInformeMasterRecomotos> findAll();

    List<AccInformeMasterRecomotos> findRange(int[] range);

    int count();
    
}
