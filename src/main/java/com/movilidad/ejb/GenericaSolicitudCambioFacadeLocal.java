/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudCambio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaSolicitudCambioFacadeLocal {

    void create(GenericaSolicitudCambio genericaSolicitudCambio);

    void edit(GenericaSolicitudCambio genericaSolicitudCambio);

    void remove(GenericaSolicitudCambio genericaSolicitudCambio);

    GenericaSolicitudCambio find(Object id);
    
    GenericaSolicitudCambio findBySolicitud(Integer idSolicitud);

    List<GenericaSolicitudCambio> findAll();

    List<GenericaSolicitudCambio> findRange(int[] range);

    int count();
    
}
