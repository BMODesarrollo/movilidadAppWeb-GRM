/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovDetSemana;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovDetSemanaFacadeLocal {

    void create(GestorNovDetSemana gestorNovDetSemana);

    void edit(GestorNovDetSemana gestorNovDetSemana);

    void remove(GestorNovDetSemana gestorNovDetSemana);

    GestorNovDetSemana find(Object id);

    List<GestorNovDetSemana> findAll();

    /**
     * Método que se encarga de retornar el detalle semanal de las novedades que
     * afectan gestor, filtrado por cargo y unidad funcional
     *
     * @param idGopUnidadFuncional
     * @param idEmpleadoTipoCargo
     * @param idGestorNovedad
     * @return Lista de objeto GestorNovDetSemana
     */
    List<GestorNovDetSemana> findAllByUfAndIdCargo(int idGopUnidadFuncional, Integer idEmpleadoTipoCargo, Integer idGestorNovedad);

    List<GestorNovDetSemana> findRange(int[] range);

    int count();

}
