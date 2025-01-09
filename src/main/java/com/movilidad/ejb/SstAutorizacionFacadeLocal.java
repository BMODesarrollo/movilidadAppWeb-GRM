/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstAutorizacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstAutorizacionFacadeLocal {

    void create(SstAutorizacion sstAutorizacion);

    void edit(SstAutorizacion sstAutorizacion);

    void remove(SstAutorizacion sstAutorizacion);

    SstAutorizacion find(Object id);

    SstAutorizacion verificarRegistroAutorizacion(Date desde, Date hasta, String cedula);

    SstAutorizacion obtenerAutorizacionEntradaSalida(Date fecha, String cedula);

    List<SstAutorizacion> findAll();

    List<SstAutorizacion> findAllEstadoReg();

    List<SstAutorizacion> findRange(int[] range);

    List<SstAutorizacion> findBySstEmpresa(Integer idSstEmpresa);

    int count();

}
