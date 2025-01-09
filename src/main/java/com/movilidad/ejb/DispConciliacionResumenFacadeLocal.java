/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispConciliacionResumen;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface DispConciliacionResumenFacadeLocal {

    void create(DispConciliacionResumen dispConciliacionResumen);

    void edit(DispConciliacionResumen dispConciliacionResumen);

    void remove(DispConciliacionResumen dispConciliacionResumen);

    DispConciliacionResumen find(Object id);

    List<DispConciliacionResumen> findAll();

    List<DispConciliacionResumen> obtenerResumen(Integer idGopUnidadFuncional);

    List<DispConciliacionResumen> obtenerResumenPorUfAndIdConciliacion(Integer idConciliacion, int idGopUnidadFuncional, Integer flagConciliado);
    
    List<DispConciliacionResumen> findByFechaHora(String fecha);

    List<DispConciliacionResumen> findRange(int[] range);

    int count();

}
