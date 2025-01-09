/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstLaborTipoDocs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstLaborTipoDocsFacadeLocal {

    void create(SstLaborTipoDocs sstLaborTipoDocs);

    void edit(SstLaborTipoDocs sstLaborTipoDocs);

    void remove(SstLaborTipoDocs sstLaborTipoDocs);

    SstLaborTipoDocs find(Object id);
    
    SstLaborTipoDocs findByTipoLaborAndDocTercero(Integer idTipoDocTercero,Integer idTipoLabor);

    List<SstLaborTipoDocs> findAll();
    
    List<SstLaborTipoDocs> findByTipoLabor(Integer idTipoLabor);
    
    List<SstLaborTipoDocs> findAllEstadoReg();

    List<SstLaborTipoDocs> findRange(int[] range);

    int count();
    
}
