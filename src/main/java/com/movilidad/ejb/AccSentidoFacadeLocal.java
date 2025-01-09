/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccSentido;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccSentidoFacadeLocal {

    void create(AccSentido accSentido);

    void edit(AccSentido accSentido);

    void remove(AccSentido accSentido);

    AccSentido find(Object id);

    List<AccSentido> findAll();

    List<AccSentido> findRange(int[] range);

    int count();
    
    List<AccSentido> estadoReg();
    
}
