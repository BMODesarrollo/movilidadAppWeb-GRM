/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuRuta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar Beltrán
 */
@Local
public interface PlaRecuRutaFacadeLocal {

    void create(PlaRecuRuta ruta);

    void edit(PlaRecuRuta ruta);

    void remove(PlaRecuRuta ruta);

    PlaRecuRuta find(Object id);

    int count();
    
    PlaRecuRuta findById(String id);
    
    PlaRecuRuta findByRuta(String description);
    
    List<PlaRecuRuta> findAll();
}

