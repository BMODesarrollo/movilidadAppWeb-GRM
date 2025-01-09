/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadInfrastruc;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadInfrastrucFacadeLocal {

    void create(NovedadInfrastruc novedadInfrastruc);

    void edit(NovedadInfrastruc novedadInfrastruc);

    void remove(NovedadInfrastruc novedadInfrastruc);

    NovedadInfrastruc find(Object id);

    List<NovedadInfrastruc> findAll();

    List<NovedadInfrastruc> findRange(int[] range);

    int count();
    
    List<NovedadInfrastruc> findRanfoFechaEstadoReg(Date desde, Date hasta);
}
