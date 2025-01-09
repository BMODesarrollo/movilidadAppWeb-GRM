/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccInformeRespondiente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface CableAccInformeRespondienteFacadeLocal {

    void create(CableAccInformeRespondiente cableAccInformeRespondiente);

    void edit(CableAccInformeRespondiente cableAccInformeRespondiente);

    void remove(CableAccInformeRespondiente cableAccInformeRespondiente);

    CableAccInformeRespondiente find(Object id);

    List<CableAccInformeRespondiente> findAll();

    List<CableAccInformeRespondiente> findRange(int[] range);

    int count();

    CableAccInformeRespondiente findByCableAccidentalidad(Integer idCableAccidentalidad);

}
