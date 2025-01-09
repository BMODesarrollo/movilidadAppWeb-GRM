/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdMaestroDetalle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdMaestroDetalleFacadeLocal {

    void create(PdMaestroDetalle pdMaestroDetalle);

    void edit(PdMaestroDetalle pdMaestroDetalle);

    void remove(PdMaestroDetalle pdMaestroDetalle);

    PdMaestroDetalle find(Object id);
    
    PdMaestroDetalle findByIdNovedad(Integer idNovedad);

    List<PdMaestroDetalle> findAll();
    
    List<PdMaestroDetalle> findByIdProceso(Integer idProceso);

    List<PdMaestroDetalle> findRange(int[] range);

    int count();
    
}
