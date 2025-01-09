/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ActividadInfraTipoDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ActividadInfraTipoDetFacadeLocal {

    void create(ActividadInfraTipoDet actividadInfraTipoDet);

    void edit(ActividadInfraTipoDet actividadInfraTipoDet);

    void remove(ActividadInfraTipoDet actividadInfraTipoDet);

    ActividadInfraTipoDet find(Object id);

    ActividadInfraTipoDet findByNombre(String nombre, Integer idRegistro, Integer idActividadInfraTipo);

    List<ActividadInfraTipoDet> findAllByEstadoReg();

    List<ActividadInfraTipoDet> findAll();

    List<ActividadInfraTipoDet> findRange(int[] range);

    int count();

    List<ActividadInfraTipoDet> findByActividadInfraTipo(Integer idActividadInfraTp);

}
