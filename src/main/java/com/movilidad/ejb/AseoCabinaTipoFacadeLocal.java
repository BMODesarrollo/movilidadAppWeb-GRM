/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoCabinaTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AseoCabinaTipoFacadeLocal {

    void create(AseoCabinaTipo aseoCabinaTipo);

    void edit(AseoCabinaTipo aseoCabinaTipo);

    void remove(AseoCabinaTipo aseoCabinaTipo);

    AseoCabinaTipo find(Object id);

    AseoCabinaTipo findByNombre(String nombre, Integer idRegistro);

    List<AseoCabinaTipo> findAll();

    List<AseoCabinaTipo> findAllByEstadoReg();

    List<AseoCabinaTipo> findRange(int[] range);

    int count();

}
