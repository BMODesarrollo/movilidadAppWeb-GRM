/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvVehiculosAtencion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AtvVehiculosAtencionFacadeLocal {

    void create(AtvVehiculosAtencion atvVehiculosAtencion);

    void edit(AtvVehiculosAtencion atvVehiculosAtencion);

    void remove(AtvVehiculosAtencion atvVehiculosAtencion);

    AtvVehiculosAtencion find(Object id);

    List<AtvVehiculosAtencion> findAll();

    List<AtvVehiculosAtencion> findRange(int[] range);

    int count();

    List<AtvVehiculosAtencion> findByEstadoReg();

    AtvVehiculosAtencion findByplacaAndIdPrestador(String placa, int idAtvPRestador, int idAtvVehiculosAtencion);
}
