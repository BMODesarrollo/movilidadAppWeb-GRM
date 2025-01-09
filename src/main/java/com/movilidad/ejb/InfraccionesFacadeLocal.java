/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Infracciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar Beltrán
 */
@Local
public interface InfraccionesFacadeLocal {

    void create(Infracciones infraccion);

    void edit(Infracciones infraccion);

    void remove(Infracciones infraccion);

    Infracciones find(Object id);

    List<Infracciones> findAll();

    List<Infracciones> findAllByDateRangeAndEstadoReg(Date desde, Date hasta);

    List<Infracciones> findAllByDateRangeAndArea(Date desde, Date hasta, int idInfraccionesParamArea);

    List<Infracciones> findRange(int[] range);

    int count();

    Infracciones findByIdNovedad(int idNovedad);
    
    Infracciones findByIdICO(int idICO);
}
