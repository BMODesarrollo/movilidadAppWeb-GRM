/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgVehicleStatus;
import com.movilidad.utils.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
public class PrgVehicleStatusFacade extends AbstractFacade<PrgVehicleStatus> implements PrgVehicleStatusFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgVehicleStatusFacade() {
        super(PrgVehicleStatus.class);
    }

    @Override
    public List<PrgVehicleStatus> findByDate(Date fechaIni, Date fechaFin) {
        try {
            Query q = em.createNativeQuery("select prgvs.* from prg_vehicle_status prgvs "
                    + "where (fecha between ?1 and ?2) "
                    + "order by fecha, servbus, time_origin desc", PrgVehicleStatus.class);
            q.setParameter(1, fechaIni);
            q.setParameter(2, fechaFin);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long countByFechasByIdGopUnidadFuncional(Date fromDate, Date toDate, Integer idGopUnidadFunc) {
        try {
            Query q = em.createQuery("SELECT \n"
                    + "    COUNT(p.id_prg_vehicle_status)\n"
                    + "FROM\n"
                    + "    prg_vehicle_status p\n"
                    + "WHERE\n"
                    + "    p.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND p.id_gop_unidad_funcional = ?3\n"
                    + "ORDER BY p.time_origin");
            q.setParameter(1, Util.dateFormat(fromDate));
            q.setParameter(2, Util.dateFormat(toDate));
            q.setParameter(3, idGopUnidadFunc);
            return (long) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<PrgVehicleStatus> findPrgNextDay(Date fecha) {
        String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    id_prg_vehicle_status as id_prg_vehicle_status,\n"
                    + "    fecha as fecha,\n"
                    + "    servbus as servbus,\n"
                    + "    MIN(time_origin) as time_origin,\n"
                    + "    MAX(time_destiny) as time_destiny,\n"
                    + "    SUM(production_distance) as production_distance\n"
                    + "FROM\n"
                    + "    `prg_vehicle_status`\n"
                    + "WHERE\n"
                    + "    fecha = ?1\n"
                    + "GROUP BY 2 , 3;", PrgVehicleStatus.class);
            q.setParameter(1, c_fechaIni);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgVehicleStatus> findPrgNextDayPorDistanciaPasoII(Date fecha, int idPatio) {
        String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        String parte = " ";
        if (idPatio != 0) {
            parte = "        AND pvs.id_from_depot = ?2\n";
        }
        String sql = "SELECT \n"
                + "    pvs.id_prg_vehicle_status AS id_prg_vehicle_status,\n"
                + "    pvs.fecha AS fecha,\n"
                + "    pvs.servbus AS servbus,\n"
                + "    MIN(pvs.time_origin) AS time_origin,\n"
                + "    MAX(pvs.time_destiny) AS time_destiny,\n"
                + "    (SELECT \n"
                + "            SUM(pvs_.production_distance)\n"
                + "        FROM\n"
                + "            prg_vehicle_status pvs_\n"
                + "        WHERE\n"
                + "            pvs_.servbus = pvs.servbus\n"
                + "                AND pvs_.fecha = ?1\n"
                + "                AND pvs_.id_prg_actividad = 1\n"
                + "        ORDER BY pvs_.time_origin ASC\n"
                + "        LIMIT 1) AS production_distance,\n"
                + "    (SELECT \n"
                + "            pvs_.id_from_depot\n"
                + "        FROM\n"
                + "            prg_vehicle_status pvs_\n"
                + "        WHERE\n"
                + "            pvs_.servbus = pvs.servbus\n"
                + "                AND pvs_.fecha = ?1\n"
                + "                AND pvs_.id_prg_actividad = 1\n"
                + "        ORDER BY pvs_.time_origin ASC\n"
                + "        LIMIT 1) AS id_from_depot,\n"
                + "    (SELECT \n"
                + "            pvs_i.id_to_depot\n"
                + "        FROM\n"
                + "            prg_vehicle_status pvs_i\n"
                + "        WHERE\n"
                + "            pvs_i.servbus = pvs.servbus\n"
                + "                AND pvs_i.fecha = ?1\n"
                + "                AND pvs_i.id_prg_actividad = 1\n"
                + "        ORDER BY pvs_i.time_destiny DESC\n"
                + "        LIMIT 1) AS id_to_depot\n"
                + "FROM\n"
                + "    prg_vehicle_status pvs\n"
                + "WHERE\n"
                + "    pvs.fecha = ?1\n"
                + "        AND pvs.id_prg_actividad = 1\n"
                + "GROUP BY 3\n"
                + "ORDER BY pvs.id_from_depot DESC;";
        try {
            Query q = em.createNativeQuery(sql, PrgVehicleStatus.class);
            q.setParameter(1, c_fechaIni);
            q.setParameter(2, idPatio);
            List<PrgVehicleStatus> lista = new ArrayList<>();
            List<PrgVehicleStatus> lista_aux = new ArrayList<>();
            lista = q.getResultList();
            lista_aux.addAll(lista);
            if (idPatio != 0) {
                for (PrgVehicleStatus p : lista_aux) {
                    if (!(p.getIdFromDepot().getIdPrgStoppoint().equals(idPatio))) {
                        lista.remove(p);
                    }
                }
            } else {
                return lista;
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgVehicleStatus> findPrgPatiosNextDay(Date fecha) {
        String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    id_prg_vehicle_status AS id_prg_vehicle_status,\n"
                    + "    fecha AS fecha,\n"
                    + "    servbus AS servbus,\n"
                    + "    MIN(time_origin) AS time_origin,\n"
                    + "    MAX(time_destiny) AS time_destiny,\n"
                    + "    SUM(production_distance) AS production_distance,\n"
                    + "	id_from_depot AS id_from_depot\n"
                    + "FROM\n"
                    + "    prg_vehicle_status\n"
                    + "WHERE\n"
                    + "    fecha = ?1\n"
                    + "GROUP BY 7\n"
                    + "ORDER BY id_from_depot DESC , production_distance DESC; ", PrgVehicleStatus.class
            );
            q.setParameter(1, c_fechaIni);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgVehicleStatus> findPrgEnPatioNextDay(Date fecha, int idPatio, String desde, String hasta) {
        String fecha_ = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "  pvs.id_prg_vehicle_status,\n"
                    + "    pvs.fecha,\n"
                    + "    pvs.servbus,\n"
                    + "    pvs.id_vehiculo_tipo,\n"
                    + "    pvs.expedicion,\n"
                    + "    pvs.id_prg_actividad,\n"
                    + "    pvs.id_from_depot,\n"
                    + "    pvs.id_to_depot,\n"
                    + "    pvs.time_origin,\n"
                    + "    pvs.time_destiny,\n"
                    + "    pvs.comercial_time,\n"
                    + "    pvs.hlp_time,\n"
                    + "    pvs.dead_time,\n"
                    + "    pvs.production_time,\n"
                    + "    pvs.comercial_distance,\n"
                    + "    pvs.hlp_distance,\n"
                    + "    (SELECT \n"
                    + "            SUM(pvs_.production_distance)\n"
                    + "        FROM\n"
                    + "            prg_vehicle_status pvs_\n"
                    + "        WHERE\n"
                    + "            pvs_.fecha = ?1\n"
                    + "                AND servbus = pvs.servbus) AS production_distance,\n"
                    + "    pvs.lineas,\n"
                    + "    pvs.username,\n"
                    + "    pvs.creado,\n"
                    + "    pvs.modificado,\n"
                    + "    pvs.estado_reg\n"
                    + "FROM\n"
                    + "    prg_vehicle_status pvs\n"
                    + "WHERE\n"
                    + "    pvs.fecha = ?1\n"
                    + "        AND pvs.id_prg_actividad = 2\n"
                    + "        AND pvs.id_from_depot = ?2\n"
                    + "        AND (TIME_TO_SEC(pvs.time_origin) >= TIME_TO_SEC(?3)\n"
                    + "        AND TIME_TO_SEC(pvs.time_destiny) <= TIME_TO_SEC(?4))\n"
                    + "ORDER BY pvs.servbus ASC;", PrgVehicleStatus.class);
            q.setParameter(1, fecha_);
            q.setParameter(2, idPatio);
            q.setParameter(3, desde);
            q.setParameter(4, hasta);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public PrgVehicleStatus getFromDepotOrToDepotByServbus(Date fecha, String servbus, int opc) {
        String fecha_ = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        String orderBy = "";
        if (opc == 0) {
            orderBy = "ORDER BY pvs.time_origin ASC\n";
        } else {
            orderBy = "ORDER BY pvs.time_destiny DESC\n";
        }

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    pvs.*\n"
                    + "FROM\n"
                    + "    prg_vehicle_status pvs\n"
                    + "WHERE\n"
                    + "    pvs.servbus = ?2\n"
                    + "        AND pvs.fecha = ?1\n"
                    + "        AND pvs.id_prg_actividad = 1\n"
                    + orderBy
                    + "LIMIT 1", PrgVehicleStatus.class
            );
            q.setParameter(1, fecha_);
            q.setParameter(2, servbus);
            return (PrgVehicleStatus) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int removeByDate(Date d) {
        try {
            String sql = "delete from prg_vehicle_status where fecha= ?1";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
