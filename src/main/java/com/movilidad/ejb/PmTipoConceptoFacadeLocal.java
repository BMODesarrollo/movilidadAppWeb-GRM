/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmTipoConcepto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PmTipoConceptoFacadeLocal {

    void create(PmTipoConcepto pmTipoConcepto);

    void edit(PmTipoConcepto pmTipoConcepto);

    void remove(PmTipoConcepto pmTipoConcepto);

    PmTipoConcepto find(Object id);

    List<PmTipoConcepto> findAll();

    List<PmTipoConcepto> findRange(int[] range);

    int count();
    
}