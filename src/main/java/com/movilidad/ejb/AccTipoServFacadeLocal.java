/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoServ;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoServFacadeLocal {

    void create(AccTipoServ accTipoServ);

    void edit(AccTipoServ accTipoServ);

    void remove(AccTipoServ accTipoServ);

    AccTipoServ find(Object id);

    List<AccTipoServ> findAll();

    List<AccTipoServ> findRange(int[] range);

    int count();
    
    List<AccTipoServ> estadoReg();
    
}
