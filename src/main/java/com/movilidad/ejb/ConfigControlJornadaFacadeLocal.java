/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConfigControlJornada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ConfigControlJornadaFacadeLocal {

    void create(ConfigControlJornada configControlJornada);

    void edit(ConfigControlJornada configControlJornada);

    void remove(ConfigControlJornada configControlJornada);

    ConfigControlJornada find(Object id);

    List<ConfigControlJornada> findAll();

    List<ConfigControlJornada> findRange(int[] range);

    int count();
    
    List<ConfigControlJornada> findAllByEstadoRegActivo();
    
}
