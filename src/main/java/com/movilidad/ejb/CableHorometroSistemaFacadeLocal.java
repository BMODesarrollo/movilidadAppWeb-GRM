/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableHorometroSistema;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface CableHorometroSistemaFacadeLocal {

    void create(CableHorometroSistema cableHorometroSistema);

    void edit(CableHorometroSistema cableHorometroSistema);

    void remove(CableHorometroSistema cableHorometroSistema);

    CableHorometroSistema find(Object id);

    List<CableHorometroSistema> findAll();

    List<CableHorometroSistema> findRange(int[] range);

    int count();

    List<CableHorometroSistema> findAllByRangoFecha(Date desde, Date hasta);

    CableHorometroSistema findByFecha(Date fecha);
}
