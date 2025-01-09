/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoMunicipio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoMunicipioFacadeLocal {

    void create(EmpleadoMunicipio empleadoMunicipio);

    void edit(EmpleadoMunicipio empleadoMunicipio);

    void remove(EmpleadoMunicipio empleadoMunicipio);

    EmpleadoMunicipio find(Object id);

    List<EmpleadoMunicipio> findAll();

    List<EmpleadoMunicipio> findRange(int[] range);

    int count();
    
}
