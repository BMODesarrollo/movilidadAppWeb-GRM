/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovDetSemana;
import com.movilidad.model.GestorNovReqSemana;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovReqSemanaFacadeLocal {

    void create(GestorNovReqSemana gestorNovReqSemana);

    void edit(GestorNovReqSemana gestorNovReqSemana);

    void remove(GestorNovReqSemana gestorNovReqSemana);

    GestorNovReqSemana find(Object id);

    List<GestorNovReqSemana> findAll();
    
    /**
     * Método que se encarga de retornar el detalle semanal de 
     * los requerimientos, filtrado por cargo y unidad funcional
     *
     * @param idGopUnidadFuncional
     * @param idEmpleadoTipoCargo
     * @param idGestorNovedad
     * @return Lista de objeto GestorNovReqSemana
     */
    List<GestorNovReqSemana> findAllByIdCargo(Integer idEmpleadoTipoCargo, Integer idGestorNovedad);

    List<GestorNovReqSemana> findRange(int[] range);

    int count();
    
}
