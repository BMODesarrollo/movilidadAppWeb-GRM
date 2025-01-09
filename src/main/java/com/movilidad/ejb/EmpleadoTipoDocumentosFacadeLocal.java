/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoTipoDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoTipoDocumentosFacadeLocal {

    void create(EmpleadoTipoDocumentos empleadoTipoDocumentos);

    void edit(EmpleadoTipoDocumentos empleadoTipoDocumentos);

    void remove(EmpleadoTipoDocumentos empleadoTipoDocumentos);

    EmpleadoTipoDocumentos find(Object id);

    List<EmpleadoTipoDocumentos> findAll();

    List<EmpleadoTipoDocumentos> findRange(int[] range);

    int count();

    EmpleadoTipoDocumentos findByNombreTipoDoc(String value, int id);

    List<EmpleadoTipoDocumentos> findAllActivos();

    List<EmpleadoTipoDocumentos> findByIdCargo(int idTipoCargo);
	
    List<EmpleadoTipoDocumentos> findAllEstadoReg();
    
}
