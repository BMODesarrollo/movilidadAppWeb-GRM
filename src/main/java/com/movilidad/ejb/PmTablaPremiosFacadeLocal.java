/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmTablaPremios;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PmTablaPremiosFacadeLocal {

    void create(PmTablaPremios pmTablaPremios);

    void edit(PmTablaPremios pmTablaPremios);

    void remove(PmTablaPremios pmTablaPremios);

    PmTablaPremios find(Object id);
    
    PmTablaPremios verificarFecha(Date fecha, Integer idRegistro, Integer puntoMin, Integer puntoMax);
    
    PmTablaPremios verificarPosicion(Date fecha, Integer idRegistro, Integer pocision);

    List<PmTablaPremios> findAll();
    
    List<PmTablaPremios> findAllByEstadoReg();

    List<PmTablaPremios> findRange(int[] range);

    int count();
    
}
