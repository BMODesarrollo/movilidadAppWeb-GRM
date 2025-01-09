/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovedad;
import com.movilidad.model.Novedad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovedadFacadeLocal {

    void create(GestorNovedad gestorNovedad);

    void edit(GestorNovedad gestorNovedad);

    void remove(GestorNovedad gestorNovedad);

    GestorNovedad find(Object id);

    List<GestorNovedad> findAll();

    GestorNovedad findAllByRangoFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional);

    List<GestorNovedad> findRange(int[] range);

    List<Novedad> obtenerNovedadesFreeway(Date fechaLiquidado, Date desde, Date hasta, int idGopUnidadFuncional);

    int count();

}
