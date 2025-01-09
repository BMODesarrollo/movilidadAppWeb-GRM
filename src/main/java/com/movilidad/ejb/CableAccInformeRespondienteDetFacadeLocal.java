/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccInformeRespondienteDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface CableAccInformeRespondienteDetFacadeLocal {

    void create(CableAccInformeRespondienteDet cableAccInformeRespondienteDet);

    void edit(CableAccInformeRespondienteDet cableAccInformeRespondienteDet);

    void remove(CableAccInformeRespondienteDet cableAccInformeRespondienteDet);

    CableAccInformeRespondienteDet find(Object id);

    List<CableAccInformeRespondienteDet> findAll();

    List<CableAccInformeRespondienteDet> findRange(int[] range);

    int count();
    
}
