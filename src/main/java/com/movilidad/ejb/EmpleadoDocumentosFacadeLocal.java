/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoDocumentos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoDocumentosFacadeLocal {

    void create(EmpleadoDocumentos empleadoDocumentos);

    void edit(EmpleadoDocumentos empleadoDocumentos);

    void remove(EmpleadoDocumentos empleadoDocumentos);

    EmpleadoDocumentos find(Object id);

    List<EmpleadoDocumentos> findAll();

    List<EmpleadoDocumentos> findRange(int[] range);

    int count();

    EmpleadoDocumentos findByIdEmpleadoAndIdTipoDocu(int idEmpleado, int idTipoDocu);

    EmpleadoDocumentos findByIdEmpleadoDocumentoCap(int idEmpleado);

    List<EmpleadoDocumentos> findAllActivos();

    EmpleadoDocumentos findByIdEmpleadoAndIdTipoDocuAndFechas(int idEmpleado, int idTipoDocu, Date desde, Date hasta);

    int updateUltimoDocActivo(int idEmpleadoTipoDoc, int idEmpledo, int idEmpleadoDoc, boolean vigencia);

    EmpleadoDocumentos findByActivoAndVigente(Integer idEmpleado, Integer idEmpTpDoc, String fecha);

    EmpleadoDocumentos findByActivo(Integer idEmpleado, Integer idEmpTpDoc);

    List<EmpleadoDocumentos> findDocVencidos(Integer idEmpleado, Date fecha);

    List<EmpleadoDocumentos> findAllDocVencidos(Date fecha, int idGopUnidadFuncional);

    List<EmpleadoDocumentos> findAllActivosUF(int idGopUnidadFuncional);
}
