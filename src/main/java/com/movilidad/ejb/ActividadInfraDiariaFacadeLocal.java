/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ActividadInfraDiaria;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface ActividadInfraDiariaFacadeLocal {

    void create(ActividadInfraDiaria actividadInfraDiaria);

    void edit(ActividadInfraDiaria actividadInfraDiaria);

    void remove(ActividadInfraDiaria actividadInfraDiaria);

    ActividadInfraDiaria find(Object id);

    List<ActividadInfraDiaria> findAll();

    List<ActividadInfraDiaria> findRange(int[] range);

    int count();

    List<ActividadInfraDiaria> findAllByFechaHora(Date ini, Date fin);

}
