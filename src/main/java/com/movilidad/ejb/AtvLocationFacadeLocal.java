/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvLocation;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvLocationFacadeLocal {

    void create(AtvLocation atvLocation);

    void edit(AtvLocation atvLocation);

    void remove(AtvLocation atvLocation);

    AtvLocation find(Object id);

    List<AtvLocation> findAll();

    List<AtvLocation> findRange(int[] range);

    int count();

    /**
     * Permite consultar la ubicacion por un rango de fecha (minutos de tiempo)
     * la ruta real del vehiculo de atencion
     *
     * @param idNovedad
     * @param inicio
     * @param fin
     * @return
     */
    List<AtvLocation> findByIdNovedadAndInicioBetweenFin(Integer idNovedad, Date inicio, Date fin);

}
