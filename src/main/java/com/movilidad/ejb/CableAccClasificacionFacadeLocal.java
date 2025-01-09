/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccClasificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface CableAccClasificacionFacadeLocal {

    void create(CableAccClasificacion cableAccClasificacion);

    void edit(CableAccClasificacion cableAccClasificacion);

    void remove(CableAccClasificacion cableAccClasificacion);

    CableAccClasificacion find(Object id);

    List<CableAccClasificacion> findAll();

    List<CableAccClasificacion> findRange(int[] range);

    int count();

    List<CableAccClasificacion> findAllEstadoReg();

}
