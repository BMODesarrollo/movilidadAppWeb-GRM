/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterInspectores;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterInspectoresFacadeLocal {

    void create(AccInformeMasterInspectores accInformeMasterInspectores);

    void edit(AccInformeMasterInspectores accInformeMasterInspectores);

    void remove(AccInformeMasterInspectores accInformeMasterInspectores);

    AccInformeMasterInspectores find(Object id);

    List<AccInformeMasterInspectores> findAll();

    List<AccInformeMasterInspectores> findRange(int[] range);

    int count();
    
}
