/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoContratista;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoContratistaFacadeLocal {

    void create(LavadoContratista lavadoContratista);

    void edit(LavadoContratista lavadoContratista);

    void remove(LavadoContratista lavadoContratista);

    LavadoContratista find(Object id);

    LavadoContratista findByNombre(Integer idRegistro, String nombre, Date fechaDesde, Date fechaHasta);

    List<LavadoContratista> findAll();

    List<LavadoContratista> findAllActivos();

    List<LavadoContratista> findAllByEstadoReg();

    List<LavadoContratista> findRange(int[] range);

    int count();

}
