/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvEvidencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvEvidenciaFacadeLocal {

    void create(AtvEvidencia atvEvidencia);

    void edit(AtvEvidencia atvEvidencia);

    void remove(AtvEvidencia atvEvidencia);

    AtvEvidencia find(Object id);

    List<AtvEvidencia> findAll();

    List<AtvEvidencia> findRange(int[] range);

    int count();

    /**
     * Permite consultar las evidencias de una novedad
     *
     * @param idNovedad
     * @return
     */
    List<AtvEvidencia> findByIdNovedad(Integer idNovedad);

}
