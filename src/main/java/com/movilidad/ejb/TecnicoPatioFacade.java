/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.TpConteoDTO;
import com.movilidad.model.TecnicoPatio;
import com.movilidad.utils.Util;
import static com.movilidad.utils.Util.DATE_TO_TIME_FORMAT_HH_MM_SS;
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
public class TecnicoPatioFacade extends AbstractFacade<TecnicoPatio> implements TecnicoPatioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private Util util;

    public TecnicoPatioFacade() {
        super(TecnicoPatio.class);
    }

    @Override
    public List<TecnicoPatio> findAll(int uF, Date d) {
        try {
            Query q = em.createNativeQuery("select TG.*,PR.name as ruta, ST1.num_entry_depot as type_table, gv.bateria  from ( \n"
                    + "SELECT DISTINCT tc.id_prg_tc, tc.id_vehiculo, tc.id_empleado, em.codigo_tm,CONCAT(em.nombres,' ', \n"
                    + "em.apellidos) as nombre, tc.servbus, \n"
                    + "veh.codigo as bus, tc.time_origin, tc.time_destiny, tc.to_stop, tc.from_stop, tc.tabla, \n"
                    + "tc.id_task_type, 1 as type_entry, 0 as type_exit,\n"
                    + "(select id_ruta from prg_tc where time_destiny=tc.time_origin and id_ruta is not null and id_vehiculo=tc.id_vehiculo \n"
                    + "and fecha=tc.fecha LIMIT 1) as idruta,\n"
                    + "IFNULL(nov.id_novedad_tipo, 0) as novedad \n"
                    + "FROM prg_tc tc \n"
                    + "LEFT JOIN empleado em on em.id_empleado=tc.id_empleado \n"
                    + "LEFT JOIN vehiculo veh on veh.id_vehiculo=tc.id_vehiculo \n"
                    + "LEFT JOIN tp_novedad nov on nov.id_prg_tc=tc.id_prg_tc\n"
                    + "INNER JOIN (SELECT id_prg_stoppoint FROM prg_stop_point sp WHERE sp.is_depot = 1) AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
                    + "WHERE tc.fecha =  DATE(?2) AND tc.id_prg_tc NOT IN (SELECT  made.id_prg_tc FROM my_app_confirm_depot_entry made)  \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
                    + "AND tc.estado_operacion NOT IN (5 , 8, 99) AND tc.servbus IS NOT NULL AND tc.id_vehiculo IS NOT NULL\n"
                    + "\n"
                    + "UNION ALL \n"
                    + "\n"
                    + "SELECT DISTINCT tc.id_prg_tc, tc.id_vehiculo, tc.id_empleado, em.codigo_tm,CONCAT(em.nombres,' ',em.apellidos) as nombre, tc.servbus, \n"
                    + "veh.codigo as bus, tc.time_origin, tc.time_destiny, tc.to_stop, tc.from_stop, tc.tabla, \n"
                    + "tc.id_task_type,0 as type_entry,1 as type_exit,\n"
                    + "(select id_ruta from prg_tc where time_origin=tc.time_destiny and id_ruta is not null and id_vehiculo=tc.id_vehiculo \n"
                    + "and fecha=tc.fecha LIMIT 1) as idruta,\n"
                    + "IFNULL(nov.id_novedad_tipo, 0) as novedad \n"
                    + "FROM prg_tc tc  \n"
                    + "LEFT JOIN empleado em on em.id_empleado=tc.id_empleado \n"
                    + "LEFT JOIN vehiculo veh on veh.id_vehiculo=tc.id_vehiculo \n"
                    + "LEFT JOIN tp_novedad nov on nov.id_prg_tc=tc.id_prg_tc\n"
                    + "INNER JOIN (SELECT id_prg_stoppoint FROM prg_stop_point sp WHERE sp.is_depot = 1) AS sp ON tc.from_stop = sp.id_prg_stoppoint \n"
                    + "WHERE tc.fecha =  DATE(?2) AND tc.id_prg_tc NOT IN (SELECT made.id_prg_tc FROM my_app_confirm_depot_exit made) \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 AND tc.servbus IS NOT NULL \n"
                    + "AND tc.estado_operacion NOT IN (5 , 8, 99) AND tc.id_vehiculo IS NOT NULL\n"
                    + "\n"
                    + ") TG  \n"
                    + "LEFT JOIN prg_route PR ON TG.idruta=PR.id_prg_route \n"
                    + "LEFT JOIN gestion_vehiculo gv on gv.id_vehiculo = TG.id_vehiculo \n"
                    + "INNER JOIN ( \n"
                    + "SELECT  \n"
                    + "p.servbus AS servbus,  \n"
                    + "CASE WHEN COUNT(p.id_prg_tc)>1 THEN 'Partida' ELSE 'Larga' end AS num_entry_depot \n"
                    + "FROM \n"
                    + "prg_tc p \n"
                    + "INNER JOIN \n"
                    + "(SELECT  \n"
                    + "psp.id_prg_stoppoint \n"
                    + "FROM \n"
                    + "prg_stop_point psp \n"
                    + "WHERE \n"
                    + "psp.is_depot IS NULL OR psp.is_depot = 1) psppx ON psppx.id_prg_stoppoint = p.to_stop \n"
                    + "WHERE \n"
                    + "p.fecha = DATE(?2) \n"
                    + "AND p.servbus IS NOT NULL \n"
                    + "AND p.estado_operacion NOT IN (3 , 4, 6, 7) \n"
                    + "AND p.id_gop_unidad_funcional = 1 \n"
                    + "GROUP BY servbus \n"
                    + ") ST1 on TG.servbus= ST1.servbus \n"
                    + "order by time_origin asc;", TecnicoPatio.class);
            q.setParameter(1, uF);
            q.setParameter(2, d);
            //q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TpConteoDTO> findCounterTp(int uF, Date d) {
        try {
            Query q = em.createNativeQuery("SELECT   \n"
                    + "1 AS id, total, confirmado , 0 as depot_type \n"
                    + "FROM  \n"
                    + "( \n"
                    + "SELECT   \n"
                    + "COUNT(*) AS total  \n"
                    + "FROM  \n"
                    + "prg_tc tc  \n"
                    + "INNER JOIN (SELECT   \n"
                    + "*  \n"
                    + "FROM  \n"
                    + "prg_stop_point sp  \n"
                    + "WHERE  \n"
                    + "sp.is_depot = 1) AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
                    + "WHERE  \n"
                    + "tc.fecha = DATE( ?2 ) \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
                    + "AND tc.servbus IS NOT NULL  \n"
                    + "AND tc.id_vehiculo IS NOT NULL) AS tc,  \n"
                    + "(SELECT   \n"
                    + "COUNT(*) AS confirmado  \n"
                    + "FROM  \n"
                    + "prg_tc tc  \n"
                    + "INNER JOIN (SELECT   \n"
                    + "*  \n"
                    + "FROM  \n"
                    + "prg_stop_point sp  \n"
                    + "WHERE  \n"
                    + "sp.is_depot = 1 \n"
                    + ") AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
                    + "WHERE  \n"
                    + "tc.fecha = DATE( ?2 )  \n"
                    + "AND tc.id_prg_tc IN (SELECT   \n"
                    + "made.id_prg_tc  \n"
                    + "FROM  \n"
                    + "my_app_confirm_depot_entry made WHERE tipo_tabla <> 3)  \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
                    + "AND tc.servbus IS NOT NULL  \n"
                    + "AND tc.id_vehiculo IS NOT NULL \n"
                    + ") AS tcc \n"
                    + "UNION ALL \n"
                    + "-- CONTADOR DE SALIDAS CONFIRMADAS \n"
                    + "SELECT   \n"
                    + "2 AS id, total, confirmado , 1 as depot_type \n"
                    + "FROM  \n"
                    + "(SELECT   \n"
                    + "COUNT(*) AS total  \n"
                    + "FROM  \n"
                    + "prg_tc tc  \n"
                    + "INNER JOIN (SELECT   \n"
                    + "*  \n"
                    + "FROM  \n"
                    + "prg_stop_point sp  \n"
                    + "WHERE  \n"
                    + "sp.is_depot = 1) AS sp ON tc.from_stop = sp.id_prg_stoppoint  \n"
                    + "WHERE  \n"
                    + "tc.fecha = DATE( ?2 ) \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
                    + "AND tc.servbus IS NOT NULL  \n"
                    + "AND tc.id_vehiculo IS NOT NULL) AS tc,  \n"
                    + "(SELECT   \n"
                    + "COUNT(*) AS confirmado  \n"
                    + "FROM  \n"
                    + "prg_tc tc  \n"
                    + "INNER JOIN (SELECT   \n"
                    + "*  \n"
                    + "FROM  \n"
                    + "prg_stop_point sp  \n"
                    + "WHERE  \n"
                    + "sp.is_depot = 1) AS sp ON tc.from_stop = sp.id_prg_stoppoint  \n"
                    + "WHERE  \n"
                    + "tc.fecha = DATE( ?2 )  \n"
                    + "AND tc.id_prg_tc IN (SELECT   \n"
                    + "made.id_prg_tc  \n"
                    + "FROM  \n"
                    + "my_app_confirm_depot_exit made WHERE tipo_tabla <> 3)  \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
                    + "AND tc.servbus IS NOT NULL  \n"
                    + "AND tc.id_vehiculo IS NOT NULL) AS tcc \n"
                    + "UNION ALL \n"
                    //+ "-- CONSULTA ENTRADAS PARTIDAS \n"
                    + "SELECT   \n"
                    + "3 AS id, total, confirmado , 2 as depot_type \n"
                    + "FROM  \n"
                    + "( \n"
                    + "SELECT   \n"
                    + "COUNT(*) AS total  \n"
                    + "FROM  \n"
                    + "(SELECT  \n"
                    + "p.servbus AS servbus, COUNT(p.id_prg_tc) AS num_entry_depot \n"
                    + "FROM \n"
                    + "prg_tc p \n"
                    + "INNER JOIN \n"
                    + "(SELECT  \n"
                    + "psp.* \n"
                    + "FROM \n"
                    + "prg_stop_point psp \n"
                    + "WHERE \n"
                    + "psp.is_depot IS NULL OR psp.is_depot = 1) psppx ON  \n"
                    + "psppx.id_prg_stoppoint = p.to_stop \n"
                    + "WHERE \n"
                    + "p.fecha = DATE( ?2 ) \n"
                    + "AND p.servbus IS NOT NULL \n"
                    + "AND p.estado_operacion NOT IN (3 , 4, 6, 7) \n"
                    + "AND p.id_gop_unidad_funcional = ?1 \n"
                    + "GROUP BY servbus) ST1 WHERE	 ST1.num_entry_depot=2 ) AS tc,  \n"
                    + "(SELECT   \n"
                    + "COUNT(*) AS confirmado  \n"
                    + "FROM  \n"
                    + "prg_tc tc  \n"
                    + "INNER JOIN (SELECT   \n"
                    + "*  \n"
                    + "FROM  \n"
                    + "prg_stop_point sp  \n"
                    + "WHERE  \n"
                    + "sp.is_depot = 1 \n"
                    + ") AS sp ON tc.to_stop = sp.id_prg_stoppoint  \n"
                    + "WHERE  \n"
                    + "tc.fecha = DATE( ?2 )  \n"
                    + "AND tc.id_prg_tc IN (SELECT   \n"
                    + "made.id_prg_tc  \n"
                    + "FROM  \n"
                    + "my_app_confirm_depot_entry made WHERE tipo_tabla = 3)  \n"
                    + "AND tc.id_gop_unidad_funcional = ?1 \n"
                    + "AND tc.servbus IS NOT NULL  \n"
                    + "AND tc.id_vehiculo IS NOT NULL \n"
                    + ") AS tcc \n" //                    + "UNION ALL \n"
                    //                    + "-- Consulta parrilla mantenimiento \n"
                    //                    + "SELECT 4 AS id, 0 as total,COUNT(codigo) AS confirmado, 3 as depot_type FROM rgmo.parrilla \n"
                    //                    + "WHERE is_mtto_patio=1 \n"
                    //                    + "AND uf= ?1 \n"
                    //                    + "UNION ALL \n"
                    //                    + "-- Consulta parrilla en patio \n"
                    //                    + "SELECT 5 AS id, 0 as total,COUNT(codigo) AS confirmado, 4 as depot_type FROM rgmo.parrilla \n"
                    //                    + "WHERE ubicacion=0 \n"
                    //                    + "AND uf= ?1"
                    ,
                     "TpConteoDTOMapping");
            q.setParameter(1, uF);
            q.setParameter(2, d);
            //q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TpConteoDTO> findCounterTpTime(int uF, String horaInicio, String horaFinal) {

        Date d = new Date();
        try {
            Query q = em.createNativeQuery("SELECT 1 AS id, total, confirmado, 0 AS depot_type,\n"
                    + "CASE\n"
                    + "    WHEN (confirmado * 100) / total >= 100 THEN 100\n"
                    + "    ELSE IFNULL((confirmado * 100) / total, 0)\n"
                    + "END AS porcentaje\n"
                    + "FROM (\n"
                    + "    SELECT COUNT(*) AS total\n"
                    + "    FROM prg_tc tc\n"
                    + "    INNER JOIN (\n"
                    + "        SELECT *\n"
                    + "        FROM prg_stop_point sp\n"
                    + "        WHERE sp.is_depot = 1\n"
                    + "    ) AS sp ON tc.to_stop = sp.id_prg_stoppoint\n"
                    + "    WHERE tc.fecha = DATE(?1)\n"
                    + "    AND tc.time_origin BETWEEN ?2 AND ?3\n"
                    + "    AND tc.id_gop_unidad_funcional = ?4\n"
                    + "    AND tc.servbus IS NOT NULL\n"
                    + "    AND tc.id_vehiculo IS NOT NULL\n"
                    + ") AS tc,\n"
                    + "(\n"
                    + "    SELECT COUNT(*) AS confirmado\n"
                    + "    FROM prg_tc tc\n"
                    + "    INNER JOIN (\n"
                    + "        SELECT *\n"
                    + "        FROM prg_stop_point sp\n"
                    + "        WHERE sp.is_depot = 1\n"
                    + "    ) AS sp ON tc.to_stop = sp.id_prg_stoppoint\n"
                    + "    WHERE tc.fecha = DATE(?1)\n"
                    + "    AND tc.time_origin BETWEEN ?2 AND ?3\n"
                    + "    AND tc.id_prg_tc IN (\n"
                    + "        SELECT made.id_prg_tc\n"
                    + "        FROM my_app_confirm_depot_entry made\n"
                    + "        WHERE tipo_tabla <> 3\n"
                    + "    )\n"
                    + "    AND tc.id_gop_unidad_funcional = ?4\n"
                    + "    AND tc.servbus IS NOT NULL\n"
                    + "    AND tc.id_vehiculo IS NOT NULL\n"
                    + ") AS tcc\n"
                    + "UNION ALL\n"
                    + "SELECT 2 AS id, total, confirmado, 1 AS depot_type,\n"
                    + "CASE\n"
                    + "    WHEN (confirmado * 100) / total >= 100 THEN 100\n"
                    + "    ELSE IFNULL((confirmado * 100) / total, 0)\n"
                    + "END AS porcentaje\n"
                    + "FROM (\n"
                    + "    SELECT COUNT(*) AS total\n"
                    + "    FROM prg_tc tc\n"
                    + "    INNER JOIN (\n"
                    + "        SELECT *\n"
                    + "        FROM prg_stop_point sp\n"
                    + "        WHERE sp.is_depot = 1\n"
                    + "    ) AS sp ON tc.from_stop = sp.id_prg_stoppoint\n"
                    + "    WHERE tc.fecha = DATE(?1)\n"
                    + "    AND tc.time_origin BETWEEN ?2 AND ?3\n"
                    + "    AND tc.id_gop_unidad_funcional = ?4\n"
                    + "    AND tc.servbus IS NOT NULL\n"
                    + "    AND tc.id_vehiculo IS NOT NULL\n"
                    + ") AS tc,\n"
                    + "(\n"
                    + "    SELECT COUNT(*) AS confirmado\n"
                    + "    FROM prg_tc tc\n"
                    + "    INNER JOIN (\n"
                    + "        SELECT *\n"
                    + "        FROM prg_stop_point sp\n"
                    + "        WHERE sp.is_depot = 1\n"
                    + "    ) AS sp ON tc.from_stop = sp.id_prg_stoppoint\n"
                    + "    WHERE tc.fecha = DATE(?1)\n"
                    + "    AND tc.time_origin BETWEEN ?2 AND ?3\n"
                    + "    AND tc.id_prg_tc IN (\n"
                    + "        SELECT made.id_prg_tc\n"
                    + "        FROM my_app_confirm_depot_exit made\n"
                    + "        WHERE tipo_tabla <> 3\n"
                    + "    )\n"
                    + "    AND tc.id_gop_unidad_funcional = ?4\n"
                    + "    AND tc.servbus IS NOT NULL\n"
                    + "    AND tc.id_vehiculo IS NOT NULL\n"
                    + ") AS tcc\n"
                    + "UNION ALL\n"
                    + "SELECT 3 AS id, total, confirmado, 2 AS depot_type,\n"
                    + "CASE\n"
                    + "    WHEN (confirmado * 100) / total >= 100 THEN 100\n"
                    + "    ELSE IFNULL((confirmado * 100) / total, 0)\n"
                    + "END AS porcentaje\n"
                    + "FROM (\n"
                    + "    SELECT COUNT(*) AS total\n"
                    + "    FROM (\n"
                    + "        SELECT p.servbus AS servbus, COUNT(p.id_prg_tc) AS num_entry_depot\n"
                    + "        FROM prg_tc p\n"
                    + "        INNER JOIN (\n"
                    + "            SELECT psp.*\n"
                    + "            FROM prg_stop_point psp\n"
                    + "            WHERE psp.is_depot IS NULL OR psp.is_depot = 1\n"
                    + "        ) psppx ON psppx.id_prg_stoppoint = p.to_stop\n"
                    + "        WHERE p.fecha = DATE(?1)\n"
                    + "        AND p.time_origin BETWEEN ?2 AND '28:59:59'\n"
                    + "        AND p.servbus IS NOT NULL\n"
                    + "        AND p.estado_operacion NOT IN (3, 4, 6, 7)\n"
                    + "        AND p.id_gop_unidad_funcional = ?4\n"
                    + "        GROUP BY servbus\n"
                    + "    ) ST1\n"
                    + "    WHERE ST1.num_entry_depot = 2\n"
                    + ") AS tc,\n"
                    + "(\n"
                    + "    SELECT COUNT(*) AS confirmado\n"
                    + "    FROM prg_tc tc\n"
                    + "    INNER JOIN (\n"
                    + "        SELECT *\n"
                    + "        FROM prg_stop_point sp\n"
                    + "        WHERE sp.is_depot = 1\n"
                    + "    ) AS sp ON tc.to_stop = sp.id_prg_stoppoint\n"
                    + "    WHERE tc.fecha = DATE(?1)\n"
                    + "    AND tc.time_origin BETWEEN ?2 AND ?3\n"
                    + "    AND tc.id_prg_tc IN (\n"
                    + "        SELECT made.id_prg_tc\n"
                    + "        FROM my_app_confirm_depot_entry made\n"
                    + "        WHERE tipo_tabla = 3\n"
                    + "    )\n"
                    + "    AND tc.id_gop_unidad_funcional = ?4\n"
                    + "    AND tc.servbus IS NOT NULL\n"
                    + "    AND tc.id_vehiculo IS NOT NULL\n"
                    + ") AS tcc;",
                    "TpConteoDTOMapping");
            q.setParameter(1, d);
            q.setParameter(2, horaInicio);
            q.setParameter(3, horaFinal);
            q.setParameter(4, uF);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
