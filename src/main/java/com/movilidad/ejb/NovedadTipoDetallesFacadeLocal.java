/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.util.beans.GestionNovedad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface NovedadTipoDetallesFacadeLocal {

    void create(NovedadTipoDetalles novedadTipoDetalles);

    void edit(NovedadTipoDetalles novedadTipoDetalles);

    void remove(NovedadTipoDetalles novedadTipoDetalles);

    NovedadTipoDetalles find(Object id);

    NovedadTipoDetalles findByNovedadTipoId(NovedadTipo id);

    List<NovedadTipoDetalles> findAll();

    List<NovedadTipoDetalles> findRange(int[] range);

    int count();

    List<NovedadTipoDetalles> findTipoAcc();

    List<NovedadTipoDetalles> findByTipo(int idTipo);

    List<NovedadTipoDetalles> findByTipoNovedad(Integer idTipoNov);
    
    List<NovedadTipoDetalles> findByTipoNovedadForTC(Integer idTipoNov);//carga par el TC, las novedades tipo detalle "ausentismo, ausentismo parcial e Inoperable TM"

    /**
     * Retorna las novedades detalles donde se parametrizaron las fechas
     * requeridas
     *
     * @return List objeto NovedadTipoDetalles fecha igual 1
     */
    List<NovedadTipoDetalles> findByFechaIsActive();

    List<NovedadTipoDetalles> findByTipoNovedadAndAfectaDisp(Integer idTipoNov);

    List<NovedadTipoDetalles> findByTipoNovedadAndBeMtto(Integer idTipoNov);
    
    List<NovedadTipoDetalles> obtenerDetallesAfectaGestor();
    
    List<NovedadTipoDetalles> obtenerDetallesNomina(String idsListaDetalles);
    
    List<NovedadTipoDetalles> obtenerDetallesAccesoRapido();

    List<GestionNovedad> obtenerValoresGestorNovedades(Date desde, Date hasta, int idGopUnidadFuncional);
}
