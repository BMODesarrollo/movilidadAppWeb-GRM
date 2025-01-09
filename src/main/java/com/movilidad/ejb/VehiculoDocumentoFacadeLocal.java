/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDocumentos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoDocumentoFacadeLocal {

    void create(VehiculoDocumentos vehiculoDocumentos);

    void edit(VehiculoDocumentos vehiculoDocumentos);

    void remove(VehiculoDocumentos vehiculoDocumentos);

    VehiculoDocumentos find(Object id);

    VehiculoDocumentos verificarRegistro(Integer idVehiculo, Integer idTipoDocumento, Date fechaDesde, Date fechaHasta, Integer idDocumento);

    VehiculoDocumentos verificarRegistroDesde(Integer idVehiculo, Integer idTipoDocumento, Date fechaDesde, Integer idDocumento);

    VehiculoDocumentos findByVehiculoAndTipoDocumento(Integer idVehiculo, Integer idTipoDocumento, Integer idDocumento, Date fechaDesde, Date fechaHasta);

    VehiculoDocumentos obtenerDocumentoAnterior(Integer idVehiculo, Integer idTipoDocumento);

    List<VehiculoDocumentos> findAll();

    List<VehiculoDocumentos> findAllByActivos(int idGopUnidadFuncional);

    List<VehiculoDocumentos> findRange(int[] range);

    int count();

    VehiculoDocumentos findByActivoAndVigente(Integer idVehiculo, Integer idTipoDocumento, String fecha);

    VehiculoDocumentos findByActivo(Integer idVehiculo, Integer idTipoDocumento);

    List<VehiculoDocumentos> findDocVencidos(Integer idVehiculo, Date fecah);

    List<VehiculoDocumentos> findAllDocVencidos(Date fecha, int idGopUnidadFunc);
}
