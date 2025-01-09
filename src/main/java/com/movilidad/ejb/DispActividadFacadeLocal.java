/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispActividad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DispActividadFacadeLocal {

    void create(DispActividad dispActividad);

    void edit(DispActividad dispActividad);

    void remove(DispActividad dispActividad);

    DispActividad find(Object id);

    List<DispActividad> findAll();

    List<DispActividad> findRange(int[] range);
    
    List<DispActividad> findByFechaHora(Date fechaHora);

    int count();

    List<DispActividad> findAllByIdNovedad(int idNovedad);

    DispActividad findDiferidaByIdNovedad(int idNovedad, int idDispActividad);
}
