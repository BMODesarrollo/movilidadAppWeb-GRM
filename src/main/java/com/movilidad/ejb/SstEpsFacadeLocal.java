/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEps;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstEpsFacadeLocal {

    void create(SstEps sstEps);

    void edit(SstEps sstEps);

    void remove(SstEps sstEps);

    SstEps find(Object id);

    List<SstEps> findAll();

    List<SstEps> findRange(int[] range);

    int count();
    
    List<SstEps> findAllEstadoReg();
    
}
