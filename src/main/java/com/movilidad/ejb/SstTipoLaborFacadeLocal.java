/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstTipoLabor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstTipoLaborFacadeLocal {

    void create(SstTipoLabor sstTipoLabor);

    void edit(SstTipoLabor sstTipoLabor);

    void remove(SstTipoLabor sstTipoLabor);

    SstTipoLabor find(Object id);
    
    SstTipoLabor findByTipoLabor(String tipo);

    List<SstTipoLabor> findAll();
    
    List<SstTipoLabor> findAllEstadoReg();

    List<SstTipoLabor> findRange(int[] range);

    int count();
    
}
