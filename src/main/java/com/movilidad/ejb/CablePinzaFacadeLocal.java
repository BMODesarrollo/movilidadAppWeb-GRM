/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CablePinza;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CablePinzaFacadeLocal {

    void create(CablePinza cablePinza);

    void edit(CablePinza cablePinza);

    void remove(CablePinza cablePinza);

    CablePinza find(Object id);

    CablePinza findByCodigo(String codigo, Integer idRegistro);

    CablePinza findByNombre(String nombre, Integer idRegistro);

    CablePinza verificarRangoFechas(Date fecha, Integer idRegistro);

    List<CablePinza> findAll();

    List<CablePinza> findAllByEstadoReg();

    List<CablePinza> findRange(int[] range);

    int count();

}
