/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmTipoConcepto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPmTipoConceptoFacadeLocal {

    void create(GenericaPmTipoConcepto genericaPmTipoConcepto);

    void edit(GenericaPmTipoConcepto genericaPmTipoConcepto);

    void remove(GenericaPmTipoConcepto genericaPmTipoConcepto);

    GenericaPmTipoConcepto find(Object id);

    List<GenericaPmTipoConcepto> findAll();

    List<GenericaPmTipoConcepto> findRange(int[] range);

    int count();
    
}
