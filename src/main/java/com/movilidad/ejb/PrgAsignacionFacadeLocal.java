/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgAsignacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PrgAsignacionFacadeLocal {

    void create(PrgAsignacion prgAsignacion);

    void edit(PrgAsignacion prgAsignacion);

    void remove(PrgAsignacion prgAsignacion);

    PrgAsignacion find(Object id);

    List<PrgAsignacion> findAll();

    List<PrgAsignacion> findRange(int[] range);

    List<PrgAsignacion> getTareasProgramadasMtto(Date desde, Date hasta, int idGopUF);

    List<PrgAsignacion> findAsignacionDiaByFechaAndIdGopUF(Date fecha, int idGopUF);
    
    Integer countAsignacionDiaByFechaAndIdGopUF(Date fecha, int idGopUF);

    int count();

    int removeByDate(Date d, int idGopUF);

}
