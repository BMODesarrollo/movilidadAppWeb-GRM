/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacionDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NominaAutorizacionDetFacadeLocal {

    void create(NominaAutorizacionDet nominaAutorizacionDet);

    void edit(NominaAutorizacionDet nominaAutorizacionDet);

    void remove(NominaAutorizacionDet nominaAutorizacionDet);

    NominaAutorizacionDet find(Object id);

    List<NominaAutorizacionDet> findAll();

    List<NominaAutorizacionDet> findRange(int[] range);

    int count();

    List<NominaAutorizacionDet> findByIdNominaAutorizacion(Integer idNominaAutorizacion);

    List<NominaAutorizacionDet> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError);

    Long obtenerCantidadErrores(Integer idNominaAutorizacion);
}
