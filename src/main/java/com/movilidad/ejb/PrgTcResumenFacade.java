/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.util.beans.KmsEjecutadoRes;
import com.movilidad.util.beans.KmsResumen;
import com.movilidad.utils.Util;
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
public class PrgTcResumenFacade extends AbstractFacade<PrgTcResumen> implements PrgTcResumenFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTcResumenFacade() {
        super(PrgTcResumen.class);
    }

    @Override
    public void createTransaction(PrgTcResumen tcResumen, List<PrgTc> listaTc) {
//        System.out.println("Transaccion");
//        EntityTransaction transaction = em.getTransaction();
        try {
            int txs = 1;
//            transaction.begin();
//            em.persist(tcResumen);
            for (PrgTc tc : listaTc) {
//                System.out.println("ID TCResumen : " + tcResumen.getIdPrgTcResumen());
                tc.setIdPrgTcResumen(tcResumen);
                em.persist(tc);
                if (txs % 20 == 0) {
                    em.flush();
                    em.clear();
                }
                txs++;
            }

            em.flush();
            em.clear();
//            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<PrgTcResumen> findAll(String fromDate, String toDate) {
        Query q = em.createNativeQuery("select tcr.* from prg_tc_resumen tcr where "
                + "fecha between ?1 and ?2 order by fecha asc;", PrgTcResumen.class);
        q.setParameter(1, fromDate);
        q.setParameter(2, toDate);
        return q.getResultList();
    }

    @Override
    public PrgTcResumen findByFecha(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND p.id_gop_unidad_funcional = ?2\n";
            Query query = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    prg_tc_resumen p\n"
                    + "WHERE\n"
                    + "    DATE(p.fecha) = ?1\n"
                    + "        AND p.estado_reg = 0\n"
                    + sql_unida_func
                    + "ORDER BY p.id_gop_unidad_funcional ASC\n"
                    + "LIMIT 1", PrgTcResumen.class)
                    .setParameter(1, Util.dateFormat(fecha))
                    .setParameter(2, idGopUnidadFuncional);
            return (PrgTcResumen) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<PrgTcResumen> getResumenSem(String fechaIni, String fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND ptr.id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc_resumen ptr\n"
                    + "WHERE\n"
                    + "    ptr.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND ptr.estado_reg = 0"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, PrgTcResumen.class)
                    .setParameter(1, fechaIni)
                    .setParameter(2, fechaFin)
                    .setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public KmsResumen getResumen(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and p.id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT\n"
                    + "	SUM(if(p.id_vehiculo_tipo = 1 AND p.estado_operacion in (3, 4, 6), TRUNCATE(p.distance, 0), 0)) as AdcArt,\n"
                    + "	SUM(if(p.id_vehiculo_tipo = 2 AND p.estado_operacion in (3, 4, 6), TRUNCATE(p.distance, 0), 0)) as AdcBi,\n"
                    + "	SUM(if(p.id_vehiculo_tipo = 1 AND p.distance > 0 AND p.estado_operacion = 5 AND t.id_prg_tarea NOT IN (2, 3, 4), TRUNCATE(p.distance, 0), 0)) as ElimArt,\n"
                    + "	SUM(if(p.id_vehiculo_tipo = 2 AND p.distance > 0 AND p.estado_operacion = 5 AND t.id_prg_tarea NOT IN (2, 3, 4), TRUNCATE(p.distance, 0), 0)) as ElimBi\n"
                    + "FROM\n"
                    + "	prg_tc p\n"
                    + "INNER JOIN prg_tarea t on\n"
                    + "	p.id_task_type = t.id_prg_tarea\n"
                    + "WHERE\n"
                    + "	p.fecha = ?1\n"
                    + sql_unida_func
                    + "	AND t.sum_distancia = 1\n"
                    + "	AND t.comercial = 1;";
            Query query = em.createNativeQuery(sql, "KmsResumenMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, idGopUnidadFuncional);
            return (KmsResumen) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public KmsEjecutadoRes getEjecutado(Date fecha) {
        String sql = "SELECT\n"
                + "	SUM(if(p.id_vehiculo_tipo = 1 AND p.estado_operacion in (7), TRUNCATE(p.distance, 0), 0)) as hlpArt,\n"
                + "	SUM(if(p.id_vehiculo_tipo = 2 AND p.estado_operacion in (7), TRUNCATE(p.distance, 0), 0)) as hlpBi\n"
                + "FROM\n"
                + "	prg_tc p\n"
                + "INNER JOIN prg_tarea t on\n"
                + "	p.id_task_type = t.id_prg_tarea\n"
                + "WHERE\n"
                + "	p.fecha = ?1\n"
                + "	AND t.sum_distancia = 1\n"
                + "	AND t.comercial = 0;";
        Query query = em.createNativeQuery(sql, "KmsEjecutadoResMapping");
        query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
        return (KmsEjecutadoRes) query.getSingleResult();
    }

    @Override
    public PrgTcResumen isConciliado(Date fecha, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";

            Query query = em.createNativeQuery("select * from prg_tc_resumen where fecha = ?1"
                    + sql_unida_func
                    + ";", PrgTcResumen.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, idGopUnidadFuncional);

            return (PrgTcResumen) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int removeByDate(Date d, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";
            String sql = "delete from prg_tc_resumen where fecha= ?1"
                    + sql_unida_func
                    + ";";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            q.setParameter(2, idGopUnidadFuncional);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

}
