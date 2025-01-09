/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccDocumento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface CableAccDocumentoFacadeLocal {

    void create(CableAccDocumento cableAccDocumento);

    void edit(CableAccDocumento cableAccDocumento);

    void remove(CableAccDocumento cableAccDocumento);

    CableAccDocumento find(Object id);

    List<CableAccDocumento> findAll();

    List<CableAccDocumento> findRange(int[] range);

    int count();

    List<CableAccDocumento> findByAccidentalidadAndEstadoReg(Integer idCableAccidentalidad);
}
