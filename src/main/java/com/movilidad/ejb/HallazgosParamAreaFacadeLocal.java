/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.HallazgosParamArea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface HallazgosParamAreaFacadeLocal {

    void create(HallazgosParamArea hallazgosParamArea);

    void edit(HallazgosParamArea hallazgosParamArea);

    void remove(HallazgosParamArea hallazgosParamArea);

    HallazgosParamArea find(Object id);

    HallazgosParamArea findByNombre(String nombre, Integer idRegistro);

    List<HallazgosParamArea> findAll();

    List<HallazgosParamArea> findAllByEstadoReg();

    List<HallazgosParamArea> findRange(int[] range);

    int count();

}
