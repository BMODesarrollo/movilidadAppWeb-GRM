/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableCabina;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableCabinaFacadeLocal {

    void create(CableCabina cableCabina);

    void edit(CableCabina cableCabina);

    void remove(CableCabina cableCabina);

    CableCabina find(Object id);

    CableCabina findByCodigo(String codigo, Integer idRegistro);

    CableCabina findByNombre(String nombre, Integer idRegistro);

    CableCabina verificarRangoFechas(Date fecha, Integer idRegistro);

    List<CableCabina> findAllByEstadoReg();

    List<CableCabina> findAll();

    List<CableCabina> findRange(int[] range);

    int count();

    List<CableCabina> findAllByEstadoRegAndNombreOrderBy(String order);

    List<CableCabina> findByLavadas(Date fecha, String order);

    List<CableCabina> findByNoOperando(Date fecha, String order);
}
