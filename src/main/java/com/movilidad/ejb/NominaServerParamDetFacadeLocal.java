/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaServerParamDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NominaServerParamDetFacadeLocal {

    void create(NominaServerParamDet nominaServerParamDet);

    void edit(NominaServerParamDet nominaServerParamDet);

    void remove(NominaServerParamDet nominaServerParamDet);

    NominaServerParamDet find(Object id);
    
    NominaServerParamDet findByIdNovedadTipoDetalle(Integer idNovedadTipoDetalle);

    List<NominaServerParamDet> findAll();
    
    List<NominaServerParamDet> findAllByEstadoReg();

    List<NominaServerParamDet> findRange(int[] range);

    int count();
    
}
