/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvCostoServicio;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AtvCostoServicioFacadeLocal {

    void create(AtvCostoServicio atvCostoServicio);

    void edit(AtvCostoServicio atvCostoServicio);

    void remove(AtvCostoServicio atvCostoServicio);

    AtvCostoServicio find(Object id);

    List<AtvCostoServicio> findAll();

    List<AtvCostoServicio> findRange(int[] range);

    int count();

    List<AtvCostoServicio> findByEstadoReg();

    AtvCostoServicio findByCostoAndIdPrestadorAndVehiculoTipoAndRangoFecha(int idAtvPrestador, int idVehiculoTipo, Date desde, Date hasta, int idAtvCostoServicio);

    /**
     * Permite consulltar los registros activos que se encentren en un rango de
     * fechas
     *
     * @param desde
     * @param hasta
     * @return
     */
    List<AtvCostoServicio> findAllByFechas(Date desde, Date hasta);

}
