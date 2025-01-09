/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudPermiso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaSolicitudPermisoFacadeLocal {

    void create(GenericaSolicitudPermiso genericaSolicitudPermiso);

    void edit(GenericaSolicitudPermiso genericaSolicitudPermiso);

    void remove(GenericaSolicitudPermiso genericaSolicitudPermiso);

    GenericaSolicitudPermiso find(Object id);
    
    GenericaSolicitudPermiso findBySolicitud(Integer idSolicitud);

    List<GenericaSolicitudPermiso> findAll();

    List<GenericaSolicitudPermiso> findRange(int[] range);

    int count();
    
}
