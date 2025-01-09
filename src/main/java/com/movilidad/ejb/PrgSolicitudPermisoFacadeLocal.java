/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudPermiso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgSolicitudPermisoFacadeLocal {

    void create(PrgSolicitudPermiso prgSolicitudPermiso);

    void edit(PrgSolicitudPermiso prgSolicitudPermiso);

    void remove(PrgSolicitudPermiso prgSolicitudPermiso);

    PrgSolicitudPermiso find(Object id);

    PrgSolicitudPermiso findBySolicitud(Integer idSolicitud);

    List<PrgSolicitudPermiso> findAll();

    List<PrgSolicitudPermiso> findRange(int[] range);

    int count();

}
