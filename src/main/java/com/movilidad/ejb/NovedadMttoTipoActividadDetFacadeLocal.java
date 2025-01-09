/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipoActividadDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface NovedadMttoTipoActividadDetFacadeLocal {

    void create(NovedadMttoTipoActividadDet novedadMttoTipoActividadDet);

    void edit(NovedadMttoTipoActividadDet novedadMttoTipoActividadDet);

    void remove(NovedadMttoTipoActividadDet novedadMttoTipoActividadDet);

    NovedadMttoTipoActividadDet find(Object id);

    List<NovedadMttoTipoActividadDet> findAll();

    List<NovedadMttoTipoActividadDet> findRange(int[] range);

    int count();
    
    List<NovedadMttoTipoActividadDet> findAllEstadoRegByIdNovMttoTpAct(Integer idNovMttoTpAct);
    
    List<NovedadMttoTipoActividadDet> findAllEstadoReg();
    
}
