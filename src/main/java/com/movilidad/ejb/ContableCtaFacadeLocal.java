/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ContableCta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ContableCtaFacadeLocal {

    void create(ContableCta contableCta);

    void edit(ContableCta contableCta);

    void remove(ContableCta contableCta);

    ContableCta find(Object id);

    List<ContableCta> findAll();

    List<ContableCta> findRange(int[] range);

    int count();
    
    List<ContableCta> findAllEstadoReg();
    
}
