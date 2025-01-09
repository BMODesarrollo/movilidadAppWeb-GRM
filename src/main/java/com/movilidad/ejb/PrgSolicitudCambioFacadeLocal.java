/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudCambio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgSolicitudCambioFacadeLocal {

    void create(PrgSolicitudCambio prgSolicitudCambio);

    void edit(PrgSolicitudCambio prgSolicitudCambio);

    void remove(PrgSolicitudCambio prgSolicitudCambio);

    PrgSolicitudCambio find(Object id);

    PrgSolicitudCambio findBySolicitud(Integer idSolicitud);

    List<PrgSolicitudCambio> findAll();

    List<PrgSolicitudCambio> findRange(int[] range);

    int count();

}
