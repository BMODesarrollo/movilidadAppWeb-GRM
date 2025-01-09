/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaServerParamEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NominaServerParamEmpresaFacadeLocal {

    void create(NominaServerParamEmpresa nominaServerParamEmpresa);

    void edit(NominaServerParamEmpresa nominaServerParamEmpresa);

    void remove(NominaServerParamEmpresa nominaServerParamEmpresa);

    NominaServerParamEmpresa find(Object id);

    NominaServerParamEmpresa findByUfAndCodigoSw(Integer idNominaServerParamEmpresa, int idGopUnidadFuncional, String codigo);

    List<NominaServerParamEmpresa> findAll();

    List<NominaServerParamEmpresa> findAllByEstadoRegAndUnidadFuncional(int idGopUnidadFuncional);

    List<NominaServerParamEmpresa> findRange(int[] range);

    int count();

    NominaServerParamEmpresa findByIdNominaServerParamAndUf(Integer idNominaServerParam, int idGopUnidadFuncional);
}
