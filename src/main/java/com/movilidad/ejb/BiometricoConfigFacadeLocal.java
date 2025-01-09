/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.BiometricoConfig;
import com.movilidad.util.beans.NovedadesMarcaciones;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Omar.beltran
 */
public interface BiometricoConfigFacadeLocal {

void create(BiometricoConfig biometricoConfig);

    void edit(BiometricoConfig biometricoConfig);

    void remove(BiometricoConfig biometricoConfig);

    BiometricoConfig find(Object id);

    List<BiometricoConfig> findAllActivos(int idGopUnidadFuncional, int idArea);

    List<BiometricoConfig> findAll();

    List<BiometricoConfig> findRange(int[] range);

    int count();

}

