/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispConciliacionDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface DispConciliacionDetFacadeLocal {

    void create(DispConciliacionDet dispConciliacionDet);

    void edit(DispConciliacionDet dispConciliacionDet);

    void remove(DispConciliacionDet dispConciliacionDet);

    DispConciliacionDet find(Object id);

    List<DispConciliacionDet> findAll();

    List<DispConciliacionDet> obtenerDetalles(Date fecha, Integer idGopUnidadFuncional);

    List<DispConciliacionDet> findRange(int[] range);

    int count();

    List<DispConciliacionDet> obtenerDetallesByIdDispConciliacion(int idDispConciliacion, int idGopUnidadFuncional);
}
