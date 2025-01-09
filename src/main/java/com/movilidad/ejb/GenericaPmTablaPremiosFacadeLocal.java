/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmTablaPremios;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPmTablaPremiosFacadeLocal {

    void create(GenericaPmTablaPremios genericaPmTablaPremios);

    void edit(GenericaPmTablaPremios genericaPmTablaPremios);

    void remove(GenericaPmTablaPremios genericaPmTablaPremios);

    GenericaPmTablaPremios find(Object id);

    GenericaPmTablaPremios verificarFecha(Date fecha, Integer idRegistro, Integer idArea, Integer puntoMin, Integer puntoMax);
    
    GenericaPmTablaPremios verificarPosicion(Date fecha, Integer idRegistro, Integer idArea, Integer pocision);

    List<GenericaPmTablaPremios> findAll();

    List<GenericaPmTablaPremios> findAllByEstadoReg(Integer idArea);

    List<GenericaPmTablaPremios> findRange(int[] range);

    int count();

}
