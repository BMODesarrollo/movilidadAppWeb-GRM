/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegAseoArmamento;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegAseoArmamentoFacadeLocal {

    void create(SegAseoArmamento segPlantillaAseoArmamento);

    void edit(SegAseoArmamento segPlantillaAseoArmamento);

    void remove(SegAseoArmamento segPlantillaAseoArmamento);

    SegAseoArmamento find(Object id);

    List<SegAseoArmamento> findAll();

    List<SegAseoArmamento> findRange(int[] range);

    int count();

    List<SegAseoArmamento> findRangoFechaEstadoReg(Date desde, Date hasta);
}