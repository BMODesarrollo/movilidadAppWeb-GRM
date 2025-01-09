/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableNovedadOp;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class CableNovedadOpFacade extends AbstractFacade<CableNovedadOp> implements CableNovedadOpFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableNovedadOpFacade() {
        super(CableNovedadOp.class);
    }

    @Override
    public List<CableNovedadOp> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableNovedadOp.findByEstadoReg", CableNovedadOp.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableNovedadOp findByClaseEventoAndFecha(int claseEvento, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    n.*\n"
                    + "FROM\n"
                    + "    cable_novedad_op n\n"
                    + "        INNER JOIN\n"
                    + "    cable_evento_tipo_det d ON d.id_cable_evento_tipo_det = n.id_cable_evento_tipo_det\n"
                    + "WHERE\n"
                    + "    d.clase_evento = ?1 AND DATE(n.fecha) = ?2\n"
                    + "        AND d.estado_reg = 0 LIMIT 1;", CableNovedadOp.class);
            q.setParameter(1, claseEvento);
            q.setParameter(2, Util.dateFormat(fecha));
            return (CableNovedadOp) q.getSingleResult();

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            return null;
        }
    }

    @Override
    public CableNovedadOp findByFecha(Date fecha) {
        try {
            String sql = "SELECT * FROM cable_novedad_op where DATE(fecha) = ?1 and estado_reg = 0 limit 1;";
            Query query = em.createNativeQuery(sql, CableNovedadOp.class);
            query.setParameter(1, Util.dateFormat(fecha));

            return (CableNovedadOp) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableNovedadOp findByClaseEventoAndIdRegistro(int claseEvento, Date fecha, Integer idRegistro) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    n.*\n"
                    + "FROM\n"
                    + "    cable_novedad_op n\n"
                    + "        INNER JOIN\n"
                    + "    cable_evento_tipo_det d ON d.id_cable_evento_tipo_det = n.id_cable_evento_tipo_det\n"
                    + "WHERE\n"
                    + "    d.clase_evento = ?1 AND DATE(n.fecha) = ?2 AND\n"
                    + "    n.id_cable_novedad_op <> ?3\n"
                    + "        AND d.estado_reg = 0 LIMIT 1;", CableNovedadOp.class);
            q.setParameter(1, claseEvento);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idRegistro);
            return (CableNovedadOp) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableNovedadOp> getListByFecha(Date fecha) {
        try {
            String sql = "SELECT * FROM cable_novedad_op where DATE(fecha) = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableNovedadOp.class);
            query.setParameter(1, Util.dateFormat(fecha));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableNovedadOp findByFinOperacion(Date fecha) {
        try {
            Query q = em.createNativeQuery("select\n"
                    + "	cno.*\n"
                    + "from\n"
                    + "	cable_novedad_op cno\n"
                    + "inner join cable_evento_tipo_det cetd on\n"
                    + "	cno.id_cable_evento_tipo_det = cetd.id_cable_evento_tipo_det\n"
                    + "where\n"
                    + "cetd.clase_evento = 2\n"
                    + "and date(cno.fecha) = ?1 limit 1;", CableNovedadOp.class);
            q.setParameter(1, Util.dateFormat(fecha));
            return (CableNovedadOp) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableNovedadOp> findByDateRange(Date desde, Date hasta) {
        try {
            String sql = "SELECT * FROM cable_novedad_op where DATE(fecha) BETWEEN ?1 AND ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableNovedadOp.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableNovedadOp findByFechaAnterior(Date fecha) {
        try {
            String sql = "SELECT * FROM cable_novedad_op where date(fecha) = ?1 and estado_reg = 0 order by fecha desc LIMIT 1;";
            Query query = em.createNativeQuery(sql, CableNovedadOp.class);
            query.setParameter(1, Util.dateFormat(fecha));

            return (CableNovedadOp) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableNovedadOp findLastTimeParada(Date fecha) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    cable_novedad_op\n"
                    + "WHERE\n"
                    + "    DATE(fecha) = ?1\n"
                    + "    AND estado_reg = 0\n"
                    + "    order by time_fin_parada desc\n"
                    + "    limit 1;";
            Query query = em.createNativeQuery(sql, CableNovedadOp.class);
            query.setParameter(1, Util.dateFormat(fecha));

            return (CableNovedadOp) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
