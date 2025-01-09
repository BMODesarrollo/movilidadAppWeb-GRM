/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmIeConceptos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PmIeConceptosFacadeLocal {

    void create(PmIeConceptos pmIeConceptos);

    void edit(PmIeConceptos pmIeConceptos);

    void remove(PmIeConceptos pmIeConceptos);

    PmIeConceptos find(Object id);
    
    PmIeConceptos findByConcepto(String concepto, Integer idRegistro,Integer idPmTipoConcepto);

    List<PmIeConceptos> findAllByEstadoReg();
    
    List<PmIeConceptos> findAll();

    List<PmIeConceptos> findRange(int[] range);

    int count();
    
}
