/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvNovedadDocumento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvNovedadDocumentoFacadeLocal {

    void create(AtvNovedadDocumento atvNovedadDocumento);

    void edit(AtvNovedadDocumento atvNovedadDocumento);

    void remove(AtvNovedadDocumento atvNovedadDocumento);

    AtvNovedadDocumento find(Object id);

    List<AtvNovedadDocumento> findAll();

    List<AtvNovedadDocumento> findRange(int[] range);

    int count();

    /**
     * Permite obtener los documentos cargados para una novedad de atv
     *
     * @param idNovedad
     * @return
     */
    List<AtvNovedadDocumento> findAllByIdNovedadAndActivos(Integer idNovedad);

}
