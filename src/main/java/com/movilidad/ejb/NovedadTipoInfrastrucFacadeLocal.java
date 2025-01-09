/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoInfrastruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadTipoInfrastrucFacadeLocal {

    void create(NovedadTipoInfrastruc novedadTipoInfrastruc);

    void edit(NovedadTipoInfrastruc novedadTipoInfrastruc);

    void remove(NovedadTipoInfrastruc novedadTipoInfrastruc);

    NovedadTipoInfrastruc find(Object id);

    List<NovedadTipoInfrastruc> findAll();

    List<NovedadTipoInfrastruc> findRange(int[] range);

    int count();

    NovedadTipoInfrastruc findByNombre(String nombre, Integer idRegistro);

    List<NovedadTipoInfrastruc> findAllByEstadoReg();

}
