/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgDesasignarParam;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface PrgDesasignarParamFacadeLocal {

    void create(PrgDesasignarParam prgDesasignarParam);

    void edit(PrgDesasignarParam prgDesasignarParam);

    void remove(PrgDesasignarParam prgDesasignarParam);

    PrgDesasignarParam find(Object id);

    List<PrgDesasignarParam> findAll();

    List<PrgDesasignarParam> findRange(int[] range);

    int count();

    /**
     * Retorna List objetos de PrgDesasignarParam donde estado_reg = 0
     *
     * @return List de objetos PrgDesasignarParam
     */
    List<PrgDesasignarParam> findAllEstadoReg();

    /**
     * Consultar los PrgDesasignarParam asociados a NovedadTipoDetalle
     * idPrgDesasignarParam no puede ser null, idNovTpDet no puede ser null
     *
     * @param idPrgDesasignarParam Identificador unico Objeto PrgDesasignarParam
     * @param idNovTpDet Identificador unico Objeto NovedadTipoDetalle
     * @return List -> PrgDesasignarParam
     */
    List<PrgDesasignarParam> findByIdNovedadTipoDetalle(Integer idPrgDesasignarParam, Integer idNovTpDet);
}
