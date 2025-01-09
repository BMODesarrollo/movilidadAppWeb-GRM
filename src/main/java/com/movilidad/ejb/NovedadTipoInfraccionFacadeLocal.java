/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoInfraccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadTipoInfraccionFacadeLocal {

    void create(NovedadTipoInfraccion novedadTipoInfrastruc);

    void edit(NovedadTipoInfraccion novedadTipoInfrastruc);

    void remove(NovedadTipoInfraccion novedadTipoInfrastruc);

    NovedadTipoInfraccion find(Object id);

    List<NovedadTipoInfraccion> findAll();

    List<NovedadTipoInfraccion> findRange(int[] range);

    int count();

    List<NovedadTipoInfraccion> findAllByEstadoReg();

}
