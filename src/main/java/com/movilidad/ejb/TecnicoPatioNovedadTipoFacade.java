/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.TpConteoDTO;
import com.movilidad.model.TecnicoPatio;
import com.movilidad.model.TecnicoPatioNovedad;
import com.movilidad.model.TecnicoPatioNovedadTipo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class TecnicoPatioNovedadTipoFacade extends AbstractFacade<TecnicoPatioNovedadTipo> implements TecnicoPatioNovedadTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public TecnicoPatioNovedadTipoFacade() {
        super(TecnicoPatioNovedadTipo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    @Override
    public List<TecnicoPatioNovedadTipo> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM rgmo.tp_novedad_tipo where estado_reg = 1;", TecnicoPatioNovedadTipo.class);
            //q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
//
//    @Override
//    public List<TpConteoDTO> findCounterTp(int uF, Date d) {
//        try {
//            Query q = em.createNativeQuery("SELECT   \n"
//                    + "1 AS id, total, confirmado , 0 as depot_type \n"
//                    + "FROM  \n"
//                    + "( \n"
//                    + "SELECT   \n"
//                    + "COUNT(*) AS total  \n"
//                    + "FROM  \n"
//                    + "prg_tc tc  \n"
//                    + "INNER JOIN (SELECT   \n"
//                    + "*  \n"
//                    + "FROM  \n"
//                    + "prg_stop_point sp  \n"
//                    + "WHERE  \n"
//                    + "sp.is_depot = 1) AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
//                    + "WHERE  \n"
//                    + "tc.fecha = DATE( ?2 ) \n"
//                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
//                    + "AND tc.servbus IS NOT NULL  \n"
//                    + "AND tc.id_vehiculo IS NOT NULL) AS tc,  \n"
//                    + "(SELECT   \n"
//                    + "COUNT(*) AS confirmado  \n"
//                    + "FROM  \n"
//                    + "prg_tc tc  \n"
//                    + "INNER JOIN (SELECT   \n"
//                    + "*  \n"
//                    + "FROM  \n"
//                    + "prg_stop_point sp  \n"
//                    + "WHERE  \n"
//                    + "sp.is_depot = 1 \n"
//                    + ") AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
//                    + "WHERE  \n"
//                    + "tc.fecha = DATE( ?2 )  \n"
//                    + "AND tc.id_prg_tc IN (SELECT   \n"
//                    + "made.id_prg_tc  \n"
//                    + "FROM  \n"
//                    + "my_app_confirm_depot_entry made WHERE tipo_tabla <> 3)  \n"
//                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
//                    + "AND tc.servbus IS NOT NULL  \n"
//                    + "AND tc.id_vehiculo IS NOT NULL \n"
//                    + ") AS tcc \n"
//                    + "UNION ALL \n"
//                    + "-- CONTADOR DE SALIDAS CONFIRMADAS \n"
//                    + "SELECT   \n"
//                    + "2 AS id, total, confirmado , 1 as depot_type \n"
//                    + "FROM  \n"
//                    + "(SELECT   \n"
//                    + "COUNT(*) AS total  \n"
//                    + "FROM  \n"
//                    + "prg_tc tc  \n"
//                    + "INNER JOIN (SELECT   \n"
//                    + "*  \n"
//                    + "FROM  \n"
//                    + "prg_stop_point sp  \n"
//                    + "WHERE  \n"
//                    + "sp.is_depot = 1) AS sp ON tc.from_stop = sp.id_prg_stoppoint  \n"
//                    + "WHERE  \n"
//                    + "tc.fecha = DATE( ?2 ) \n"
//                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
//                    + "AND tc.servbus IS NOT NULL  \n"
//                    + "AND tc.id_vehiculo IS NOT NULL) AS tc,  \n"
//                    + "(SELECT   \n"
//                    + "COUNT(*) AS confirmado  \n"
//                    + "FROM  \n"
//                    + "prg_tc tc  \n"
//                    + "INNER JOIN (SELECT   \n"
//                    + "*  \n"
//                    + "FROM  \n"
//                    + "prg_stop_point sp  \n"
//                    + "WHERE  \n"
//                    + "sp.is_depot = 1) AS sp ON tc.from_stop = sp.id_prg_stoppoint  \n"
//                    + "WHERE  \n"
//                    + "tc.fecha = DATE( ?2 )  \n"
//                    + "AND tc.id_prg_tc IN (SELECT   \n"
//                    + "made.id_prg_tc  \n"
//                    + "FROM  \n"
//                    + "my_app_confirm_depot_exit made WHERE tipo_tabla <> 3)  \n"
//                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
//                    + "AND tc.servbus IS NOT NULL  \n"
//                    + "AND tc.id_vehiculo IS NOT NULL) AS tcc \n"
//                    + "UNION ALL \n"
//                    //+ "-- CONSULTA ENTRADAS PARTIDAS \n"
//                    + "SELECT   \n"
//                    + "3 AS id, total, confirmado , 2 as depot_type \n"
//                    + "FROM  \n"
//                    + "( \n"
//                    + "SELECT   \n"
//                    + "COUNT(*) AS total  \n"
//                    + "FROM  \n"
//                    + "(SELECT  \n"
//                    + "p.servbus AS servbus, COUNT(p.id_prg_tc) AS num_entry_depot \n"
//                    + "FROM \n"
//                    + "prg_tc p \n"
//                    + "INNER JOIN \n"
//                    + "(SELECT  \n"
//                    + "psp.* \n"
//                    + "FROM \n"
//                    + "prg_stop_point psp \n"
//                    + "WHERE \n"
//                    + "psp.is_depot IS NULL OR psp.is_depot = 1) psppx ON  \n"
//                    + "psppx.id_prg_stoppoint = p.to_stop \n"
//                    + "WHERE \n"
//                    + "p.fecha = DATE( ?2 ) \n"
//                    + "AND p.servbus IS NOT NULL \n"
//                    + "AND p.estado_operacion NOT IN (3 , 4, 6, 7) \n"
//                    + "AND p.id_gop_unidad_funcional = ?1 \n"
//                    + "GROUP BY servbus) ST1 WHERE	 ST1.num_entry_depot=2 ) AS tc,  \n"
//                    + "(SELECT   \n"
//                    + "COUNT(*) AS confirmado  \n"
//                    + "FROM  \n"
//                    + "prg_tc tc  \n"
//                    + "INNER JOIN (SELECT   \n"
//                    + "*  \n"
//                    + "FROM  \n"
//                    + "prg_stop_point sp  \n"
//                    + "WHERE  \n"
//                    + "sp.is_depot = 1 \n"
//                    + ") AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
//                    + "WHERE  \n"
//                    + "tc.fecha = DATE( ?2 )  \n"
//                    + "AND tc.id_prg_tc IN (SELECT   \n"
//                    + "made.id_prg_tc  \n"
//                    + "FROM  \n"
//                    + "my_app_confirm_depot_entry made WHERE tipo_tabla = 3)  \n"
//                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
//                    + "AND tc.servbus IS NOT NULL  \n"
//                    + "AND tc.id_vehiculo IS NOT NULL \n"
//                    + ") AS tcc \n"
////                    + "UNION ALL \n"
////                    + "-- Consulta parrilla mantenimiento \n"
////                    + "SELECT 4 AS id, 0 as total,COUNT(codigo) AS confirmado, 3 as depot_type FROM rgmo.parrilla \n"
////                    + "WHERE is_mtto_patio=1 \n"
////                    + "AND uf= ?1 \n"
////                    + "UNION ALL \n"
////                    + "-- Consulta parrilla en patio \n"
////                    + "SELECT 5 AS id, 0 as total,COUNT(codigo) AS confirmado, 4 as depot_type FROM rgmo.parrilla \n"
////                    + "WHERE ubicacion=0 \n"
////                    + "AND uf= ?1"
//                    , "TpConteoDTOMapping");
//            q.setParameter(1, uF);
//            q.setParameter(2, d);
//            //q.setParameter(1, idArea);
//            return q.getResultList();
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//    }
}
