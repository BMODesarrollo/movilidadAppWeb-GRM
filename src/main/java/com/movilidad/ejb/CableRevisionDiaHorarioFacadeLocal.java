/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionDiaHorario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableRevisionDiaHorarioFacadeLocal {

    void create(CableRevisionDiaHorario cableRevisionDiaHorario);

    void edit(CableRevisionDiaHorario cableRevisionDiaHorario);

    void remove(CableRevisionDiaHorario cableRevisionDiaHorario);

    CableRevisionDiaHorario find(Object id);

    CableRevisionDiaHorario findByHora(String hora, Integer idRegistro);

    List<CableRevisionDiaHorario> findAll();

    List<CableRevisionDiaHorario> findAllByEstadoReg();

    List<CableRevisionDiaHorario> findRange(int[] range);

    int count();

}
