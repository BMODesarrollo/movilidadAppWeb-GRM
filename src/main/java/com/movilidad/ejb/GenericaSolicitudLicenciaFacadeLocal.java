/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudLicencia;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface GenericaSolicitudLicenciaFacadeLocal {

    void create(GenericaSolicitudLicencia genericaSolicitudLicencia);

    void edit(GenericaSolicitudLicencia genericaSolicitudLicencia);

    void remove(GenericaSolicitudLicencia genericaSolicitudLicencia);

    GenericaSolicitudLicencia find(Object id);

    List<GenericaSolicitudLicencia> findAll();

    List<GenericaSolicitudLicencia> findRange(int[] range);

    int count();

    List<GenericaSolicitudLicencia> findEstadoReg(Integer idArea);
    
    List<GenericaSolicitudLicencia> getTodoPorFecha(Date desde, Date hasta, Integer idArea);

}
