/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AutorizacionNominaKactusFacadeLocal {

    void create(NominaAutorizacion autorizacionNominaKactus);

    void edit(NominaAutorizacion autorizacionNominaKactus);

    void remove(NominaAutorizacion autorizacionNominaKactus);

    NominaAutorizacion find(Object id);

    List<NominaAutorizacion> findAll();
    
    List<NominaAutorizacion> findAllByRangoFechasAndUF(Date desde, Date hasta, int idGopUnidadFuncional);

    List<NominaAutorizacion> findRange(int[] range);

    int count();
    
}
