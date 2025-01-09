/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.DocumentosPdDTO;
import com.movilidad.model.PdMaestroSeguimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdMaestroSeguimientoFacadeLocal {

    void create(PdMaestroSeguimiento pdMaestroSeguimiento);

    void edit(PdMaestroSeguimiento pdMaestroSeguimiento);

    void remove(PdMaestroSeguimiento pdMaestroSeguimiento);

    PdMaestroSeguimiento find(Object id);

    List<PdMaestroSeguimiento> findAll();

    List<DocumentosPdDTO> findByIdProceso(Integer idProceso);

    List<PdMaestroSeguimiento> findRange(int[] range);

    int count();
    
}
