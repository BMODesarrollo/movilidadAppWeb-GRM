/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuLugar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar Beltrán
 */
@Local
public interface PlaRecuLugarFacadeLocal {

    void create(PlaRecuLugar actividad);

    void edit(PlaRecuLugar actividad);

    void remove(PlaRecuLugar actividad);

    PlaRecuLugar find(Object id);

    int count();
    
    PlaRecuLugar findById(String id);
    
    PlaRecuLugar findByLugar(String description);
    
    List<PlaRecuLugar> findAll();
    
    List<PlaRecuLugar> findAllByArea(int idInfraccionesParamArea);
}

