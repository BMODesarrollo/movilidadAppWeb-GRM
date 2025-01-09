/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoCriticidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MttoCriticidadFacadeLocal {

    void create(MttoCriticidad mttoCriticidad);

    void edit(MttoCriticidad mttoCriticidad);

    void remove(MttoCriticidad mttoCriticidad);

    MttoCriticidad find(Object id);

    List<MttoCriticidad> findAll();

    List<MttoCriticidad> findRange(int[] range);

    int count();
    
}
