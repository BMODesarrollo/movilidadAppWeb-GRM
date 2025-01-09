/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitud;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaSolicitudFacadeLocal {

    void create(GenericaSolicitud genericaSolicitud);

    void edit(GenericaSolicitud genericaSolicitud);

    void remove(GenericaSolicitud genericaSolicitud);

    GenericaSolicitud find(Object id);

    List<GenericaSolicitud> findByDateRange(Date fechaIni,Date fechaFin,Integer area, int idGopUnidadFuncional);
    
    List<GenericaSolicitud> findAll();

    List<GenericaSolicitud> findRange(int[] range);

    int count();
    
}
