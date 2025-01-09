/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadPrgTc;
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
 * @author solucionesit
 */
@Stateless
public class NovedadPrgTcFacade extends AbstractFacade<NovedadPrgTc> implements NovedadPrgTcFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadPrgTcFacade() {
        super(NovedadPrgTc.class);
    }

    @Override
    public List<NovedadPrgTc> findTqByFechas(Date desde, Date hasta) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(desde);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(hasta);
            Query query = em.createNativeQuery("SELECT \n"
                    + "    ntc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    novedad_prg_tc ntc ON ntc.id_prg_tc = tc.id_prg_tc\n"
                    + "WHERE\n"
                    + "    ntc.id_novedad IN (SELECT\n"
                    + "            n.id_novedad\n"
                    + "        FROM\n"
                    + "            novedad n\n"
                    + "        WHERE\n"
                    + "            n.fecha BETWEEN ?1 AND ?2\n"
                    + "                AND n.id_novedad_tipo_detalle = 128)\n"
                    + "        AND tc.estado_operacion IN (2 , 5, 8, 99)\n"
                    + "ORDER BY tc.fecha ASC , ntc.id_novedad ASC , tc.time_origin ASC;", NovedadPrgTc.class)
                    .setParameter(1, c_fechaIni)
                    .setParameter(2, c_fechaFin);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<NovedadPrgTc> findAllByFechas(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func_tc = idGopUnidadFuncional == 0 ? "" : "        AND tc.id_gop_unidad_funcional = ?3\n";
            String sql_unida_func_ntc = idGopUnidadFuncional == 0 ? "" : "        AND ntc.id_gop_unidad_funcional = ?3\n";
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(desde);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(hasta);
            Query query = em.createNativeQuery("SELECT \n"
                    + "    ntc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    novedad_prg_tc ntc ON ntc.id_prg_tc = tc.id_prg_tc\n"
                    + "WHERE\n"
                    + "    ntc.id_novedad IN (SELECT \n"
                    + "            n.id_novedad\n"
                    + "        FROM\n"
                    + "            novedad n\n"
                    + "        WHERE\n"
                    + "            n.fecha BETWEEN ?1 AND ?2)\n"
                    + sql_unida_func_ntc
                    + sql_unida_func_tc
                    + "ORDER BY tc.fecha ASC , ntc.id_novedad ASC , tc.time_origin ASC", NovedadPrgTc.class)
                    .setParameter(1, c_fechaIni)
                    .setParameter(2, c_fechaFin)
                    .setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<NovedadPrgTc> findNovedadAusentismoPrgTcByFechas(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func_tc = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?3\n";
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(desde);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(hasta);
            Query query = em.createNativeQuery("select id, id_novedad, id_prg_tc, username, creado, modificado, estado_reg, id_empleado, id_old_empleado," +
                    " id_vehiculo, id_old_vehiculo, observaciones, control, id_prg_tc_responsable,\n" +
                    " time_origin, time_destiny, estado_operacion, to_stop, from_stop, distancia, id_gop_unidad_funcional from novedad_prg_tc where id_novedad " +
                    "in (select id_novedad\n" +
                    "from \n" +
                    "novedad \n" +
                    "where id_novedad_tipo = 1\n" +
                    "and id_novedad_tipo_detalle in (7,8,9,10,11,12,13,14,15,16,22,24,77,78,79,80,81,6)\n" +
                    "and procede = 1\n" +
                    "and fecha between ?1 and ?2\n"
                    + sql_unida_func_tc
                    + ")", NovedadPrgTc.class)
                    .setParameter(1, c_fechaIni)
                    .setParameter(2, c_fechaFin)
                    .setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
