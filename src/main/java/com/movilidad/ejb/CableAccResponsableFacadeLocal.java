/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccResponsable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccResponsableFacadeLocal {

    void create(CableAccResponsable cableAccResponsable);

    void edit(CableAccResponsable cableAccResponsable);

    void remove(CableAccResponsable cableAccResponsable);

    CableAccResponsable find(Object id);

    List<CableAccResponsable> findAll();

    List<CableAccResponsable> findRange(int[] range);

    int count();

    List<CableAccResponsable> findAllEstadoReg();

}
