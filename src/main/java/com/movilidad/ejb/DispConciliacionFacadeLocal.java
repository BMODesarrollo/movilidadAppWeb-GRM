/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispConciliacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface DispConciliacionFacadeLocal {

    void create(DispConciliacion dispConciliacion);

    void edit(DispConciliacion dispConciliacion);

    void remove(DispConciliacion dispConciliacion);

    DispConciliacion find(Object id);

    List<DispConciliacion> findByFecha(Date fecha, int idGopUnidadFuncional,Integer flagConciliado);

    DispConciliacion obtenerUltimaConciliacion();

    List<DispConciliacion> findAll();

    List<DispConciliacion> findRange(int[] range);

    int count();

}
