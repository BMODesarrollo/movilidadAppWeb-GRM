/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaJornadaTipoFacadeLocal {

    void create(GenericaJornadaTipo genericaJornadaTipo);

    void edit(GenericaJornadaTipo genericaJornadaTipo);

    void remove(GenericaJornadaTipo genericaJornadaTipo);

    GenericaJornadaTipo find(Object id);

    GenericaJornadaTipo findByDescripcion(String valor, int idJornadaT, int idArea);
    
    GenericaJornadaTipo findBySercon(String valor, int idArea);

    GenericaJornadaTipo findByHIniAndHFin(String horaInicio, String horaFin, int idArea);

    List<GenericaJornadaTipo> findAll();

    List<GenericaJornadaTipo> findAllByArea(int idArea);

    List<GenericaJornadaTipo> findRange(int[] range);

    int count();

}
