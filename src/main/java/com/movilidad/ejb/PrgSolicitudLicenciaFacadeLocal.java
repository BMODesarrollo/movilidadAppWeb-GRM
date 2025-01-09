/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitudLicencia;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface PrgSolicitudLicenciaFacadeLocal {

    void create(PrgSolicitudLicencia prgSolicitudLicencia);

    void edit(PrgSolicitudLicencia prgSolicitudLicencia);

    void remove(PrgSolicitudLicencia prgSolicitudLicencia);

    PrgSolicitudLicencia find(Object id);

    List<PrgSolicitudLicencia> findAll();

    List<PrgSolicitudLicencia> findRange(int[] range);

    int count();
    
    List<PrgSolicitudLicencia> findAllEstadoReg();
    
    List<PrgSolicitudLicencia> getTodoPorFecha(Date desde, Date hasta);
    
}
