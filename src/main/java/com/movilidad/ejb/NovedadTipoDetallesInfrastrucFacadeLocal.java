/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoDetallesInfrastruc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadTipoDetallesInfrastrucFacadeLocal {

    void create(NovedadTipoDetallesInfrastruc novedadTipoDetallesInfrastruc);

    void edit(NovedadTipoDetallesInfrastruc novedadTipoDetallesInfrastruc);

    void remove(NovedadTipoDetallesInfrastruc novedadTipoDetallesInfrastruc);

    NovedadTipoDetallesInfrastruc find(Object id);

    List<NovedadTipoDetallesInfrastruc> findAll();

    List<NovedadTipoDetallesInfrastruc> findRange(int[] range);

    int count();

    NovedadTipoDetallesInfrastruc findByNombre(String nombre, Integer idRegistro);

    List<NovedadTipoDetallesInfrastruc> findAllByEstadoReg();

}
