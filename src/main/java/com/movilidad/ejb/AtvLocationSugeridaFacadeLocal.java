/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvLocationSugerida;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvLocationSugeridaFacadeLocal {

    void create(AtvLocationSugerida atvLocationSugerida);

    void edit(AtvLocationSugerida atvLocationSugerida);

    void remove(AtvLocationSugerida atvLocationSugerida);

    AtvLocationSugerida find(Object id);

    List<AtvLocationSugerida> findAll();

    List<AtvLocationSugerida> findRange(int[] range);

    int count();
    
}
