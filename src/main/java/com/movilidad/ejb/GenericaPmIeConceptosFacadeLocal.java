/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmIeConceptos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPmIeConceptosFacadeLocal {

    void create(GenericaPmIeConceptos genericaPmIeConceptos);

    void edit(GenericaPmIeConceptos genericaPmIeConceptos);

    void remove(GenericaPmIeConceptos genericaPmIeConceptos);

    GenericaPmIeConceptos find(Object id);

    GenericaPmIeConceptos findByConcepto(String concepto, Integer idRegistro, Integer idPmTipoConcepto, Integer idArea);

    List<GenericaPmIeConceptos> findAllByEstadoRegAndArea(Integer idArea);

    List<GenericaPmIeConceptos> findAll();

    List<GenericaPmIeConceptos> findRange(int[] range);

    int count();

}
