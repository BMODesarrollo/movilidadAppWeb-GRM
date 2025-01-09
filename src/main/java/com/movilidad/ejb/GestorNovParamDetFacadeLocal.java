/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovParamDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovParamDetFacadeLocal {

    void create(GestorNovParamDet gestorNovParamDet);

    void edit(GestorNovParamDet gestorNovParamDet);

    void remove(GestorNovParamDet gestorNovParamDet);

    GestorNovParamDet find(Object id);

    List<GestorNovParamDet> findAll();
    
    /**
     * Método que se encarga de retornar el detalle semanal de 
     * los parámetros, filtrado por cargo y gestión novedad
     *
     * @param idEmpleadoTipoCargo
     * @param idGestorNovedad
     * @return Lista de objeto GestorNovParamDet
     */
    List<GestorNovParamDet> findAllByIdCargo(Integer idEmpleadoTipoCargo, Integer idGestorNovedad);

    List<GestorNovParamDet> findRange(int[] range);

    int count();
    
}
