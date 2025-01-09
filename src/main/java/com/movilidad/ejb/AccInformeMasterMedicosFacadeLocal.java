/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterMedicos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterMedicosFacadeLocal {

    void create(AccInformeMasterMedicos accInformeMasterMedicos);

    void edit(AccInformeMasterMedicos accInformeMasterMedicos);

    void remove(AccInformeMasterMedicos accInformeMasterMedicos);

    AccInformeMasterMedicos find(Object id);

    List<AccInformeMasterMedicos> findAll();

    List<AccInformeMasterMedicos> findRange(int[] range);

    int count();
    
}
