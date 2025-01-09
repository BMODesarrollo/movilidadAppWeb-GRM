/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoDocumentoFacadeLocal {

    void create(VehiculoTipoDocumentos vehiculoTipoDocumento);

    void edit(VehiculoTipoDocumentos vehiculoTipoDocumento);

    void remove(VehiculoTipoDocumentos vehiculoTipoDocumento);

    VehiculoTipoDocumentos find(Object id);

    List<VehiculoTipoDocumentos> findAll();

    List<VehiculoTipoDocumentos> findRange(int[] range);

    int count();
    
    List<VehiculoTipoDocumentos> findAllEstadoReg();
    
}
