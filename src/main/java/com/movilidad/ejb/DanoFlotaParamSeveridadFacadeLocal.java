/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaParamSeveridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface DanoFlotaParamSeveridadFacadeLocal {

    void create(DanoFlotaParamSeveridad danoFlotaParamSeveridad);

    void edit(DanoFlotaParamSeveridad danoFlotaParamSeveridad);

    void remove(DanoFlotaParamSeveridad danoFlotaParamSeveridad);

    DanoFlotaParamSeveridad find(Object id);

    List<DanoFlotaParamSeveridad> findAll();

    List<DanoFlotaParamSeveridad> findRange(int[] range);

    int count();

    List<DanoFlotaParamSeveridad> getAllActivo();  
    
}
