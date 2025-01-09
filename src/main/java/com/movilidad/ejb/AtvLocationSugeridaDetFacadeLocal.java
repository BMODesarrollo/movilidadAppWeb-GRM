/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvLocationSugeridaDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvLocationSugeridaDetFacadeLocal {

    void create(AtvLocationSugeridaDet atvLocationSugeridaDet);

    void edit(AtvLocationSugeridaDet atvLocationSugeridaDet);

    void remove(AtvLocationSugeridaDet atvLocationSugeridaDet);

    AtvLocationSugeridaDet find(Object id);

    List<AtvLocationSugeridaDet> findAll();

    List<AtvLocationSugeridaDet> findRange(int[] range);

    int count();
    
}
